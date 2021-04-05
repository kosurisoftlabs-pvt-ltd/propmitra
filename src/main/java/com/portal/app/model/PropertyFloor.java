package com.portal.app.model;

import com.portal.app.model.audit.UserDateAudit;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "prop_floor")
public class PropertyFloor extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROP_FLOOR_PKID")
    private Long id;

    @NotNull
    @Column(name = "PROP_FLOOR_NO_OF_FLOORS")
    private Integer noFloors;

    @NotNull
    @Column(name = "PROP_FLOOR_NO_OF_BEDS")
    private String noBeds;

    @NotNull
    @Column(name = "PROP_FLOOR_NO")
    private Integer floorNo;

    @NotNull
    @Column(name = "PROP_FLOOR_AREA_SFT")
    private Double areaSft;

    @NotNull
    @Column(name = "PROP_FLOOR_PRICE_SFT")
    private Double priceSft;

    @OneToOne(mappedBy = "propertyFloor")
    private PropertyMaster propertyMaster;

    @OneToMany(
            mappedBy = "propertyFloor",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @Fetch(FetchMode.SELECT)
    private Set<Attachment> attachments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public PropertyMaster getPropertyMaster() {
        return propertyMaster;
    }

    public void setPropertyMaster(PropertyMaster propertyMaster) {
        this.propertyMaster = propertyMaster;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }
}
