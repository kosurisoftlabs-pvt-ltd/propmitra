package com.portal.app.payload.request;

import java.io.Serializable;

public class ChangePwd implements Serializable {
    private static final long serialVersionUID = 6264064902113870219L;

    private String currentPass;
    private String newPass;

    public String getCurrentPass() {
        return currentPass;
    }

    public void setCurrentPass(String currentPass) {
        this.currentPass = currentPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
