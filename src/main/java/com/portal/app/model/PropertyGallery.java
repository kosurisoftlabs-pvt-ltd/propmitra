package com.portal.app.model;

import com.portal.app.model.audit.UserDateAudit;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "prop_gallery")
public class PropertyGallery extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROP_GAL_PKID")
    private Long id;

    @OneToOne(mappedBy = "propertyGallery")
    private PropertyMaster propertyMaster;

    @OneToMany(
            mappedBy = "propertyGallery",
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
