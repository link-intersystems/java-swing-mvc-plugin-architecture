package com.link_intersystems.fileeditor.login;

import com.link_intersystems.fileeditor.services.login.LoginService;
import com.link_intersystems.swing.action.concurrent.DirectTaskExecutor;

import java.util.TimerTask;

class TestableLoginView extends LoginView {
    @Override
    protected LoginAction createLoginAction(LoginService loginService) {
        LoginAction loginAction = super.createLoginAction(loginService);
        loginAction.setTaskExecutor(new DirectTaskExecutor());
        return loginAction;
    }

    @Override
    protected void schedule(TimerTask resetProgress) {
        resetProgress.run();
    }
}
