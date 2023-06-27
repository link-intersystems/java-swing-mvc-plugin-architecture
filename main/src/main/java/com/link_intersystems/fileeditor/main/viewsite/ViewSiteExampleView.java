package com.link_intersystems.fileeditor.main.viewsite;

import com.link_intersystems.swing.view.View;
import com.link_intersystems.swing.view.ViewContent;
import com.link_intersystems.swing.view.ViewSite;
import com.link_intersystems.swing.view.layout.DefaultViewLayout;
import com.link_intersystems.swing.view.layout.ViewLayout;
import com.link_intersystems.util.context.Context;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.awt.BorderLayout.*;

public class ViewSiteExampleView implements View {

    // Define the view site names
    public static final String TOP = "top";
    public static final String BOTTOM = "bottom";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String MIDDLE = "middle";

    private ViewSite viewSite;
    private JFrame frame;
    private DefaultViewLayout viewLayout;

    @Override
    public void install(ViewSite viewSite) {
        this.viewSite = viewSite;

        // Create the main view component
        frame = new JFrame("ViewSite Example");
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                uninstall();
            }
        });

        frame.setLocationRelativeTo(null);

        // Create the ViewLayout
        Container contentPane = frame.getContentPane();
        Context viewContext = viewSite.getViewContext();
        viewLayout = createViewLayout(viewContext, contentPane);
        viewContext.put(ViewLayout.class, viewLayout);

        // Instal views into the ViewLayout's view sites
        installView(viewLayout, TOP, "ViewSite north");
        installView(viewLayout, BOTTOM, "ViewSite south");
        installView(viewLayout, LEFT, "ViewSite west");
        installView(viewLayout, RIGHT, "ViewSite east");
        installView(viewLayout, MIDDLE, "ViewSite center");

        // Make the main frame visible by setting the ViewContent
        ViewContent viewContent = viewSite.getViewContent();
        viewContent.setComponent(frame);
    }

    private DefaultViewLayout createViewLayout(Context viewContext, Container viewContainer) {
        DefaultViewLayout viewLayout = new DefaultViewLayout(viewContext, viewContainer);

        viewLayout.addViewSite(TOP, NORTH);
        viewLayout.addViewSite(BOTTOM, SOUTH);
        viewLayout.addViewSite(LEFT, WEST);
        viewLayout.addViewSite(RIGHT, EAST);
        viewLayout.addViewSite(MIDDLE, CENTER);

        return viewLayout;
    }

    private void installView(ViewLayout viewLayout, String viewSiteName, String areaName) {
        ViewSiteView viewSiteView = new ViewSiteView(areaName);
        viewLayout.install(viewSiteName, viewSiteView);
    }

    // Uninstall in reverse order
    @Override
    public void uninstall() {
        if (viewSite == null) {
            return;
        }

        viewLayout.dispose();

        Context viewContext = viewSite.getViewContext();
        viewContext.remove(ViewLayout.class);
        viewLayout = null;

        ViewContent viewContent = viewSite.getViewContent();
        viewContent.setComponent(null);

        frame.dispose();
        frame = null;

        viewSite = null;
    }
}
