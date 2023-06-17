package com.link_intersystems.swing.view;

import com.link_intersystems.util.context.Context;

import static java.util.Objects.*;

public class SubSite extends AbstractSite {

    private Site superViewSite;
    private ViewContent viewContent;

    public SubSite(Site superViewSite, ViewContent viewContent) {
        this.superViewSite = requireNonNull(superViewSite);
        this.viewContent = viewContent;
    }

    @Override
    public ViewContent getViewContent() {
        return viewContent;
    }

    @Override
    public Context getViewContext() {
        return superViewSite;
    }

}
