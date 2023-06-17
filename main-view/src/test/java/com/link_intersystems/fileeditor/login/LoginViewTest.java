package com.link_intersystems.fileeditor.login;

import com.link_intersystems.fileeditor.services.login.LoginService;
import com.link_intersystems.fileeditor.services.login.LoginServiceMock;
import com.link_intersystems.swing.view.NullViewContent;
import com.link_intersystems.swing.view.RootSite;
import com.link_intersystems.swing.view.ViewContent;
import com.link_intersystems.swing.view.Site;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

class LoginViewTest {

    private LoginService loginService;
    private TestableLoginView loginView;
    private Site viewSite;

    @BeforeEach
    void setUp() {
        loginService = new LoginServiceMock();
        loginView = new TestableLoginView();

        viewSite = new RootSite() {
            @Override
            public ViewContent getViewContent() {
                return new NullViewContent();
            }

        };

        viewSite.put(LoginService.class, loginService);
    }

    @Test
    void login() {
        loginView.install(viewSite);

        loginView.getUsernameField().setText("name");
        loginView.getPasswordField().setText("name");

        JButton loginButton = loginView.getLoginButton();
        loginButton.doClick();

        loginView.uninstall();
    }

    @Test
    void loginFailed() {
        loginView.install(viewSite);

        loginView.getUsernameField().setText("name");
        loginView.getPasswordField().setText("");

        JButton loginButton = loginView.getLoginButton();
        loginButton.doClick();

        loginView.uninstall();
    }

}