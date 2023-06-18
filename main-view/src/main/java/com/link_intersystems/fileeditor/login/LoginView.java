package com.link_intersystems.fileeditor.login;

import com.link_intersystems.fileeditor.main.ApplicationModel;
import com.link_intersystems.fileeditor.main.ApplicationView;
import com.link_intersystems.fileeditor.services.login.LoginResponseModel;
import com.link_intersystems.fileeditor.services.login.LoginService;
import com.link_intersystems.fileeditor.services.login.UserModel;
import com.link_intersystems.swing.action.concurrent.TaskActionListener;
import com.link_intersystems.swing.view.AbstractView;
import com.link_intersystems.swing.view.ViewSite;
import com.link_intersystems.util.context.CloseContext;
import com.link_intersystems.util.context.Context;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class LoginView extends AbstractView implements TaskActionListener<LoginResponseModel, Integer> {

    private LoginAction loginAction;
    private JDialog dialog;
    private JProgressBar progressBar;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JButton loginButton;
    private Optional<TimerTask> resetProgressTask = Optional.empty();


    @Override
    public void doInstall(ViewSite viewSite) {
        loginAction = createLoginAction(viewSite);

        dialog = createComponent();
        viewSite.setComponent(dialog);
    }

    private LoginAction createLoginAction(ViewSite viewSite) {
        LoginService loginService = viewSite.get(LoginService.class);
        return createLoginAction(loginService);
    }

    protected LoginAction createLoginAction(LoginService loginService) {
        LoginAction loginAction = new LoginAction(loginService);
        loginAction.putValue(Action.NAME, "Login");
        loginAction.setTaskActionListener(this);
        return loginAction;
    }

    @Override
    public void uninstall() {
        if (dialog == null) {
            return;
        }

        dialog.dispose();
        loginAction.setTaskActionListener(null);
        loginAction = null;
        usernameField = null;
        passwordField = null;
        loginButton = null;
        dialog = null;
    }

    private JDialog createComponent() {
        LoginModel loginModel = getLoginModel();

        JDialog dialog = new JDialog();
        dialog.setSize(new Dimension(500, 180));
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ViewSite viewSite = getViewSite();
                viewSite.get(CloseContext.class).close();
                uninstall();
            }
        });

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

    private LoginModel getLoginModel() {
        return loginAction.getLoginModel();
    }

    @Override
    public void aboutToRun() {
        resetProgressTask.ifPresent(TimerTask::cancel);
        resetProgress();
    }

    private void resetProgress() {
        progressBar.setString("");
        Color foreground = new JProgressBar().getForeground();
        progressBar.setForeground(foreground);
        progressBar.setStringPainted(false);
    }

    @Override
    public void done(LoginResponseModel result) {
        if (result.isLoginSuccessful()) {
            try {
                UserModel userModel = putUserModel(result);
                ApplicationModel applicationModel = createApplicationModel(userModel);

                ApplicationView applicationView = new ApplicationView();
                applicationView.setApplicationModel(applicationModel);

                applicationView.install(getViewSite());
            } finally {
                this.uninstall();
            }
        } else {
            progressBar.setString("Login failed!");
            progressBar.setForeground(new Color(255, 50, 50));
            progressBar.setStringPainted(true);

            TimerTask resetProgressTask = new TimerTask() {
                @Override
                public void run() {
                    resetProgress();
                    LoginView.this.resetProgressTask = Optional.empty();
                }
            };
            this.resetProgressTask = Optional.of(resetProgressTask);

            schedule(resetProgressTask);
        }
    }

    protected void schedule(TimerTask resetProgress) {
        Timer timer = getViewSite().get(Timer.class);
        timer.schedule(resetProgress, 5000);
    }

    JButton getLoginButton() {
        return loginButton;
    }

    JPasswordField getPasswordField() {
        return passwordField;
    }

    JTextField getUsernameField() {
        return usernameField;
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


}
