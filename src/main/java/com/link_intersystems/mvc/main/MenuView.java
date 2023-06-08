package com.link_intersystems.mvc.main;

import com.link_intersystems.mvc.View;
import com.link_intersystems.mvc.action.menu.MenuContribution;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class MenuView extends View {

    private JMenu fileMenu;
    private JMenuBar menuBar = new JMenuBar();
    private ApplicationView applicationView;

    MenuView(ApplicationView applicationView) {
        super(applicationView);
        this.applicationView = applicationView;
        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
    }

    public void setMenuContributions(List<MenuContribution> menuContributions) {
        for (MenuContribution menuContribution : menuContributions) {
            String menuPath = menuContribution.getMenuPath();

            if (menuPath.equals("file")) {
                fileMenu.add(menuContribution.getAction(applicationView));
            }
        }
    }

    public void install(Consumer<JMenuBar> menuBarInstaller) {
        menuBarInstaller.accept(menuBar);
    }

    public void open() {

    }

    @Override
    public Component createComponent() {
        return null;
    }
}
