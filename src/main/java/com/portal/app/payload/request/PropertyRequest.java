package com.portal.app.payload.request;

import javax.validation.constraints.NotBlank;

public class PropertyRequest {

    private String[] propIds;

    private String key;

    @NotBlank
    private String value;

    private String type;

    @NotBlank
    private String comments;

    public String[] getPropIds() {
        return propIds;
    }

    public void setPropIds(String[] propIds) {
        this.propIds = propIds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "PropertyRequest{" +
                "propId=" + propIds +
                ", type='" + type + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
