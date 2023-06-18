package com.link_intersystems.swing.view;

import com.link_intersystems.util.context.Context;
import com.link_intersystems.util.context.ContextListener;
import com.link_intersystems.util.context.ObjectQualifier;

import java.awt.*;
import java.util.function.Supplier;

public abstract class AbstractViewSite implements ViewSite {

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
    public <T> void addViewContextListener(ObjectQualifier<? super T> objectQualifier, ContextListener<T> contextListener) {
        getViewContext().addViewContextListener(objectQualifier, contextListener);
    }

    @Override
    public <T> void removeViewContextListener(ObjectQualifier<? super T> objectQualifier, ContextListener<T> contextListener) {
        getViewContext().removeViewContextListener(objectQualifier, contextListener);
    }

    @Override
    public <T> T get(ObjectQualifier<T> objectQualifier) {
        return getViewContext().get(objectQualifier);
    }

    @Override
    public <T> Supplier<T> getSupplier(ObjectQualifier<T> objectQualifier) {
        return getViewContext().getSupplier(objectQualifier);
    }

    @Override
    public boolean remove(ObjectQualifier<?> objectQualifier) {
        return getViewContext().remove(objectQualifier);
    }

    @Override
    public <T, O extends T> void put(ObjectQualifier<? super T> objectQualifier, O object) {
        getViewContext().put(objectQualifier, object);
    }
}
