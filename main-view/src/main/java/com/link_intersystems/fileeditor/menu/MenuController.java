package com.link_intersystems.fileeditor.menu;

import com.link_intersystems.swing.action.concurrent.DefaultTaskAction;
import com.link_intersystems.swing.menu.MenuContribution;
import com.link_intersystems.util.concurrent.task.TaskProgress;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class MenuController extends DefaultTaskAction<List<MenuContribution>, Void> {

    @Override
    protected List<MenuContribution> doInBackground(TaskProgress<Void> taskProgress) {
        return ServiceLoader.load(MenuContribution.class).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }

}
