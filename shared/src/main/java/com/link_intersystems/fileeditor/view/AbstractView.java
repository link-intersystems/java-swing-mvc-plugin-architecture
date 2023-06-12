package com.link_intersystems.fileeditor.view;

import static java.util.Objects.*;

public abstract class AbstractView implements View {

    private ViewSite viewSite;

    @Override
    public void install(ViewSite viewSite) {
        this.viewSite = requireNonNull(viewSite);
        doInstall(viewSite);
    }

    protected ViewSite getViewSite() {
        return viewSite;
    }

    protected abstract void doInstall(ViewSite viewSite);

    @Override
    public void uninstall() {
        if (viewSite == null) {
            return;
        }

        doUninstall(viewSite);

        viewSite = null;
    }

    protected void doUninstall(ViewSite viewSite) {
    }

    protected SubViewSite createSubViewSite(ViewContent viewContent){
        return new SubViewSite(viewSite, viewContent);
    }
}