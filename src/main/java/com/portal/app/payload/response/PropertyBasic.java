package com.portal.app.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertyBasic implements Serializable {

    private static final long serialVersionUID = 8019668722780696758L;

    private Long id;

    private Long pId;

    private String propId;

    private String postedBy;

    private String rentSale;

    private String propAge;

    private String propType;

    private String propStatus;

    private String completion;

    private String completionType;

    private String leaseTerm;

    private String advanceAmount;

    private String rentAmount;

    private String furnishedOrSemi;

    private String noBathRooms;

    private String landMark;

    private String placesNear;

    private String floorSft;

    private String floorNo;

    private String projectName;

    private String propLocation;

    private String pinCode;

    private String isGated;

    private String description;

    private Attachment frontImage;

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

    public String getPropAge() {
        return propAge;
    }

    public void setPropAge(String propAge) {
        this.propAge = propAge;
    }

    public String getPropType() {
        return propType;
    }

    public void setPropType(String propType) {
        this.propType = propType;
    }

    public String getPropStatus() {
        return propStatus;
    }

    public void setPropStatus(String propStatus) {
        this.propStatus = propStatus;
    }

    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

    public String getCompletionType() {
        return completionType;
    }

    public void setCompletionType(String completionType) {
        this.completionType = completionType;
    }

    public String getLeaseTerm() {
        return leaseTerm;
    }

    public void setLeaseTerm(String leaseTerm) {
        this.leaseTerm = leaseTerm;
    }

    public String getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(String advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    public String getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(String rentAmount) {
        this.rentAmount = rentAmount;
    }

    public String getFurnishedOrSemi() {
        return furnishedOrSemi;
    }

    public void setFurnishedOrSemi(String furnishedOrSemi) {
        this.furnishedOrSemi = furnishedOrSemi;
    }

    public String getNoBathRooms() {
        return noBathRooms;
    }

    public void setNoBathRooms(String noBathRooms) {
        this.noBathRooms = noBathRooms;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getPlacesNear() {
        return placesNear;
    }

    public void setPlacesNear(String placesNear) {
        this.placesNear = placesNear;
    }

    public String getFloorSft() {
        return floorSft;
    }

    public void setFloorSft(String floorSft) {
        this.floorSft = floorSft;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsGated() {
        return isGated;
    }

    public void setIsGated(String isGated) {
        this.isGated = isGated;
    }

    public Attachment getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(Attachment frontImage) {
        this.frontImage = frontImage;
    }

    @Override
    public String toString() {
        return "PropertyBasic{" +
                "id=" + id +
                ", pId='" + pId + '\'' +
                ", propId='" + propId + '\'' +
                ", postedBy='" + postedBy + '\'' +
                ", rentSale='" + rentSale + '\'' +
                ", propAge='" + propAge + '\'' +
                ", propType='" + propType + '\'' +
                ", propStatus='" + propStatus + '\'' +
                ", completion='" + completion + '\'' +
                ", completionType='" + completionType + '\'' +
                ", leaseTerm='" + leaseTerm + '\'' +
                ", advanceAmount='" + advanceAmount + '\'' +
                ", rentAmount='" + rentAmount + '\'' +
                ", furnishedOrSemi='" + furnishedOrSemi + '\'' +
                ", noBathRooms='" + noBathRooms + '\'' +
                ", landMark='" + landMark + '\'' +
                ", placesNear='" + placesNear + '\'' +
                ", floorSft='" + floorSft + '\'' +
                ", floorNo='" + floorNo + '\'' +
                ", projectName='" + projectName + '\'' +
                ", propLocation='" + propLocation + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", description='" + description + '\'' +
                ", isGated='" + isGated + '\'' +
                '}';
    }
}
