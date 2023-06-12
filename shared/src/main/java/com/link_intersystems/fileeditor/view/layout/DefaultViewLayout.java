package com.link_intersystems.fileeditor.view.layout;

import com.link_intersystems.fileeditor.view.ContainerViewContent;
import com.link_intersystems.fileeditor.view.SubViewSite;
import com.link_intersystems.fileeditor.view.ViewSite;

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
        SubViewSite subViewSite = new SubViewSite(parentViewSite, new ContainerViewContent(viewContainer, layoutConstraints));
        layout.put(requireNonNull(name), subViewSite);
    }

    @Override
    public ViewSite getViewSite(String identifier) {
        return layout.get(identifier);
    }
}