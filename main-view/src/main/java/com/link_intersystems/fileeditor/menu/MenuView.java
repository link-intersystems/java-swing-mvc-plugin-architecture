package com.link_intersystems.fileeditor.menu;

import com.link_intersystems.swing.action.ActionCallback;
import com.link_intersystems.swing.menu.MenuContribution;
import com.link_intersystems.swing.action.spi.ServiceLoaderAction;
import com.link_intersystems.swing.context.ViewContext;
import com.link_intersystems.swing.view.AbstractView;
import com.link_intersystems.swing.view.ViewContent;
import com.link_intersystems.swing.view.ViewSite;
import com.link_intersystems.swing.action.ActionTrigger;

import javax.swing.*;
import java.util.List;

public class MenuView extends AbstractView implements ActionCallback<List<MenuContribution>, Void> {

    private JMenu fileMenu;
    private JMenuBar menuBar = new JMenuBar();

    @Override
    public void doInstall(ViewSite viewSite) {

        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        viewSite.setComponent(menuBar);

        ServiceLoaderAction<MenuContribution> menuContributionAction = new ServiceLoaderAction<>(MenuContribution.class);
        menuContributionAction.setCallback(this);
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
            fileMenu.add(menuContribution.getAction(viewSite));
        }
    }
}
