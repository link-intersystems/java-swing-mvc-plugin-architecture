package com.link_intersystems.swing.view;

import com.link_intersystems.swing.context.ViewContext;

import static java.util.Objects.*;

public class SubViewSite extends AbstractViewSite {

    private ViewSite superViewSite;
    private ViewContent viewContent;

    public SubViewSite(ViewSite superViewSite, ViewContent viewContent) {
        this.superViewSite = requireNonNull(superViewSite);
        this.viewContent = viewContent;
    }

    @Override
    public <T> T getService(Class<T> type, String name) {
        return superViewSite.getService(type, name);
    }

    @Override
    public ViewContent getViewContent() {
        return viewContent;
    }

    @Override
    public ViewContext getViewContext() {
        return superViewSite;
    }
}
