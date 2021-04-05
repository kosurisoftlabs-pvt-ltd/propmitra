package com.portal.app.payload.request;

import java.util.Arrays;

public class SearchRequest {

    private String[] keywords;

    private String location;

    private String rentOrSale;

    private String minPrice;

    private String maxPrice;

    private String[] bhk;

    private String[] propType;

    private String[] listBy;

    private String[] propStatus;

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRentOrSale() {
        return rentOrSale;
    }

    public void setRentOrSale(String rentOrSale) {
        this.rentOrSale = rentOrSale;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String[] getBhk() {
        return bhk;
    }

    public void setBhk(String[] bhk) {
        this.bhk = bhk;
    }

    public String[] getPropType() {
        return propType;
    }

    public void setPropType(String[] propType) {
        this.propType = propType;
    }

    public String[] getListBy() {
        return listBy;
    }

    public void setListBy(String[] listBy) {
        this.listBy = listBy;
    }

    public String[] getPropStatus() {
        return propStatus;
    }

    public void setPropStatus(String[] propStatus) {
        this.propStatus = propStatus;
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "keywords='" + keywords + '\'' +
                ", location='" + location + '\'' +
                ", rentOrSale='" + rentOrSale + '\'' +
                ", minPrice=" + minPrice + '\'' +
                ", maxPrice=" + maxPrice + '\'' +
                ", bhk=" + Arrays.toString(bhk) +
                ", propType=" + Arrays.toString(propType) +
                ", listBy=" + Arrays.toString(listBy) +
                ", propStatus=" + Arrays.toString(propStatus) +
                '}';
    }
}
