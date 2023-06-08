package com.link_intersystems.mvc.main;

import com.link_intersystems.mvc.AbstractTaskAction2;
import com.link_intersystems.mvc.View;
import com.link_intersystems.swing.action.TaskProgress;

import javax.swing.*;
import java.util.List;

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

        return new LoginResponeModel("Test Application Name", "rene.link");
    }

}
