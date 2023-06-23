package com.link_intersystems.swing.view;

import com.link_intersystems.util.context.Context;
import com.link_intersystems.util.context.dsl.ContextDsl;

import static java.util.Objects.*;

public abstract class AbstractView implements View {

    private ViewSite viewSite;
    private ContextDsl contextDsl;

    @Override
    public void install(ViewSite viewSite) {
        this.viewSite = requireNonNull(viewSite);
        Context viewContext = viewSite.getViewContext();
        this.contextDsl = new ContextDsl(viewContext);
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
        ViewContent viewContent = viewSite.getViewContent();
        viewContent.setComponent(null);
    }

    protected ViewSite createSubViewSite(ViewContent viewContent) {
        Context viewContext = viewSite.getViewContext();
        return new DefaultViewSite(viewContent, viewContext);
    }
}
