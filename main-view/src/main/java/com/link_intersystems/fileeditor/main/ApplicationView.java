package com.link_intersystems.fileeditor.main;

import com.link_intersystems.fileeditor.action.ActionCallback;
import com.link_intersystems.fileeditor.action.spi.ServiceLoaderAction;
import com.link_intersystems.fileeditor.context.ViewContext;
import com.link_intersystems.fileeditor.menu.MenuView;
import com.link_intersystems.fileeditor.view.AbstractView;
import com.link_intersystems.fileeditor.view.View;
import com.link_intersystems.fileeditor.view.ViewSite;
import com.link_intersystems.fileeditor.view.layout.DefaultViewLayout;
import com.link_intersystems.fileeditor.view.layout.ViewLayout;
import com.link_intersystems.fileeditor.view.layout.ViewLayoutContribution;
import com.link_intersystems.swing.DisplayResolution;
import com.link_intersystems.swing.action.ActionTrigger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class ApplicationView extends AbstractView {

    private MenuView menuView;
    private JTabbedPane tabbedPane;
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
                uninstall();
            }
        });
        frame.setTitle(applicationModel.getTitle());

        ViewContext viewContext = viewSite.getViewContext();

        Container contentPane = frame.getContentPane();
        ViewLayout viewLayout = createViewLayout(viewSite, contentPane);
        viewContext.put(ViewLayout.class, "layout", viewLayout);

        menuView = new MenuView();
        menuView.install(viewLayout.getViewSite("menuSite"));

        ServiceLoaderAction<ViewLayoutContribution> viewContributionAction = new ServiceLoaderAction<>(ViewLayoutContribution.class);
        viewContributionAction.setCallback(new ActionCallback<List<ViewLayoutContribution>, Void>() {
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

        frame.setVisible(true);

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
        tabbedPane = null;
    }
}