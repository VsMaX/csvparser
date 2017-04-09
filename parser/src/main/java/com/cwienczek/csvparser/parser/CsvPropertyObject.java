package com.cwienczek.csvparser.parser;

/**
 * Created by Michal Cwienczek on 09/04/2017.
 */
public class CsvPropertyObject {
    private double att1;
    private double att2;
    private double att3;
    private double att4;
    private double att5;
    private String att6;
    private String att7;
    private String att8;

    public CsvPropertyObject(double att1, double att2, double att3, double att4, double att5, String att6, String att7, String att8) {
        this.att1 = att1;
        this.att2 = att2;
        this.att3 = att3;
        this.att4 = att4;
        this.att5 = att5;
        this.att6 = att6;
        this.att7 = att7;
        this.att8 = att8;
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

    public String getAtt6() {
        return att6;
    }

    public void setAtt6(String att6) {
        this.att6 = att6;
    }

    public String getAtt7() {
        return att7;
    }

    public void setAtt7(String att7) {
        this.att7 = att7;
    }

    public String getAtt8() {
        return att8;
    }

    public void setAtt8(String att8) {
        this.att8 = att8;
    }
}
