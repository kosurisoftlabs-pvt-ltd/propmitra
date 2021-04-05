package com.portal.app.payload.request;

import java.util.Set;

public class BankLoanRequest {

    private Long id;

    private Long pId;

    private Set<String> selectedBanks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Set<String> getSelectedBanks() {
        return selectedBanks;
    }

    public void setSelectedBanks(Set<String> selectedBanks) {
        this.selectedBanks = selectedBanks;
    }

    @Override
    public String toString() {
        return "BankLoanRequest{" +
                "id=" + id +
                ", pId=" + pId +
                ", selectedBanks=" + selectedBanks +
                '}';
    }
}
