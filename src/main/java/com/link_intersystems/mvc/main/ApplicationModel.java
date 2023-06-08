package com.link_intersystems.mvc.main;

public class ApplicationModel {

    private String applicationName;
    private String username;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return applicationName + " [" + username + "]";
    }
}
