package com.portal.app.model;

import com.portal.app.model.audit.DateAudit;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "notifications")
public class VerificationToken extends DateAudit {

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_VERIFIED = "VERIFIED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public VerificationToken(){
        this.token = UUID.randomUUID().toString();
        this.status = STATUS_PENDING;
    }

    public VerificationToken(String token){
        this.token = token;
        this.status = STATUS_PENDING;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
