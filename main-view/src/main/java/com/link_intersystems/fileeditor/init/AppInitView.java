package com.link_intersystems.fileeditor.init;

import com.link_intersystems.fileeditor.main.ApplicationModel;
import com.link_intersystems.fileeditor.main.ApplicationView;
import com.link_intersystems.fileeditor.services.login.LoginResponeModel;
import com.link_intersystems.fileeditor.services.login.UserModel;
import com.link_intersystems.swing.action.ActionCallback;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.context.ViewContext;
import com.link_intersystems.swing.view.AbstractView;
import com.link_intersystems.swing.view.RootViewSite;
import com.link_intersystems.swing.view.ViewSite;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AppInitView extends AbstractView implements ActionCallback<LoginResponeModel, Integer> {

    private ActionTrigger actionTrigger = new ActionTrigger(this);
    private AppInitAction appInitAction;
    private JDialog dialog;
    private ViewSite viewSite;

    public AppInitView() {
    }


    @Override
    public void doInstall(ViewSite viewSite) {
        this.viewSite = viewSite;
        appInitAction = new AppInitAction();
        appInitAction.setCallback(this);

        dialog = createComponent();
        dialog.setVisible(true);

        actionTrigger.performAction(appInitAction);
    }

    @Override
    public void uninstall() {
        if (dialog == null) {
            return;
        }

        dialog.dispose();
        appInitAction.setCallback(null);
        appInitAction = null;
        dialog = null;
        this.viewSite = null;
    }

    private JDialog createComponent() {
        JDialog dialog = new JDialog();
        dialog.setSize(new Dimension(500, 65));
        dialog.setLocationRelativeTo(null);

        Container contentPane = dialog.getContentPane();

        JProgressBar progressBar = new JProgressBar();
        AppInitModel appInitModel = getAppInitModel();
        progressBar.setModel(appInitModel.getInitProgress());
        contentPane.add(progressBar);

        return dialog;
    }

    @Override
    public void aboutToRun() {
        AppInitModel appInitModel = getAppInitModel();
        BoundedRangeModel initProgress = appInitModel.getInitProgress();
        initProgress.setMinimum(0);
        initProgress.setMaximum(100);
        initProgress.setValue(0);
    }

    private AppInitModel getAppInitModel() {
        return appInitAction.getAppInitModel();
    }

    @Override
    public void intermediateResults(List<Integer> chunks) {
        AppInitModel appInitModel = getAppInitModel();
        BoundedRangeModel initProgress = appInitModel.getInitProgress();
        initProgress.setValue(initProgress.getValue() + chunks.size());
    }

    @Override
    public void done(LoginResponeModel result) {
        try {
            UserModel userModel = putUserModel(result);
            ApplicationModel applicationModel = createApplicationModel(userModel);

            ApplicationView applicationView = new ApplicationView();
            applicationView.setApplicationModel(applicationModel);

            applicationView.install(new RootViewSite() {
                @Override
                public <T> T getService(Class<T> type, String name) {
                    return viewSite.getService(type, name);
                }
            });
        } finally {
            this.uninstall();
        }
    }

    private ApplicationModel createApplicationModel(UserModel userModel) {
        ApplicationModel applicationModel = new ApplicationModel();
        applicationModel.setApplicationName("File Editor");
        applicationModel.setUserModel(userModel);
        return applicationModel;
    }

    private UserModel putUserModel(LoginResponeModel result) {
        UserModel userModel = new UserModel();
        userModel.setUsername(result.getUsername());

        ViewContext viewContext = viewSite;
        viewContext.put(userModel);

        return userModel;
    }


}
