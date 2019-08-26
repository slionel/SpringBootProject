package com.zsj.DTO;

/**
 * @author zsj55
 */
public class LoginDTO {
    private String baseUsername;
    private String basePassword;
    private boolean rememberMe;

    public String getBaseUsername() {
        return baseUsername;
    }

    public void setBaseUsername(String baseUsername) {
        this.baseUsername = baseUsername;
    }

    public String getBasePassword() {
        return basePassword;
    }

    public void setBasePassword(String basePassword) {
        this.basePassword = basePassword;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
