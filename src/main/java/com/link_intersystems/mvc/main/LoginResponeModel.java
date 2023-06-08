package com.link_intersystems.mvc.main;

public class LoginResponeModel {
    private final String appName;
    private final String username;

    public LoginResponeModel(String appName, String username) {

        this.appName = appName;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getAppName() {
        return appName;
    }
}
