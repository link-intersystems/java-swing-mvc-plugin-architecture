package com.link_intersystems.swing.view.layout;

import com.link_intersystems.swing.view.ContainerViewContent;
import com.link_intersystems.swing.view.DefaultViewSite;
import com.link_intersystems.swing.view.ViewSite;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.*;

public class DefaultViewLayout implements ViewLayout {

    private Map<String, ViewSite> layout = new HashMap<>();
    private ViewSite parentViewSite;
    private Container viewContainer;

    public DefaultViewLayout(ViewSite parentViewSite, Container viewContainer) {
        this.parentViewSite = requireNonNull(parentViewSite);
        this.viewContainer = requireNonNull(viewContainer);
    }

    public void addViewSite(String name, Object layoutConstraints) {
        ViewSite subViewSite = new DefaultViewSite(new ContainerViewContent(viewContainer, layoutConstraints), parentViewSite);
        layout.put(requireNonNull(name), subViewSite);
    }

    @Override
    public ViewSite getViewSite(String identifier) {
        return layout.get(identifier);
    }
}