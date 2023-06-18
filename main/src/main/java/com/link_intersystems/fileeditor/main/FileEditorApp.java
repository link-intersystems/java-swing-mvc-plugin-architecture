package com.link_intersystems.fileeditor.main;

import com.link_intersystems.fileeditor.login.LoginView;
import com.link_intersystems.fileeditor.services.login.LoginService;
import com.link_intersystems.swing.view.RootViewSite;
import com.link_intersystems.util.context.CloseContext;
import com.link_intersystems.util.context.DefaultContext;

import java.util.Timer;

public class FileEditorApp {

    public static void main(String[] args) {
        LoginView loginView = new LoginView();

        DefaultContext viewContext = new DefaultContext();
        RootViewSite rootViewSite = new RootViewSite(viewContext);

        viewContext.put(LoginService.class, new LoginService());
        Timer timer = new Timer();
        viewContext.put(Timer.class, timer);
        viewContext.put(CloseContext.class, timer::cancel);

        loginView.install(rootViewSite);
    }

}
