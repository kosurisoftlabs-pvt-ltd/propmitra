package com.portal.app.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.portal.app.payload.User;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropertyResponse implements Serializable {

    private static final long serialVersionUID = 6736518110700710136L;

    private Long id;

    private String propId;

    private Integer progress;

    private Byte isActive;

    private Byte isVerified;

    private Double totalValue;

    private String inactiveComments;

    private String verifyComments;

    private PropertyBasic propertyBasic;

    private PropertyFloor propertyFloor;

    private PropertyAmenities propertyAmenities;

    private PropertyGallery propertyGallery;

    private PropertyMaterial propertyMaterial;

    private PropertyNearby propertyNearby;

    private PropertyBanks propertyBanks;

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

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public String getInactiveComments() {
        return inactiveComments;
    }

    public void setInactiveComments(String inactiveComments) {
        this.inactiveComments = inactiveComments;
    }

    public String getVerifyComments() {
        return verifyComments;
    }

    public void setVerifyComments(String verifyComments) {
        this.verifyComments = verifyComments;
    }

    public PropertyBasic getPropertyBasic() {
        return propertyBasic;
    }

    public void setPropertyBasic(PropertyBasic propertyBasic) {
        this.propertyBasic = propertyBasic;
    }

    public PropertyFloor getPropertyFloor() {
        return propertyFloor;
    }

    public void setPropertyFloor(PropertyFloor propertyFloor) {
        this.propertyFloor = propertyFloor;
    }

    public PropertyAmenities getPropertyAmenities() {
        return propertyAmenities;
    }

    public void setPropertyAmenities(PropertyAmenities propertyAmenities) {
        this.propertyAmenities = propertyAmenities;
    }

    public PropertyGallery getPropertyGallery() {
        return propertyGallery;
    }

    public void setPropertyGallery(PropertyGallery propertyGallery) {
        this.propertyGallery = propertyGallery;
    }

    public PropertyMaterial getPropertyMaterial() {
        return propertyMaterial;
    }

    public void setPropertyMaterial(PropertyMaterial propertyMaterial) {
        this.propertyMaterial = propertyMaterial;
    }

    public PropertyNearby getPropertyNearby() {
        return propertyNearby;
    }

    public void setPropertyNearby(PropertyNearby propertyNearby) {
        this.propertyNearby = propertyNearby;
    }

    public PropertyBanks getPropertyBanks() {
        return propertyBanks;
    }

    public void setPropertyBanks(PropertyBanks propertyBanks) {
        this.propertyBanks = propertyBanks;
    }

    @Override
    public String toString() {
        return "PropertyResponse{" +
                "id=" + id +
                ", propId='" + propId + '\'' +
                ", progress=" + progress +
                ", isActive=" + isActive +
                ", isVerified=" + isVerified +
                ", totalValue=" + totalValue +
                ", inactiveComments='" + inactiveComments + '\'' +
                ", verifyComments='" + verifyComments + '\'' +
                ", propertyBasic=" + propertyBasic +
                '}';
    }
}
