package com.portal.app.payload.request;

import java.util.Set;

public class NearbyRequest {

    private Long id;

    private Long pId;

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
        return "NearbyRequest{" +
                "id=" + id +
                ", pId=" + pId +
                ", hospitals=" + hospitals +
                ", schools=" + schools +
                '}';
    }
}
