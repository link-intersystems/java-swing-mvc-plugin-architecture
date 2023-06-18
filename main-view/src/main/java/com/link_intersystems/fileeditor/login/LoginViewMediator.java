package com.link_intersystems.fileeditor.login;

import com.link_intersystems.fileeditor.main.ApplicationModel;
import com.link_intersystems.fileeditor.main.ApplicationView;
import com.link_intersystems.fileeditor.services.login.LoginResponseModel;
import com.link_intersystems.fileeditor.services.login.UserModel;
import com.link_intersystems.swing.action.concurrent.TaskActionListener;
import com.link_intersystems.swing.view.RootViewSite;
import com.link_intersystems.swing.view.ViewSite;
import com.link_intersystems.util.context.Context;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.*;

public class LoginViewMediator implements TaskActionListener<LoginResponseModel, Integer> {

    private LoginView loginView;
    private ViewSite viewSite;
    private ScheduledFuture<?> resetProgressFuture;

    public LoginViewMediator(LoginView loginView, ViewSite viewSite) {
        this.loginView = loginView;
        this.viewSite = viewSite;
    }

    @Override
    public void aboutToRun() {
        if (resetProgressFuture != null) {
            resetProgressFuture.cancel(true);
        }
        resetProgress();
    }

    private void resetProgress() {
        JProgressBar progressBar = loginView.getProgressBar();
        progressBar.setString("");
        Color foreground = new JProgressBar().getForeground();
        progressBar.setForeground(foreground);
        progressBar.setStringPainted(false);

        resetProgressFuture = null;
    }

    @Override
    public void done(LoginResponseModel result) {
        if (result.isLoginSuccessful()) {
            try {
                UserModel userModel = putUserModel(result);
                ApplicationModel applicationModel = createApplicationModel(userModel);

                ApplicationView applicationView = new ApplicationView();
                applicationView.setApplicationModel(applicationModel);

                RootViewSite rootViewSite = new RootViewSite(getViewSite());
                applicationView.install(rootViewSite);
            } finally {
                loginView.uninstall();
            }
        } else {
            JProgressBar progressBar = loginView.getProgressBar();
            progressBar.setString("Login failed!");
            progressBar.setForeground(new Color(255, 50, 50));
            progressBar.setStringPainted(true);

            schedule(this::resetProgress);
        }
    }

    private ViewSite getViewSite() {
        return viewSite;
    }

    private ApplicationModel createApplicationModel(UserModel userModel) {
        ApplicationModel applicationModel = new ApplicationModel();
        applicationModel.setApplicationName("File Editor");
        applicationModel.setUserModel(userModel);
        return applicationModel;
    }

    private UserModel putUserModel(LoginResponseModel result) {
        UserModel userModel = new UserModel();
        userModel.setUsername(result.getUsername());

        Context viewSite = getViewSite();
        viewSite.put(UserModel.class, userModel);

        return userModel;
    }

    private void schedule(Runnable resetProgress) {
        ScheduledExecutorService executorService = getViewSite().get(ScheduledExecutorService.class);
        resetProgressFuture = executorService.schedule(resetProgress, 5, SECONDS);
    }
}
