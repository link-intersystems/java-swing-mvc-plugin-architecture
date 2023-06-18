package com.link_intersystems.swing.view;

import com.link_intersystems.util.context.dsl.ContextDsl;

import static java.util.Objects.*;

public abstract class AbstractView implements View {

    private ViewSite viewSite;
    private ContextDsl contextDsl;

    @Override
    public void install(ViewSite viewSite) {
        this.viewSite = requireNonNull(viewSite);
        this.contextDsl = new ContextDsl(viewSite);
        doInstall(viewSite);
    }

    protected ViewSite getViewSite() {
        return viewSite;
    }

    protected ContextDsl getContextDsl() {
        return contextDsl;
    }

    protected abstract void doInstall(ViewSite viewSite);

    @Override
    public void uninstall() {
        if (viewSite == null) {
            return;
        }

        doUninstall(viewSite);

        viewSite = null;
        contextDsl.dispose();
    }

    protected void doUninstall(ViewSite viewSite) {
        viewSite.setComponent(null);
    }

    protected ViewSite createSubViewSite(ViewContent viewContent) {
        ViewSite subViewSite = new DefaultViewSite(viewContent, viewSite);
        return subViewSite;
    }
}
