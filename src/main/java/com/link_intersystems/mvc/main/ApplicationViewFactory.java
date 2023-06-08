package com.link_intersystems.mvc.main;

import com.link_intersystems.mvc.View;
import com.link_intersystems.mvc.ViewFactory;

public class ApplicationViewFactory implements ViewFactory {

    @Override
    public String getName() {
        return "mainView";
    }

    @Override
    public View createView(View parentView) {
        ApplicationView applicationView = new ApplicationView();
        ApplicationModel applicationModel = null;
        applicationView.setApplicationModel(applicationModel);

        applicationView.setToolbarController(null);

        return applicationView;
    }
}
