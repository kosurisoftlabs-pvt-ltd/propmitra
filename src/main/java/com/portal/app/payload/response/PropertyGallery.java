package com.portal.app.payload.response;

import java.io.Serializable;
import java.util.Set;

public class PropertyGallery implements Serializable {

    private static final long serialVersionUID = 8019668722780696758L;

    private Long id;

    private Long pId;

    private String propId;

    private Set<Attachment> attachments;

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

    public String getPropId() {
        return propId;
    }

    public void setPropId(String propId) {
        this.propId = propId;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }
}
