package com.link_intersystems.fileeditor.main;

import com.link_intersystems.swing.DisplayResolution;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.spi.ServiceLoaderAction;
import com.link_intersystems.swing.view.DefaultViewSite;
import com.link_intersystems.swing.view.ViewContent;
import com.link_intersystems.swing.view.ViewSite;
import com.link_intersystems.swing.view.layout.ViewLayout;
import com.link_intersystems.swing.view.layout.ViewLayoutContribution;
import com.link_intersystems.swing.view.window.SubWindowViewContent;
import com.link_intersystems.swing.view.window.WindowView;
import com.link_intersystems.util.context.Context;

import javax.swing.*;
import java.awt.*;

import static com.link_intersystems.fileeditor.main.MainViewLayout.*;

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
        ViewLayout viewLayout = MainViewLayout.createViewLayout(viewContext, contentPane);
        viewContext.put(ViewLayout.class, MAIN_VIEW_LAYOUT, viewLayout);

        ViewContent mainContent = new SubWindowViewContent(frame);
        DefaultViewSite subWindowViewSite = new DefaultViewSite(mainContent, viewContext);
        viewContext.put(ViewSite.class, MAIN_VIEW_SITE, subWindowViewSite);

        viewLayoutMediator = initLayoutContributions(viewLayout);

        return frame;
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