package com.cwienczek.csvparser.parser;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import io.reactivex.Observable;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.stream.Stream;

public class CsvParser implements Parser {
    public Stream<CsvPropertyObject> parseCsvToPropertyObject(Reader csvReader){
        CSVReader reader = new CSVReader(csvReader, ';');
        try {
            List<String[]> strings = reader.readAll();
            Stream<String[]> stream = strings.stream();
            return stream
                    .skip(1)//skip first line
                    .map(row -> {
                int rowLength = row.length;
                if(rowLength >= 8) {//correct row length
                    //TODO fail if double cannot be parsed
                    double att1 = Double.parseDouble(row[0]);
                    double att2 = Double.parseDouble(row[1]);
                    double att3 = Double.parseDouble(row[2]);
                    double att4 = Double.parseDouble(row[3]);
                    double att5 = Double.parseDouble(row[4]);

                    //For CPU and memory savings this could be omitted but it adds a bit of readability
                    String att6 = row[5];
                    String att7 = row[6];
                    String label = row[7];

                    return new CsvPropertyObject(att1, att2, att3, att4, att5, att6, att7, label);
                } else { //row length incorrect, abort parsing
                    throw new IllegalArgumentException("Error parsing CSV data, row contains illegal number of columns");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();
        }
//
//        Observable<String[]> csvObservable = Observable.fromIterable(reader);
//        return csvObservable
//                .map(row -> {
//                    int rowLength = row.length;
//                    if(rowLength >= 8) {//correct row length
//                        //TODO fail if double cannot be parsed
//                        double att1 = Double.parseDouble(row[0]);
//                        double att2 = Double.parseDouble(row[1]);
//                        double att3 = Double.parseDouble(row[2]);
//                        double att4 = Double.parseDouble(row[3]);
//                        double att5 = Double.parseDouble(row[4]);
//
//                        //For CPU and memory savings this could be omitted but it adds a bit of readability
//                        String att6 = row[5];
//                        String att7 = row[6];
//                        String label = row[7];
//
//                        return new CsvPropertyObject(att1, att2, att3, att4, att5, att6, att7, label);
//                    } else { //row length incorrect, abort parsing
//                        throw new IllegalArgumentException("Error parsing CSV data, row contains illegal number of columns");
//                    }
//                });
    }
}

