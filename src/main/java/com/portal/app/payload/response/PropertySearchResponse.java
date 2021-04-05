package com.portal.app.payload.response;

import java.io.Serializable;

public class PropertySearchResponse implements Serializable {

    private static final long serialVersionUID = -7484558098594604548L;

    private Long id;

    private String propId;

    private Integer progress;

    private Byte isActive;

    private Byte isVerified;

    private PropertyBasic propertyBasic;

    private Integer noFloors;

    private String noBeds;

    private Integer floorNo;

    private Double areaSft;

    private Double priceSft;

    private String createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPropId() {
        return propId;
    }

    public void setPropId(String propId) {
        this.propId = propId;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    public Byte getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Byte isVerified) {
        this.isVerified = isVerified;
    }

    public PropertyBasic getPropertyBasic() {
        return propertyBasic;
    }

    public void setPropertyBasic(PropertyBasic propertyBasic) {
        this.propertyBasic = propertyBasic;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "PropertySearchResponse{" +
                "id=" + id +
                ", propId='" + propId + '\'' +
                ", progress=" + progress +
                ", isActive=" + isActive +
                ", isVerified=" + isVerified +
                ", propertyBasic=" + propertyBasic +
                ", noFloors=" + noFloors +
                ", noBeds='" + noBeds + '\'' +
                ", floorNo=" + floorNo +
                ", areaSft=" + areaSft +
                ", priceSft=" + priceSft +
                '}';
    }
}
