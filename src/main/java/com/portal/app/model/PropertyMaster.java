package com.portal.app.model;

import com.portal.app.model.audit.UserDateAudit;
import com.portal.app.util.CommonUtil;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.Set;

import static com.portal.app.util.AppConstants.PROPERTY_SUB_LIST;
import static com.portal.app.util.AppConstants.PROP_TYPE_RENT;

@Entity
@Table(name = "prop_master", indexes = {@Index(name = "PROP_MSTR_ID_INDX", columnList = "PROP_MSTR_ID", unique = true)})
public class PropertyMaster extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROP_MSTR_PKID")
    private Long id;

    @NotNull
    @Column(name = "PROP_MSTR_ID")
    private String propId;

    @NotNull
    @Column(name = "PROP_MSTR_PROGRESS")
    private Integer progress;

    @NotNull
    @Column(name = "PROP_MSTR_IS_ACTIVE")
    private Byte isActive;

    @NotNull
    @Column(name = "PROP_MSTR_IS_VERIFIED")
    private Byte isVerified;

    @Column(name = "PROP_MSTR_TOTAL_VAL")
    private Double totalValue;

    @Column(name = "PROP_MSTR_INACTIVE_COMMENTS")
    private String inactiveComments;

    @Column(name = "PROP_MSTR_VERIFY_COMMENTS")
    private String verifyComments;

    @OneToOne(fetch=FetchType.EAGER , cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "PROP_BASIC_ID", unique = true)
    private PropertyBasic propertyBasic;

    @OneToOne(fetch=FetchType.EAGER , cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "PROP_FLOOR_PKID")
    private PropertyFloor propertyFloor;

    @OneToOne(fetch=FetchType.EAGER , cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "PROP_AMNTS_PKID", unique = true)
    private PropertyAmenities propertyAmenities;

    @OneToOne(fetch=FetchType.EAGER , cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "PROP_GAL_PKID")
    private PropertyGallery propertyGallery;

    @OneToOne(fetch=FetchType.EAGER , cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "PROP_MAT_PKID", unique = true)
    private PropertyMaterial propertyMaterial;

    @OneToOne(fetch=FetchType.EAGER , cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "PROP_NEAR_PKID", unique = true)
    private PropertyNearBy propertyNearBy;

    @OneToOne(fetch=FetchType.EAGER , cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "PROP_BANK_PKID", unique = true, nullable = true)
    private PropertyBanks propertyBanks;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(
            mappedBy = "propertyMaster",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @Fetch(FetchMode.SELECT)
    private Set<PropertyInquiry> inquiries;

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

    public PropertyAmenities getPropertyAmenities() {
        return propertyAmenities;
    }

    public void setPropertyAmenities(PropertyAmenities propertyAmenities) {
        this.propertyAmenities = propertyAmenities;
    }

    public PropertyFloor getPropertyFloor() {
        return propertyFloor;
    }

    public void setPropertyFloor(PropertyFloor propertyFloor) {
        this.propertyFloor = propertyFloor;
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

    public PropertyNearBy getPropertyNearBy() {
        return propertyNearBy;
    }

    public void setPropertyNearBy(PropertyNearBy propertyNearBy) {
        this.propertyNearBy = propertyNearBy;
    }

    public PropertyBanks getPropertyBanks() {
        return propertyBanks;
    }

    public void setPropertyBanks(PropertyBanks propertyBanks) {
        this.propertyBanks = propertyBanks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<PropertyInquiry> getInquiries() {
        return inquiries;
    }

    public void setInquiries(Set<PropertyInquiry> inquiries) {
        this.inquiries = inquiries;
    }

    @Override
    public String toString() {
        return "PropertyMaster{" +
                "id=" + id +
                ", propId='" + propId + '\'' +
                ", progress=" + progress +
                ", isActive=" + isActive +
                ", isVerified=" + isVerified +
                ", totalValue=" + totalValue +
                ", isVerified=" + inactiveComments +
                ", verifyComments=" + verifyComments +
                ", propertyBasic=" + propertyBasic +
                ", user=" + user +
                ", inquiries=" + inquiries +
                '}';
    }

    public int getProfilePercentage() {
        int counter = 0;
        boolean isRent = (this.propertyBasic != null && PROP_TYPE_RENT.equalsIgnoreCase(this.propertyBasic.getRentSale())) ? true : false;
        double divider = isRent ? 5 : 7;
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                if(PROPERTY_SUB_LIST.contains(field.getName())) {
                    if(isRent){
                        if((!"propertyMaterial".equals(field.getName()) || !"propertyBanks".equals(field.getName())) && field.get(this) != null) {
                            counter++;
                        }
                    } else if(field.get(this) != null) {
                        counter++;
                    }
                }
            }

        } catch (Exception e) {

        }

        return CommonUtil.roundToTwoDecimal((counter / divider)*100);
    }
}
