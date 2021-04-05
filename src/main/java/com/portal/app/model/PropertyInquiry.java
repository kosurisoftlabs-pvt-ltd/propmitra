package com.portal.app.model;

import com.portal.app.model.audit.UserDateAudit;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "prop_inquiry")
public class PropertyInquiry extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROP_INQ_PKID")
    private Long id;

    @NotNull
    @Column(name = "PROP_INQ_NAME")
    private String name;

    @NotNull
    @Column(name = "PROP_INQ_MAIL")
    private String email;

    @NotNull
    @Column(name = "PROP_INQ_CONTACT")
    private String contact;

    @NotNull
    @Column(name = "PROP_INQ_MSG")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "PROP_MSTR_PKID", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PropertyMaster propertyMaster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PropertyMaster getPropertyMaster() {
        return propertyMaster;
    }

    public void setPropertyMaster(PropertyMaster propertyMaster) {
        this.propertyMaster = propertyMaster;
    }
}
