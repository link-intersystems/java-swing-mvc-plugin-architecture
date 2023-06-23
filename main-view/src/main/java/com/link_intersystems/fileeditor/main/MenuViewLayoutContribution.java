package com.link_intersystems.fileeditor.main;

import com.link_intersystems.fileeditor.menu.MenuView;
import com.link_intersystems.swing.view.layout.AbstractViewLayoutContribution;
import com.link_intersystems.swing.view.layout.ViewLayout;

public class MenuViewLayoutContribution extends AbstractViewLayoutContribution {

    @Override
    public void doInstall(ViewLayout viewLayout) {
        viewLayout.install("menuSite", new MenuView());
    }
}
