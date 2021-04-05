package com.portal.app.payload;

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String expiresIn;
    private String hasRole;

    public JwtAuthenticationResponse(String accessToken, String expiresIn, String hasRole) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.hasRole = hasRole;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getHasRole() {
        return hasRole;
    }

    public void setHasRole(String hasRole) {
        this.hasRole = hasRole;
    }
}
