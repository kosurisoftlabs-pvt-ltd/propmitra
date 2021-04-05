package com.portal.app.model;

import com.portal.app.model.audit.UserDateAudit;

import javax.persistence.*;

@Entity
@Table(name = "prop_basic")
public class PropertyBasic extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROP_BASIC_ID")
    private Long id;

    @Column(name = "PROP_BASIC_POSTED_BY")
    private String postedBy;

    @Column(name = "PROP_BASIC_RENT_SALE")
    private String rentSale;

    @Column(name = "PROP_BASIC_PROP_AGE")
    private String propAge;

    @Column(name = "PROP_BASIC_PROP_TYPE")
    private String propType;

    @Column(name = "PROP_BASIC_PROP_STATUS")
    private String propStatus;

    @Column(name = "PROP_BASIC_COMPLETION")
    private String completion;

    @Column(name = "PROP_BASIC_COMPLETION_TYPE")
    private String completionType;

    @Column(name = "PROP_BASIC_LEASE_TERM")
    private String leaseTerm;

    @Column(name = "PROP_BASIC_ADVNC_AMT")
    private Double advanceAmount;

    @Column(name = "PROP_BASIC_RENT_AMT")
    private Double rentAmount;

    @Column(name = "PROP_BASIC_FURN_OR_SEMI")
    private String furnishedOrSemi;

    @Column(name = "PROP_BASIC_NO_BATH_RMS")
    private String noBathRooms;

    @Column(name = "PROP_BASIC_LNDMRK")
    private String landMark;

    @Column(name = "PROP_BASIC_PLCNR")
    private String placesNear;

    @Column(name = "PROP_BASIC_FLOOR_SFT")
    private String floorSft;

    @Column(name = "PROP_BASIC_FLOOR_NO")
    private String floorNo;

    @Column(name = "PROP_BASIC_PROJECT_NAME")
    private String projectName;

    @Column(name = "PROP_BASIC_PROP_LOC")
    private String propLocation;

    @Column(name = "PROP_BASIC_PIN_CODE")
    private String pinCode;

    @Column(name = "PROP_BASIC_PROP_DESC")
    private String description;

    @Column(name = "PROP_BASIC_IS_GATED")
    private String isGated = "N";

    @OneToOne(mappedBy = "propertyBasic")
    private PropertyMaster propertyMaster;

    @OneToOne(fetch=FetchType.EAGER , cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "PROP_ATCH_PKID")
    private Attachment frontImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(Double advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    public Double getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(Double rentAmount) {
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

    public void setGated(String gated) {
        isGated = gated;
    }

    public PropertyMaster getPropertyMaster() {
        return propertyMaster;
    }

    public void setPropertyMaster(PropertyMaster propertyMaster) {
        this.propertyMaster = propertyMaster;
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
                ", propertyMaster=" + propertyMaster +
                '}';
    }
}
