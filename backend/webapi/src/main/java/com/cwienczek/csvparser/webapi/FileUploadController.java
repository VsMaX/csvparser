package com.cwienczek.csvparser.webapi;

import com.cwienczek.csvparser.parser.CsvParser;
import com.cwienczek.csvparser.parser.CsvPropertyObject;
import io.reactivex.Observable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

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

    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public com.cwienczek.csvparser.webapi.FileUploadResponse handleFileUpload(
            @RequestParam("file") MultipartFile file, @RequestParam String fileName) {
        String fileId = UUID.randomUUID().toString();
        if (!file.isEmpty()) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                CsvParser parser = new CsvParser();

                Observable<CsvPropertyObject> parsedFileObservable = parser.parseCsvToPropertyObject(reader);

                return new com.cwienczek.csvparser.webapi.FileUploadResponse(fileId, fileName);
            } catch (Exception e) {
                throw new BadRequestException();
            }
        } else {
            throw new BadRequestException();
        }
    }
}
