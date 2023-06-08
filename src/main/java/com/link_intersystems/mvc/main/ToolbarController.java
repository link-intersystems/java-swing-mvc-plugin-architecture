package com.link_intersystems.mvc.main;

import com.link_intersystems.mvc.AbstractTaskAction2;
import com.link_intersystems.mvc.action.menu.MenuContribution;
import com.link_intersystems.swing.action.TaskProgress;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class ToolbarController extends AbstractTaskAction2<List<MenuContribution>, Void> {

    @Override
    protected List<MenuContribution> doInBackground(TaskProgress<Void> taskProgress) throws Exception {
        return ServiceLoader.load(MenuContribution.class).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }

}
