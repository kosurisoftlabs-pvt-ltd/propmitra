package com.portal.app.model;

import com.portal.app.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "prop_amenities")
public class PropertyAmenities extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROP_AMNTS_PKID")
    private Long id;

    @NotNull
    @ElementCollection
    @Column(name = "PROP_AMNTS_SPEC")
    private Set<String> specification = new HashSet<>();

    @OneToOne(mappedBy = "propertyAmenities")
    private PropertyMaster propertyMaster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<String> getSpecification() {
        return specification;
    }

    public void setSpecification(Set<String> specification) {
        this.specification = specification;
    }

    public PropertyMaster getPropertyMaster() {
        return propertyMaster;
    }

    public void setPropertyMaster(PropertyMaster propertyMaster) {
        this.propertyMaster = propertyMaster;
    }

    @Override
    public String toString() {
        return "PropertyAmenities{" +
                "id=" + id +
                ", specification='" + specification + '\'' +
                ", propertyMaster=" + propertyMaster +
                '}';
    }
}
