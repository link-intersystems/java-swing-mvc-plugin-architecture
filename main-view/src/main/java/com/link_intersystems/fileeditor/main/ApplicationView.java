package com.link_intersystems.fileeditor.main;

import com.link_intersystems.fileeditor.menu.MenuView;
import com.link_intersystems.swing.DisplayResolution;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.concurrent.TaskActionListener;
import com.link_intersystems.swing.action.spi.ServiceLoaderAction;
import com.link_intersystems.swing.view.View;
import com.link_intersystems.swing.view.ViewSite;
import com.link_intersystems.swing.view.layout.DefaultViewLayout;
import com.link_intersystems.swing.view.layout.ViewLayout;
import com.link_intersystems.swing.view.layout.ViewLayoutContribution;
import com.link_intersystems.swing.view.window.WindowView;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ApplicationView extends WindowView {

    private ApplicationModel applicationModel;

    public void setApplicationModel(ApplicationModel applicationModel) {
        this.applicationModel = applicationModel;
    }

    @Override
    protected Window createWindow(ViewSite viewSite) {
        JFrame frame = new JFrame();
        frame.setSize(DisplayResolution.VGA.getDimension());
        frame.setLocationRelativeTo(null);
        frame.setTitle(applicationModel.getTitle());

        Container contentPane = frame.getContentPane();
        ViewLayout viewLayout = createViewLayout(viewSite, contentPane);
        viewSite.put(ViewLayout.class, "layout", viewLayout);

        MenuView menuView = new MenuView();
        menuView.install(viewLayout.getViewSite("menuSite"));

        viewSite.setComponent(frame);

        ServiceLoaderAction<ViewLayoutContribution> viewContributionAction = new ServiceLoaderAction<>(ViewLayoutContribution.class);
        viewContributionAction.setTaskActionListener(new TaskActionListener<>() {
            @Override
            public void done(List<ViewLayoutContribution> result) {
                result.forEach(this::addViewLayoutContribution);
            }

            private void addViewLayoutContribution(ViewLayoutContribution viewLayoutContribution) {
                String viewSiteName = viewLayoutContribution.getViewSiteName();
                ViewSite viewSite = viewLayout.getViewSite(viewSiteName);
                View view = viewLayoutContribution.getView();
                view.install(viewSite);
            }
        });

        ActionTrigger.performAction(this, viewContributionAction);

        return frame;
    }

    protected ViewLayout createViewLayout(ViewSite viewSite, Container contentPane) {
        DefaultViewLayout viewLayout = new DefaultViewLayout(viewSite, contentPane);

        viewLayout.addViewSite("menuSite", BorderLayout.NORTH);
        viewLayout.addViewSite("editorSite", BorderLayout.CENTER);

        return viewLayout;
    }


}