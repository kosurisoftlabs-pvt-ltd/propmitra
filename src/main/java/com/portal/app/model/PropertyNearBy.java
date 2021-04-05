package com.portal.app.model;

import com.portal.app.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "prop_nearby")
public class PropertyNearBy extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROP_NEAR_PKID")
    private Long id;

    @NotNull
    @ElementCollection
    @Column(name = "PROP_NEAR_HOSP")
    private Set<String> hospitals = new HashSet<>();

    @NotNull
    @ElementCollection
    @Column(name = "PROP_NEAR_SCHL")
    private Set<String> schools = new HashSet<>();

    @OneToOne(mappedBy = "propertyNearBy")
    private PropertyMaster propertyMaster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public PropertyMaster getPropertyMaster() {
        return propertyMaster;
    }

    public void setPropertyMaster(PropertyMaster propertyMaster) {
        this.propertyMaster = propertyMaster;
    }

    @Override
    public String toString() {
        return "PropertyNearBy{" +
                "id=" + id +
                ", hospitals=" + hospitals +
                ", schools=" + schools +
                ", propertyMaster=" + propertyMaster +
                '}';
    }
}
