package com.link_intersystems.swing.view;

import com.link_intersystems.swing.context.DefaultViewContext;
import com.link_intersystems.swing.context.ViewContext;

public abstract class RootViewSite extends AbstractViewSite {

    private ViewContent rootViewContent = new NullViewContent();
    private DefaultViewContext viewContext = new DefaultViewContext();

    @Override
    public ViewContent getViewContent() {
        return rootViewContent;
    }

    @Override
    public ViewContext getViewContext() {
        return viewContext;
    }
}
