package com.link_intersystems.fileeditor.main;

import com.link_intersystems.fileeditor.view.RootViewSite;
import com.link_intersystems.fileeditor.init.AppInitView;

public class FileEditorApp {

    public static void main(String[] args) {

        AppInitView appInitView = new AppInitView();

        RootViewSite rootViewSite = new RootViewSite() {
            @Override
            public <T> T getService(Class<T> type, String name) {
                return null;
            }
        };

        appInitView.install(rootViewSite);
    }
}
