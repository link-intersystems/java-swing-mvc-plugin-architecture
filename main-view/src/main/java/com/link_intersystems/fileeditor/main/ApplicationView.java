package com.link_intersystems.fileeditor.main;

import com.link_intersystems.swing.DisplayResolution;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.spi.ServiceLoaderAction;
import com.link_intersystems.swing.view.ViewSite;
import com.link_intersystems.swing.view.layout.DefaultViewLayout;
import com.link_intersystems.swing.view.layout.ViewLayout;
import com.link_intersystems.swing.view.layout.ViewLayoutContribution;
import com.link_intersystems.swing.view.window.WindowView;
import com.link_intersystems.util.context.Context;

import javax.swing.*;
import java.awt.*;

public class ApplicationView extends WindowView {

    private ApplicationModel applicationModel;
    private ApplicationViewLayoutMediator viewLayoutMediator;

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

        Context viewContext = viewSite.getViewContext();
        ViewLayout viewLayout = createViewLayout(viewContext, contentPane);
        viewContext.put(ViewLayout.class, "layout", viewLayout);

        viewLayoutMediator = initLayoutContributions(viewLayout);

        return frame;
    }

    private ViewLayout createViewLayout(Context viewContext, Container contentPane) {
        DefaultViewLayout viewLayout = new DefaultViewLayout(viewContext, contentPane);

        viewLayout.addViewSite("menuSite", BorderLayout.NORTH);
        viewLayout.addViewSite("editorSite", BorderLayout.CENTER);

        return viewLayout;
    }

    private ApplicationViewLayoutMediator initLayoutContributions(ViewLayout viewLayout) {
        ServiceLoaderAction<ViewLayoutContribution> viewContributionAction = new ServiceLoaderAction<>(ViewLayoutContribution.class);

        ApplicationViewLayoutMediator viewLayoutMediator = new ApplicationViewLayoutMediator(viewLayout);
        viewContributionAction.setTaskActionListener(viewLayoutMediator);

        ActionTrigger.performAction(this, viewContributionAction);

        return viewLayoutMediator;
    }


    @Override
    protected void doUninstall(ViewSite viewSite) {
        super.doUninstall(viewSite);
        viewLayoutMediator.dispose();
        viewLayoutMediator = null;
    }
}