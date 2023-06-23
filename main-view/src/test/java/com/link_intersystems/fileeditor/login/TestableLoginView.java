package com.link_intersystems.fileeditor.login;

import com.link_intersystems.fileeditor.services.login.LoginService;
import com.link_intersystems.swing.action.concurrent.DirectTaskExecutor;
import com.link_intersystems.swing.view.DefaultViewSite;
import com.link_intersystems.swing.view.RootViewSite;
import com.link_intersystems.swing.view.ViewContent;
import com.link_intersystems.swing.view.ViewSite;
import com.link_intersystems.util.context.DefaultContext;

class TestableLoginView extends LoginView {

    @Override
    protected LoginAction getLoginAction(LoginService loginService) {
        LoginAction loginAction = super.getLoginAction(loginService);
        loginAction.setTaskExecutor(new DirectTaskExecutor());
        return loginAction;
    }

    @Override
    protected LoginAction createLoginAction(LoginService loginService) {
        return new LoginAction(loginService) {
            @Override
            protected void sleep() throws InterruptedException {
            }
        };
    }

    @Override
    protected ViewSite getApplicationViewSite() {
        return new DefaultViewSite(ViewContent.nullInstance(), getViewSite());
    }
}
