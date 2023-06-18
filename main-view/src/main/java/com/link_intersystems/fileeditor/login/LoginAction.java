package com.link_intersystems.fileeditor.login;

import com.link_intersystems.fileeditor.services.login.LoginResponseModel;
import com.link_intersystems.fileeditor.services.login.LoginService;
import com.link_intersystems.swing.action.concurrent.DefaultTaskAction;
import com.link_intersystems.util.concurrent.task.TaskProgress;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.util.List;

import static java.util.Objects.*;

public class LoginAction extends DefaultTaskAction<LoginResponseModel, Integer> {

    private LoginModel loginModel = new LoginModel();

    private LoginService loginService;

    public LoginAction(LoginService loginService) {
        this.loginService = requireNonNull(loginService);
    }

    public LoginModel getLoginModel() {
        return loginModel;
    }

    @Override
    protected void prepareExecution() throws Exception {
        super.prepareExecution();
        BoundedRangeModel initProgress = loginModel.getProgressModel();

        initProgress.setMinimum(0);
        initProgress.setMaximum(100);
        initProgress.setValue(0);
    }

    @Override
    protected LoginResponseModel doInBackground(TaskProgress<Integer> taskProgress) throws Exception {
        for (int i = 0; i <= 100; i++) {
            sleep();
            taskProgress.publish(i);
        }

        String username = getUsername();
        String password = getPassword();
        return loginService.login(username, password);
    }

    protected void sleep() throws InterruptedException {
        Thread.sleep(10);
    }

    @Override
    public void publishIntermediateResults(List<Integer> chunks) {
        super.publishIntermediateResults(chunks);

        LoginModel loginModel = getLoginModel();
        BoundedRangeModel initProgress = loginModel.getProgressModel();
        initProgress.setValue(initProgress.getValue() + chunks.size());
    }

    private String getUsername() {
        Document usernameDocument = getLoginModel().getUsernameDocument();
        return getText(usernameDocument);
    }

    private String getText(Document document) {
        try {
            return document.getText(0, document.getLength());
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    private String getPassword() {
        Document passwordDocument = getLoginModel().getPasswordDocument();
        return getText(passwordDocument);
    }

}
