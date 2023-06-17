package com.link_intersystems.fileeditor.main;

import com.link_intersystems.fileeditor.login.LoginView;
import com.link_intersystems.fileeditor.services.login.LoginService;
import com.link_intersystems.swing.view.RootSite;
import com.link_intersystems.util.context.Context;

public class FileEditorApp {

    public static void main(String[] args) {

        LoginView loginView = new LoginView();

        RootSite rootViewSite = new RootSite();

        Context viewContext = rootViewSite.getViewContext();
        viewContext.put(new LoginService());

        loginView.install(rootViewSite);
    }
}
