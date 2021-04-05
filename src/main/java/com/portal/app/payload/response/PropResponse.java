package com.portal.app.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PropResponse implements Serializable {

    private static final long serialVersionUID = 8019668722780696758L;

    private Long id;

    private String propId;

    private String postedBy;

    private String rentSale;

    private String projectName;

    private String propLocation;

    private String pinCode;

    private String isGated;

    private Byte isActive;

    private Byte isVerified;

    private Double totalValue;

    private String createdAt;

    private String username;

    private String noBeds;

    private Integer progress;

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

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getRentSale() {
        return rentSale;
    }

    public void setRentSale(String rentSale) {
        this.rentSale = rentSale;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPropLocation() {
        return propLocation;
    }

    public void setPropLocation(String propLocation) {
        this.propLocation = propLocation;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getIsGated() {
        return isGated;
    }

    public void setIsGated(String isGated) {
        this.isGated = isGated;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNoBeds() {
        return noBeds;
    }

    public void setNoBeds(String noBeds) {
        this.noBeds = noBeds;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "PropResponse{" +
                "id=" + id +
                ", propId='" + propId + '\'' +
                ", postedBy='" + postedBy + '\'' +
                ", rentSale='" + rentSale + '\'' +
                ", projectName='" + projectName + '\'' +
                ", propLocation='" + propLocation + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", isGated='" + isGated + '\'' +
                ", isActive=" + isActive +
                ", isVerified=" + isVerified +
                ", totalValue=" + totalValue +
                ", createdAt=" + createdAt +
                ", username=" + username +
                ", noBeds=" + noBeds +
                ", progress=" + progress +
                '}';
    }
}
