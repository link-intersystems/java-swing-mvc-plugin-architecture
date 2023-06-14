package com.link_intersystems.fileeditor.view;

import com.link_intersystems.fileeditor.context.DefaultViewContext;
import com.link_intersystems.fileeditor.context.ViewContext;

public abstract class RootViewSite implements ViewSite {

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
