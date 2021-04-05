package com.portal.app.payload.response;

import java.io.Serializable;
import java.util.Set;

public class PropertyFloor implements Serializable {

    private static final long serialVersionUID = 8019668722780696758L;

    private Long id;

    private Long pId;

    private String propId;

    private Integer noFloors;

    private String noBeds;

    private Integer floorNo;

    private Double areaSft;

    private Double priceSft;

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

    public Integer getNoFloors() {
        return noFloors;
    }

    public void setNoFloors(Integer noFloors) {
        this.noFloors = noFloors;
    }

    public String getNoBeds() {
        return noBeds;
    }

    public void setNoBeds(String noBeds) {
        this.noBeds = noBeds;
    }

    public Integer getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(Integer floorNo) {
        this.floorNo = floorNo;
    }

    public Double getAreaSft() {
        return areaSft;
    }

    public void setAreaSft(Double areaSft) {
        this.areaSft = areaSft;
    }

    public Double getPriceSft() {
        return priceSft;
    }

    public void setPriceSft(Double priceSft) {
        this.priceSft = priceSft;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "PropertyFloor{" +
                "id=" + id +
                ", pId=" + pId +
                ", propId=" + propId +
                ", noFloors=" + noFloors +
                ", noBeds='" + noBeds + '\'' +
                ", floorNo=" + floorNo +
                ", areaSft=" + areaSft +
                ", priceSft=" + priceSft +
                '}';
    }
}
