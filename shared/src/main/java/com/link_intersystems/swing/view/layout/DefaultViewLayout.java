package com.link_intersystems.swing.view.layout;

import com.link_intersystems.swing.view.ContainerViewContent;
import com.link_intersystems.swing.view.SubSite;
import com.link_intersystems.swing.view.Site;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.*;

public class DefaultViewLayout implements ViewLayout {

    private Map<String, Site> layout = new HashMap<>();
    private Site parentViewSite;
    private Container viewContainer;

    public DefaultViewLayout(Site parentViewSite, Container viewContainer) {
        this.parentViewSite = requireNonNull(parentViewSite);
        this.viewContainer = requireNonNull(viewContainer);
    }

    public void addViewSite(String name, Object layoutConstraints) {
        SubSite subViewSite = new SubSite(parentViewSite, new ContainerViewContent(viewContainer, layoutConstraints));
        layout.put(requireNonNull(name), subViewSite);
    }

    @Override
    public Site getViewSite(String identifier) {
        return layout.get(identifier);
    }
}