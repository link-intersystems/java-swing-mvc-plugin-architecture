package com.link_intersystems.fileeditor.menu;

import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.concurrent.TaskActionListener;
import com.link_intersystems.swing.action.spi.ServiceLoaderAction;
import com.link_intersystems.swing.menu.MenuContribution;
import com.link_intersystems.swing.view.AbstractView;
import com.link_intersystems.swing.view.ViewContent;
import com.link_intersystems.swing.view.ViewSite;
import com.link_intersystems.util.context.Context;

import javax.swing.*;
import java.util.List;

public class MenuView extends AbstractView implements TaskActionListener<List<MenuContribution>, Void> {

    private JMenu fileMenu;
    private JMenuBar menuBar = new JMenuBar();

    @Override
    public void doInstall(ViewSite viewSite) {

        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        ViewContent viewContent = viewSite.getViewContent();
        viewContent.setComponent(menuBar);

        ServiceLoaderAction<MenuContribution> menuContributionAction = new ServiceLoaderAction<>(MenuContribution.class);
        menuContributionAction.setTaskActionListener(this);
        new ActionTrigger(this).performAction(menuContributionAction);
    }

    @Override
    public void done(List<MenuContribution> menuContributions) {
        menuContributions.forEach(this::addMenuContribution);
    }

    private void addMenuContribution(MenuContribution menuContribution) {
        String menuPath = menuContribution.getMenuPath();

        if (menuPath.equals("file")) {
            ViewSite viewSite = getViewSite();
            Context viewContext = viewSite.getViewContext();
            fileMenu.add(menuContribution.getAction(viewContext));
        }
    }
}
