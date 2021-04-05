package com.portal.app.payload.response;

import java.util.Set;

public class PropertyBanks {

    private Long id;

    private Long pId;

    private String propId;

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

    public String getPropId() {
        return propId;
    }

    public void setPropId(String propId) {
        this.propId = propId;
    }

    public Set<String> getSelectedBanks() {
        return selectedBanks;
    }

    public void setSelectedBanks(Set<String> selectedBanks) {
        this.selectedBanks = selectedBanks;
    }

    @Override
    public String toString() {
        return "PropertyBanks{" +
                "id=" + id +
                ", pId=" + pId +
                ", propId=" + propId +
                ", selectedBanks=" + selectedBanks +
                '}';
    }
}
