package com.portal.app.payload.request;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

public class FloorRequest {

    private Long id;

    private Long pId;

    //@NotBlank
    private Integer noFloors;

    //@NotBlank
    private String noBeds;

    //@NotBlank
    private Integer floorNo;

    //@NotBlank
    private Double areaSft;

    //@NotBlank
    private Double priceSft;

    private MultipartFile[] files;

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

    public Integer getNoFloors() {
        return noFloors;
    }

    public void setNoFloors(Integer noFloors) {
        this.noFloors = noFloors;
    }

    public String getNoBeds() {
        return noBeds;
    }

    public void setNoBeds(String noBeds) {
        this.noBeds = noBeds;
    }

    public Integer getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(Integer floorNo) {
        this.floorNo = floorNo;
    }

    public Double getAreaSft() {
        return areaSft;
    }

    public void setAreaSft(Double areaSft) {
        this.areaSft = areaSft;
    }

    public Double getPriceSft() {
        return priceSft;
    }

    public void setPriceSft(Double priceSft) {
        this.priceSft = priceSft;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "FloorRequest{" +
                "id=" + id +
                ", pId=" + pId +
                ", noFloors=" + noFloors +
                ", noBeds='" + noBeds + '\'' +
                ", floorNo=" + floorNo +
                ", areaSft=" + areaSft +
                ", priceSft=" + priceSft +
                '}';
    }
}
