package com.portal.app.payload.response;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Base64;

public class Attachment implements Serializable {

    private static final long serialVersionUID = -4640483999682032137L;
    private Long id;
    private String fileName;
    private String fileType;
    private byte[] data;
    private String base64Image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getBase64Image() {
        if(data != null) {
            base64Image = Base64.getEncoder().encodeToString(data);
        }
        return base64Image;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType +
                '}';
    }
}
