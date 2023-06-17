package com.link_intersystems.fileeditor.services.login;

public class LoginResponseModel {
    private final String username;
    private boolean loginSuccessful;

    public LoginResponseModel(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    void setLoginSuccessful(boolean loginSuccessful) {
        this.loginSuccessful = loginSuccessful;
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }
}
