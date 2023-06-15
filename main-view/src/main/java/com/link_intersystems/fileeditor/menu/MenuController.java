package com.link_intersystems.fileeditor.menu;

import com.link_intersystems.swing.action.AbstractTaskAction2;
import com.link_intersystems.swing.action.TaskProgress;
import com.link_intersystems.swing.menu.MenuContribution;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class MenuController extends AbstractTaskAction2<List<MenuContribution>, Void> {

    @Override
    protected List<MenuContribution> doInBackground(TaskProgress<Void> taskProgress) throws Exception {
        return ServiceLoader.load(MenuContribution.class).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }

}
