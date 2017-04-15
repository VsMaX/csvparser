package com.cwienczek.csvparser.webapi;

import com.cwienczek.csvparser.parser.CsvParser;
import com.cwienczek.csvparser.parser.CsvPropertyObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Michal Cwienczek on 08/04/2017.
 */
@RestController
public class FileUploadController {

    //we need to use concurrent map because we might get concurrent calls from the web
    //if we're gonna fetch single record it's very bad to index it by Date, some other field would be much better then
    Map<Date, AnalysisResult> analysisResultMap = new ConcurrentHashMap<>();

    @CrossOrigin //todo narrow cross origin allowed URLs to one URL, URL shuold be easily swappable based on environment configuration
    @RequestMapping(value="/upload", method= RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void handleFileUpload(
            @RequestParam("file") MultipartFile file, @RequestParam String fileName) {

        //using java.util.Date as timestamp, resolution of 1 ms on most JVMs in probably more than enough
        Date timeStamp = new Date();

        String fileId = UUID.randomUUID().toString();
        if (!file.isEmpty()) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                CsvParser parser = new CsvParser();

                Stream<CsvPropertyObject> parsedFileObservable = parser.parseCsvToPropertyObject(reader);

                //I am assuming that the set of labels is rather sparse
                Map<String, List<CsvPropertyObject>> collect = parsedFileObservable
                        .collect(Collectors.groupingBy(x -> x.getLabel()));

                for (Map.Entry<String, List<CsvPropertyObject>> item: collect.entrySet()) {
                    double medianAttr1 = getMedianForList(item, x -> x.getAtt1());
                    double medianAttr2 = getMedianForList(item, x -> x.getAtt2());
                    double medianAttr3 = getMedianForList(item, x -> x.getAtt3());
                    double medianAttr4 = getMedianForList(item, x -> x.getAtt4());
                    double medianAttr5 = getMedianForList(item, x -> x.getAtt5());
                    AnalysisResult result = new AnalysisResult(timeStamp, item.getKey(), medianAttr1,
                            medianAttr2,
                            medianAttr3,
                            medianAttr4,
                            medianAttr5);

                    analysisResultMap.put(timeStamp, result);
                }
            } catch (Exception e) {
                if(e instanceof IllegalArgumentException) {
                    throw new BadRequestException();
                    //TODO differentiate between errors a bit more, catch then in Controller
                    //TODO perhaps implement filter and translate exceptions to HTTP response codes
                }
                throw new BadRequestException();
            }
        } else {
            throw new BadRequestException();
        }
    }

    @CrossOrigin
    @RequestMapping(value="/results", method= RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    //TODO support fetching single result set
    public List<AnalysisResult> getAllResults() {
        //we need to copy the map to ArrayList. This serves 2 purposes:
        //1. Serializing should be done on immutable element
        //2. It will prevent blocking Concurrent map from accepting new items
        return new ArrayList(analysisResultMap.values());
    }

    //TODO move this to outside class/project that is responsible for calculating statistical data
    public double getMedianForList(Map.Entry<String, List<CsvPropertyObject>> propertyObjectList, Function<CsvPropertyObject, Double> medianPredicate){
        List<Double> sorted = propertyObjectList.getValue().stream().map(medianPredicate).sorted().collect(Collectors.toList());
        int count = sorted.size();
        double median = 0;
        if(count % 2 == 0){ //for even number of elements we need to get average of the middle two
            median = (sorted.get(count/2 - 1) + sorted.get(count/2)) / 2;
        } else { //else we just take the median element
            median = sorted.get(count/2);
        }

        return median;
    }


}
