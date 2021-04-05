package com.portal.app.payload.request;

public class SMSRequest {

    private Long customerNumber;
    private String customerName;
    private String customerMessage;
    private Long ownerNumber;
    private String ownerName;
    private String ownerMessage;

    public Long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMessage() {
        return customerMessage;
    }

    public void setCustomerMessage(String customerMessage) {
        this.customerMessage = customerMessage;
    }

    public Long getOwnerNumber() {
        return ownerNumber;
    }

    public void setOwnerNumber(Long ownerNumber) {
        this.ownerNumber = ownerNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerMessage() {
        return ownerMessage;
    }

    public void setOwnerMessage(String ownerMessage) {
        this.ownerMessage = ownerMessage;
    }
}
