package com.link_intersystems.mvc.main;

import com.link_intersystems.mvc.ActionCallback;
import com.link_intersystems.mvc.MainView;
import com.link_intersystems.swing.DimensionExt;
import com.link_intersystems.swing.action.ActionTrigger;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AppInitView extends MainView implements ActionCallback<LoginResponeModel, Integer> {

    private final JDialog dialog;
    private final JProgressBar progressBar;
    private ActionTrigger actionTrigger = new ActionTrigger(this);
    private AppInitAction appInitController;
    private AppInitModel appInitModel = new AppInitModel();


    public AppInitView() {
        super(null);

        dialog = new JDialog();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        DimensionExt dialogDim = new DimensionExt(500, 65);
        Point point = dialogDim.centerOn(screenSize);
        dialog.setBounds(new Rectangle(point, dialogDim));
        Container contentPane = dialog.getContentPane();
        progressBar = new JProgressBar();
        contentPane.add(progressBar);

        AppInitModel appInitModel = new AppInitModel();
        appInitModel.setAppName("Test");
    }

    public void open() {

        this.dialog.setVisible(true);
        actionTrigger.performAction(appInitController);
    }

    public void close() {
        dialog.dispose();
    }

    public void setAppInitController(AppInitAction appInitController) {
        this.appInitController = appInitController;
        appInitController.setCallback(this);
        this.appInitModel = appInitController.getAppInitModel();

        dialog.setTitle(appInitModel.getAppName());
        progressBar.setModel(appInitModel.getInitProgress());
    }

    @Override
    public void aboutToRun() {
        BoundedRangeModel initProgress = appInitModel.getInitProgress();
        initProgress.setMinimum(0);
        initProgress.setMaximum(100);
        initProgress.setValue(0);
    }

    @Override
    public void intermediateResults(List<Integer> chunks) {
        BoundedRangeModel initProgress = appInitModel.getInitProgress();
        initProgress.setValue(initProgress.getValue() + chunks.size());
    }

    @Override
    public void done(LoginResponeModel result) {
        ApplicationModel applicationModel = new ApplicationModel();
        applicationModel.setApplicationName(result.getAppName());
        applicationModel.setUsername(result.getUsername());

        ApplicationView applicationView = new ApplicationView();
        ToolbarController toolbarController = new ToolbarController();
        applicationView.setToolbarController(toolbarController);
        applicationView.open();

        close();
    }

    @Override
    public Component createComponent() {
        return dialog;
    }
}
