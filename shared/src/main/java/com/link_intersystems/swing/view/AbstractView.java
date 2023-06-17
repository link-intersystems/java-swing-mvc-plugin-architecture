package com.link_intersystems.swing.view;

import static java.util.Objects.*;

public abstract class AbstractView implements View {

    private Site viewSite;

    @Override
    public void install(Site viewSite) {
        this.viewSite = requireNonNull(viewSite);
        doInstall(viewSite);
    }

    protected Site getViewSite() {
        return viewSite;
    }

    protected abstract void doInstall(Site viewSite);

    @Override
    public void uninstall() {
        if (viewSite == null) {
            return;
        }

        doUninstall(viewSite);

        viewSite = null;
    }

    protected void doUninstall(Site viewSite) {
        viewSite.setComponent(null);
    }

    protected SubSite createSubViewSite(ViewContent viewContent){
        return new SubSite(viewSite, viewContent);
    }
}
