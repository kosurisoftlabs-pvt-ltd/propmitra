package com.portal.app.payload.request;

import org.springframework.web.multipart.MultipartFile;

public class GalleryRequest {

    private Long id;

    private Long pId;

    private MultipartFile[] files;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "GalleryRequest{" +
                "id=" + id +
                ", pId=" + pId +
                '}';
    }
}
