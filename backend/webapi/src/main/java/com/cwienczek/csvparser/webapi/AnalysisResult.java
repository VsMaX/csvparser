package com.cwienczek.csvparser.webapi;

import java.util.Date;

/**
 * Created by Michal Cwienczek on 11/04/2017.
 */
public class AnalysisResult {
    private Date timestamp;
    private String label;
    /*
        Median of att1
        I know, naming could be better...
         */
    private double att1;
    /*
    Median of att2
     */
    private double att2;
    /*
    Median of att3
     */
    private double att3;
    private double att4;
    private double att5;
    private double att6;

    AnalysisResult(Date timestamp, String label, double att1, double att2, double att3, double att4, double att5) {
        this.timestamp = timestamp;
        this.label = label;
        this.att1 = att1;
        this.att2 = att2;
        this.att3 = att3;
        this.att4 = att4;
        this.att5 = att5;
    }

    public double getAtt1() {
        return att1;
    }

    public void setAtt1(double att1) {
        this.att1 = att1;
    }

    public double getAtt2() {
        return att2;
    }

    public void setAtt2(double att2) {
        this.att2 = att2;
    }

    public double getAtt3() {
        return att3;
    }

    public void setAtt3(double att3) {
        this.att3 = att3;
    }

    public double getAtt4() {
        return att4;
    }

    public void setAtt4(double att4) {
        this.att4 = att4;
    }

    public double getAtt5() {
        return att5;
    }

    public void setAtt5(double att5) {
        this.att5 = att5;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
