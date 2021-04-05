package com.portal.app.model;

import com.portal.app.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "prop_banks")
public class PropertyBanks extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROP_BANK_PKID")
    private Long id;

    @NotNull
    @ElementCollection
    @Column(name = "PROP_BANK_SELECTED")
    private Set<String> selectedBanks = new HashSet<>();

    @OneToOne(mappedBy = "propertyBanks")
    private PropertyMaster propertyMaster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<String> getSelectedBanks() {
        return selectedBanks;
    }

    public void setSelectedBanks(Set<String> selectedBanks) {
        this.selectedBanks = selectedBanks;
    }

    public PropertyMaster getPropertyMaster() {
        return propertyMaster;
    }

    public void setPropertyMaster(PropertyMaster propertyMaster) {
        this.propertyMaster = propertyMaster;
    }

    @Override
    public String toString() {
        return "PropertyBanks{" +
                "id=" + id +
                ", selectedBanks=" + selectedBanks +
                ", propertyMaster=" + propertyMaster +
                '}';
    }
}
