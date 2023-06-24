package com.link_intersystems.swing.view.action;

import com.link_intersystems.swing.view.View;
import com.link_intersystems.swing.view.ViewSite;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.function.Supplier;

import static java.util.Objects.*;

public class ViewInstallAction extends AbstractAction {

    private final Supplier<ViewSite> viewSiteSupplier;
    private final Supplier<View> viewSupplier;

    private View view;

    public ViewInstallAction(Supplier<ViewSite> viewSiteSupplier, Supplier<View> viewSupplier) {
        this.viewSiteSupplier = requireNonNull(viewSiteSupplier);
        this.viewSupplier = requireNonNull(viewSupplier);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view = viewSupplier.get();
        view.install(viewSiteSupplier.get());
    }

}
