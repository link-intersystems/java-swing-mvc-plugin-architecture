package com.link_intersystems.mvc.main;

import javax.swing.*;

public class AppInitModel {

    private BoundedRangeModel initProgress = new DefaultBoundedRangeModel();
    private String appName;

    public BoundedRangeModel getInitProgress() {
        return initProgress;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }
}
