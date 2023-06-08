package com.link_intersystems.mvc;

import com.link_intersystems.mvc.view.context.AbstractMutableViewContext;

public abstract class MainView extends View {

    private AbstractMutableViewContext abstractViewContext;

    public MainView(AbstractMutableViewContext abstractViewContext) {
        this.abstractViewContext = abstractViewContext;
    }

    @Override
    public AbstractMutableViewContext getViewContext() {
        return abstractViewContext;
    }
}
