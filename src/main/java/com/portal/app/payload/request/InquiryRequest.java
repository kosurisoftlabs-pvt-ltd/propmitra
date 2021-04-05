package com.portal.app.payload.request;

import javax.validation.constraints.NotBlank;

public class InquiryRequest {

    private Long pid;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String contact;

    @NotBlank
    private String message;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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

    @Override
    public String toString() {
        return "InquiryRequest{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
