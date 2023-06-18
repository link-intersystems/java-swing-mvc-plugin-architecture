package com.link_intersystems.fileeditor.main;

import com.link_intersystems.fileeditor.menu.MenuView;
import com.link_intersystems.swing.DisplayResolution;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.concurrent.TaskActionListener;
import com.link_intersystems.swing.action.spi.ServiceLoaderAction;
import com.link_intersystems.swing.view.AbstractView;
import com.link_intersystems.swing.view.View;
import com.link_intersystems.swing.view.ViewSite;
import com.link_intersystems.swing.view.layout.DefaultViewLayout;
import com.link_intersystems.swing.view.layout.ViewLayout;
import com.link_intersystems.swing.view.layout.ViewLayoutContribution;
import com.link_intersystems.util.context.CloseContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class ApplicationView extends AbstractView {

    private ApplicationModel applicationModel;
    private JFrame frame;

    public void setApplicationModel(ApplicationModel applicationModel) {
        this.applicationModel = applicationModel;
    }

    @Override
    public void doInstall(ViewSite viewSite) {
        frame = new JFrame();
        frame.setSize(DisplayResolution.VGA.getDimension());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                viewSite.get(CloseContext.class).close();
                uninstall();
            }
        });
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
    }

    protected ViewLayout createViewLayout(ViewSite viewSite, Container contentPane) {
        DefaultViewLayout viewLayout = new DefaultViewLayout(viewSite, contentPane);

        viewLayout.addViewSite("menuSite", BorderLayout.NORTH);
        viewLayout.addViewSite("editorSite", BorderLayout.CENTER);

        return viewLayout;
    }

    @Override
    protected void doUninstall(ViewSite viewSite) {
        super.doUninstall(viewSite);

        frame.dispose();
        frame = null;
    }
}