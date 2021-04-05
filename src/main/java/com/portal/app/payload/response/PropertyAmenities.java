package com.portal.app.payload.response;

import java.io.Serializable;
import java.util.Set;

public class PropertyAmenities implements Serializable {


    private static final long serialVersionUID = -4088542173015714852L;

    private Long id;

    private Long pId;

    private String propId;

    private Set<String> specification;

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

    public Set<String> getSpecification() {
        return specification;
    }

    public void setSpecification(Set<String> specification) {
        this.specification = specification;
    }

    @Override
    public String toString() {
        return "PropertyAmenities{" +
                "id=" + id +
                ", pId='" + pId + '\'' +
                ", propId='" + propId + '\'' +
                ", specification='" + specification + '\'' +
                '}';
    }
}
