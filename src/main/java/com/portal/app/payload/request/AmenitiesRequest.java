package com.portal.app.payload.request;

import java.util.Set;

public class AmenitiesRequest {

    private Long id;

    private Long pId;

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

    public Set<String> getSpecification() {
        return specification;
    }

    public void setSpecification(Set<String> specification) {
        this.specification = specification;
    }

    @Override
    public String toString() {
        return "AmenitiesRequest{" +
                "id=" + id +
                ", pId=" + pId +
                ", specification='" + specification + '\'' +
                '}';
    }
}
