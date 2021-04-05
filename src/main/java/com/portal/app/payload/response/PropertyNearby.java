package com.portal.app.payload.response;

import java.io.Serializable;
import java.util.Set;

public class PropertyNearby implements Serializable {


    private static final long serialVersionUID = -4088542173015714852L;

    private Long id;

    private Long pId;

    private String propId;

    private Set<String> hospitals;

    private Set<String> schools;

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

    public Set<String> getHospitals() {
        return hospitals;
    }

    public void setHospitals(Set<String> hospitals) {
        this.hospitals = hospitals;
    }

    public Set<String> getSchools() {
        return schools;
    }

    public void setSchools(Set<String> schools) {
        this.schools = schools;
    }

    @Override
    public String toString() {
        return "PropertyNearby{" +
                "id=" + id +
                ", pId=" + pId +
                ", propId=" + propId +
                ", hospitals=" + hospitals +
                ", schools=" + schools +
                '}';
    }
}
