package com.cwienczek.csvparser.webapi;

/**
 * Created by Michal Cwienczek on 08/04/2017.
 */
public class FileUploadResponse {
    /**
     * Internal file name of uploaded file
     */
    private String fileUUID;

    /**
     * Uploaded file name
     */
    private String fileName;

    public FileUploadResponse(String fileUUID, String fileName) {
        this.fileUUID = fileUUID;
        this.fileName = fileName;
    }

    public String getFileUUID() {
        return fileUUID;
    }

    public void setFileUUID(String fileUUID) {
        this.fileUUID = fileUUID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
