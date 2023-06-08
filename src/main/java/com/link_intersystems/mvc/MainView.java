package com.link_intersystems.mvc;

import com.link_intersystems.mvc.view.context.ViewContext;

public abstract class MainView extends View {

    private ViewContext viewContext;

    public MainView(ViewContext viewContext) {
        this.viewContext = viewContext;
    }

    @Override
    public ViewContext getViewContext() {
        return viewContext;
    }
}
