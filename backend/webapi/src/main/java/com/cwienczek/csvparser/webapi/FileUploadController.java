package com.cwienczek.csvparser.webapi;

import com.cwienczek.csvparser.parser.CsvParser;
import com.cwienczek.csvparser.parser.CsvPropertyObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Michal Cwienczek on 08/04/2017.
 */
@RestController
public class FileUploadController {



    //TODO remove after upload works
    @RequestMapping(value="/test", method = RequestMethod.GET)
    public @ResponseBody String test(){
        return "Hello World!";
    }

    @CrossOrigin
    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public Map<String, AnalysisResult> handleFileUpload(
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

                Map<String, AnalysisResult> analysisResultMap = new HashMap<String, AnalysisResult>();

                for (Map.Entry<String, List<CsvPropertyObject>> item: collect.entrySet()) {
                    double medianAttr1 = getMedianForList(item, x -> x.getAtt1());
                    double medianAttr2 = getMedianForList(item, x -> x.getAtt2());
                    double medianAttr3 = getMedianForList(item, x -> x.getAtt3());
                    double medianAttr4 = getMedianForList(item, x -> x.getAtt4());
                    double medianAttr5 = getMedianForList(item, x -> x.getAtt5());
                    AnalysisResult result = new AnalysisResult(medianAttr1,
                            medianAttr2,
                            medianAttr3,
                            medianAttr4,
                            medianAttr5);

                    analysisResultMap.put(item.getKey(), result);
                }

                return analysisResultMap;
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

    public double getMedianForList(Map.Entry<String, List<CsvPropertyObject>> propertyObjectList, Function<CsvPropertyObject, Double> medianPredicate){
        List<Double> sorted = propertyObjectList.getValue().stream().map(medianPredicate).sorted().collect(Collectors.toList());
        int count = sorted.size();
        double median = sorted.get(count/2);
        return median;
    }


}
