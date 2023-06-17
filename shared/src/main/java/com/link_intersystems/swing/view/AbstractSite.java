package com.link_intersystems.swing.view;

import com.link_intersystems.util.context.Qualifier;
import com.link_intersystems.util.context.Context;
import com.link_intersystems.util.context.ContextListener;

import java.awt.*;
import java.util.function.Supplier;

public abstract class AbstractSite implements Site {

    public abstract ViewContent getViewContent();

    public abstract Context getViewContext();

    @Override
    public void setComponent(Component component) {
        getViewContent().setComponent(component);
    }

    @Override
    public Component getParent() {
        return getViewContent().getParent();
    }

    @Override
    public <T> void addViewContextListener(Qualifier<? super T> qualifier, ContextListener<T> contextListener) {
        getViewContext().addViewContextListener(qualifier, contextListener);
    }

    @Override
    public <T> void removeViewContextListener(Qualifier<? super T> qualifier, ContextListener<T> contextListener) {
        getViewContext().removeViewContextListener(qualifier, contextListener);
    }

    @Override
    public <T> T get(Qualifier<T> qualifier) {
        return getViewContext().get(qualifier);
    }

    @Override
    public <T> Supplier<T> getSupplier(Qualifier<T> qualifier) {
        return getViewContext().getSupplier(qualifier);
    }

    @Override
    public boolean remove(Qualifier<?> qualifier) {
        return getViewContext().remove(qualifier);
    }

    @Override
    public <T, O extends T> void put(Qualifier<? super T> qualifier, O object) {
        getViewContext().put(qualifier, object);
    }


}
