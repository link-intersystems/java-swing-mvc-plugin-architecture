package com.link_intersystems.swing.view;

import com.link_intersystems.util.context.DefaultContext;
import com.link_intersystems.util.context.Context;
import com.link_intersystems.swing.view.window.WindowViewContent;

public class RootSite extends AbstractSite {

    private ViewContent rootViewContent = new WindowViewContent();
    private DefaultContext viewContext = new DefaultContext();

    @Override
    public ViewContent getViewContent() {
        return rootViewContent;
    }

    @Override
    public Context getViewContext() {
        return viewContext;
    }
}
