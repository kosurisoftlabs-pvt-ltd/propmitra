package com.portal.app.helper;

public class SearchCriteria {

    private String key;

    private String parentKey;

    private String operation;

    private Object value;

    public SearchCriteria() {
    }

    public SearchCriteria(String key, String parentKey, String operation, Object value) {
        this.key = key;
        this.parentKey = parentKey;
        this.operation = operation;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
