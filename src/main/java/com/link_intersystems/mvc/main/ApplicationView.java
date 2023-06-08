package com.link_intersystems.mvc.main;

import com.link_intersystems.mvc.ActionCallback;
import com.link_intersystems.mvc.View;
import com.link_intersystems.mvc.action.menu.MenuContribution;
import com.link_intersystems.swing.DimensionExt;
import com.link_intersystems.swing.DisplayResolution;
import com.link_intersystems.swing.action.ActionTrigger;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ApplicationView extends View implements ActionCallback<List<MenuContribution>, Void> {


    private final MenuView menuView;
    private JFrame frame = new JFrame();
    private JTabbedPane tabbedPane = new JTabbedPane();

    private ToolbarController toolbarController;

    public ApplicationView() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        menuView = new MenuView(this);
        menuView.install(menuBar -> frame.getContentPane().add(menuBar, BorderLayout.NORTH));
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    public void setToolbarController(ToolbarController toolbarController) {
        this.toolbarController = toolbarController;
        toolbarController.setCallback(this);
    }

    public void setApplicationModel(ApplicationModel applicationModel) {
        frame.setTitle(applicationModel.getTitle());
    }

    public void close() {
        setApplicationModel(null);
        frame.dispose();
    }

    public void open() {
        frame.setSize(DisplayResolution.VGA.getDimension());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point point = new DimensionExt(frame.getSize()).centerOn(screenSize);
        frame.setLocation(point);
        frame.setVisible(true);

        ActionTrigger actionTrigger = new ActionTrigger(this);
        actionTrigger.performAction(toolbarController);
    }

    @Override
    public Component createComponent() {
        return frame;
    }

    @Override
    public void done(List<MenuContribution> result) {
        menuView.setMenuContributions(result);
    }
}
