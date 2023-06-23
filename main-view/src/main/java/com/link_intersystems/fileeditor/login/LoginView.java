package com.link_intersystems.fileeditor.login;

import com.link_intersystems.fileeditor.services.login.LoginService;
import com.link_intersystems.swing.view.RootViewSite;
import com.link_intersystems.swing.view.ViewSite;
import com.link_intersystems.swing.view.window.WindowView;
import com.link_intersystems.util.context.Context;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;

public class LoginView extends WindowView {

    private LoginAction loginAction;
    private JProgressBar progressBar;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JButton loginButton;

    protected Window createWindow(ViewSite viewSite) {
        loginAction = getLoginAction(viewSite);
        LoginModel loginModel = getLoginModel();

        JDialog dialog = new JDialog();
        dialog.setTitle("Login");
        dialog.setSize(new Dimension(500, 180));
        dialog.setLocationRelativeTo(null);

        Container contentPane = dialog.getContentPane();

        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        contentPane.add(new JLabel("Username"), gbc);

        Document usernameDocument = loginModel.getUsernameDocument();
        usernameField = new JTextField();
        usernameField.setDocument(usernameDocument);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        contentPane.add(new JLabel("Password"), gbc);


        passwordField = new JPasswordField();
        Document passwordDocument = loginModel.getPasswordDocument();
        passwordField.setDocument(passwordDocument);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(passwordField, gbc);

        loginButton = new JButton(loginAction);

        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 2;
        contentPane.add(loginButton, gbc);


        progressBar = new JProgressBar();

        progressBar.setModel(loginModel.getProgressModel());
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.weighty = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        contentPane.add(progressBar, gbc);

        return dialog;
    }


    private LoginAction getLoginAction(ViewSite viewSite) {
        Context viewContext = viewSite.getViewContext();
        LoginService loginService = viewContext.get(LoginService.class);
        return getLoginAction(loginService);
    }

    protected LoginAction getLoginAction(LoginService loginService) {
        LoginAction loginAction = createLoginAction(loginService);
        loginAction.putValue(Action.NAME, "Login");

        LoginViewMediator loginViewMediator = new LoginViewMediator(this, getViewSite());
        loginAction.setTaskActionListener(loginViewMediator);

        return loginAction;
    }

    protected LoginAction createLoginAction(LoginService loginService) {
        return new LoginAction(loginService);
    }

    @Override
    protected void doUninstall(ViewSite viewSite) {
        super.doUninstall(viewSite);
        loginAction.setTaskActionListener(null);
        loginAction = null;
        usernameField = null;
        passwordField = null;
        loginButton = null;
    }

    void setUsername(String username) {
        usernameField.setText(username);
    }

    void setPassword(String password) {
        passwordField.setText(password);
    }

    void performLogin() {
        loginButton.doClick();
    }

    JProgressBar getProgressBar() {
        return progressBar;
    }

    protected ViewSite getApplicationViewSite() {
        Context viewContext = getViewSite().getViewContext();
        return new RootViewSite(viewContext);
    }

    LoginModel getLoginModel() {
        return loginAction.getLoginModel();
    }
}
