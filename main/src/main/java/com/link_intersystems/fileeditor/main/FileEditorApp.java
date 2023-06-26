package com.link_intersystems.fileeditor.main;

import com.link_intersystems.fileeditor.login.LoginView;
import com.link_intersystems.fileeditor.services.login.LoginService;
import com.link_intersystems.swing.view.RootViewSite;
import com.link_intersystems.swing.view.window.WindowView;
import com.link_intersystems.util.context.DefaultContext;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class FileEditorApp {

    public static void main(String[] args) {
        LoginView loginView = new LoginView();

        DefaultContext viewContext = new DefaultContext();
        RootViewSite rootViewSite = new RootViewSite(viewContext);

        LoginService loginService = new LoginService();
        loginService.registerUser("rene", "link");
        viewContext.put(LoginService.class, loginService);

        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(2);
        viewContext.put(ScheduledExecutorService.class, scheduledExecutorService);
        viewContext.put(Action.class, WindowView.DEFAULT_CLOSE_ACTION, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    viewContext.close();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        loginView.install(rootViewSite);
    }

}

