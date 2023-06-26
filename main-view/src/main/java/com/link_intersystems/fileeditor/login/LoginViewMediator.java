package com.link_intersystems.fileeditor.login;

import com.link_intersystems.fileeditor.main.ApplicationModel;
import com.link_intersystems.fileeditor.main.ApplicationView;
import com.link_intersystems.fileeditor.services.login.LoginResponseModel;
import com.link_intersystems.fileeditor.services.login.UserModel;
import com.link_intersystems.swing.action.concurrent.TaskActionListener;
import com.link_intersystems.swing.view.ViewSite;
import com.link_intersystems.util.context.Context;

import javax.swing.*;
import java.awt.*;
import java.util.List;
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

    private BoundedRangeModel getProgressModel() {
        LoginModel loginModel = loginView.getLoginModel();
        return loginModel.getProgressModel();
    }

    private void resetProgress() {
        JProgressBar progressBar = loginView.getProgressBar();
        progressBar.setString(null);
        Color foreground = new JProgressBar().getForeground();
        progressBar.setForeground(foreground);
        progressBar.setStringPainted(false);

        BoundedRangeModel progressModel = getProgressModel();

        progressModel.setMinimum(0);
        progressModel.setMaximum(100);
        progressModel.setValue(0);

        resetProgressFuture = null;
    }

    @Override
    public void publishIntermediateResults(List<Integer> chunks) {
        BoundedRangeModel progressModel = getProgressModel();
        progressModel.setValue(progressModel.getValue() + chunks.size());
    }

    @Override
    public void done(LoginResponseModel result) {
        if (result.isLoginSuccessful()) {
            onSuccessfulLogin(result);
        } else {
            onLoginFailed();
        }
    }

    private void onSuccessfulLogin(LoginResponseModel result) {
        try {
            UserModel userModel = putUserModel(result);
            ApplicationModel applicationModel = createApplicationModel(userModel);

            installApplicationView(applicationModel);
        } finally {
            loginView.uninstall();
        }
    }

    private UserModel putUserModel(LoginResponseModel result) {
        UserModel userModel = new UserModel();
        userModel.setUsername(result.getUsername());


        Context viewContext = viewSite.getViewContext();
        viewContext.put(UserModel.class, userModel);

        return userModel;
    }

    private ApplicationModel createApplicationModel(UserModel userModel) {
        ApplicationModel applicationModel = new ApplicationModel();
        applicationModel.setApplicationName("File Editor");
        applicationModel.setUserModel(userModel);
        return applicationModel;
    }

    protected void installApplicationView(ApplicationModel applicationModel) {
        ApplicationView applicationView = new ApplicationView();
        applicationView.setApplicationModel(applicationModel);

        ViewSite viewSite = loginView.getApplicationViewSite();
        applicationView.install(viewSite);
    }

    private void onLoginFailed() {
        JProgressBar progressBar = loginView.getProgressBar();
        progressBar.setString("Login failed!");
        progressBar.setForeground(new Color(255, 50, 50));
        progressBar.setStringPainted(true);

        schedule(this::resetProgress);
    }

    private void schedule(Runnable resetProgress) {
        Context viewContext = viewSite.getViewContext();
        ScheduledExecutorService executorService = viewContext.get(ScheduledExecutorService.class);
        resetProgressFuture = executorService.schedule(resetProgress, 5, SECONDS);
    }
}
