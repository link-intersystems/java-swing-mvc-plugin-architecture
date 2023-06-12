package com.link_intersystems.fileeditor.main;

import com.link_intersystems.fileeditor.services.login.UserModel;

public class ApplicationModel {

    private String applicationName;
    private UserModel userModel;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getUsername() {
        if (userModel == null) {
            return "";
        }
        return userModel.getUsername();
    }


    public String getTitle() {
        return applicationName + " [" + getUsername() + "]";
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }
}
