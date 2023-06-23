package com.link_intersystems.swing.view.layout;

import com.link_intersystems.swing.view.ContainerViewContent;
import com.link_intersystems.swing.view.DefaultViewSite;
import com.link_intersystems.swing.view.View;
import com.link_intersystems.swing.view.ViewSite;
import com.link_intersystems.util.context.Context;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.*;

public class DefaultViewLayout implements ViewLayout {

    private Map<String, ViewSite> layout = new HashMap<>();
    private Map<String, View> installedViews = new HashMap<>();
    private Context viewContext;
    private Container viewContainer;

    public DefaultViewLayout(Context viewContext, Container viewContainer) {
        this.viewContext = requireNonNull(viewContext);
        this.viewContainer = requireNonNull(viewContainer);
    }

    public void addViewSite(String name, Object layoutConstraints) {
        ViewSite layoutViewSite = new DefaultViewSite(new ContainerViewContent(viewContainer, layoutConstraints), viewContext);
        layout.put(requireNonNull(name), layoutViewSite);

    }

    @Override
    public void install(String viewSiteName, View view) {
        ViewSite viewSite = layout.get(viewSiteName);
        view.install(viewSite);
        installedViews.put(viewSiteName, view);
        viewContainer.revalidate();
    }

    @Override
    public void remove(String viewSiteName) {
        View view = installedViews.get(viewSiteName);
        if (view != null) {
            view.uninstall();
            viewContainer.revalidate();
        }
    }
}