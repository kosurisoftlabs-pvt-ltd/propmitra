package com.portal.app.payload.response;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;

public class PropertyMetaResponse implements Serializable {

    private static final long serialVersionUID = 1461198858393315419L;

    private String rentSale;

    private Set<String> keywords;

    private Set<String> locations;

    private Double minPrice;

    private Double maxPrice;

    private Set<String> propTypes;

    private Set<String> propStatuses;

    private Set<String> propPostsBy;

    public String getRentSale() {
        return rentSale;
    }

    public void setRentSale(String rentSale) {
        this.rentSale = rentSale;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public Set<String> getLocations() {
        return locations;
    }

    public void setLocations(Set<String> locations) {
        this.locations = locations;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Set<String> getPropTypes() {
        return propTypes;
    }

    public void setPropTypes(Set<String> propTypes) {
        this.propTypes = propTypes;
    }

    public Set<String> getPropStatuses() {
        return propStatuses;
    }

    public void setPropStatuses(Set<String> propStatuses) {
        this.propStatuses = propStatuses;
    }

    public Set<String> getPropPostsBy() {
        return propPostsBy;
    }

    public void setPropPostsBy(Set<String> propPostsBy) {
        this.propPostsBy = propPostsBy;
    }

    @Override
    public String toString() {
        return "PropertyMetaResponse{" +
                "rentSale=" + rentSale +
                "keywords=" + keywords +
                ", locations=" + locations +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", propTypes=" + propTypes +
                ", propStatuses=" + propStatuses +
                ", propPostsBy=" + propPostsBy +
                '}';
    }
}
