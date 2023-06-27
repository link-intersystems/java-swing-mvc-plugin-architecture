package com.link_intersystems.fileeditor.main.viewsite;

import com.link_intersystems.swing.view.RootViewSite;
import com.link_intersystems.util.context.DefaultContext;

public class ViewSiteExample {

    public static void main(String[] args) {
        ViewSiteExampleView viewSiteExampleView = new ViewSiteExampleView();

        RootViewSite rootViewSite = new RootViewSite(new DefaultContext());
        viewSiteExampleView.install(rootViewSite);
    }

}
