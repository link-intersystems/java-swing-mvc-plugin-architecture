package com.link_intersystems.fileeditor.init;

import com.link_intersystems.fileeditor.action.AbstractTaskAction2;
import com.link_intersystems.fileeditor.services.login.LoginResponeModel;
import com.link_intersystems.swing.action.TaskProgress;

import javax.swing.*;

public class AppInitAction extends AbstractTaskAction2<LoginResponeModel, Integer> {

    private AppInitModel appInitModel = new AppInitModel();

    public AppInitModel getAppInitModel() {
        return appInitModel;
    }

    @Override
    protected void prepareExecution() throws Exception {
        appInitModel.setAppName("Test app");

        BoundedRangeModel initProgress = appInitModel.getInitProgress();

        initProgress.setMinimum(0);
        initProgress.setMaximum(100);
        initProgress.setValue(0);
    }

    @Override
    protected LoginResponeModel doInBackground(TaskProgress<Integer> taskProgress) throws Exception {
        for (int i = 0; i <= 100; i++) {
            Thread.sleep(10);
            taskProgress.publish(i);
        }

        return new LoginResponeModel("rene.link");
    }

}
