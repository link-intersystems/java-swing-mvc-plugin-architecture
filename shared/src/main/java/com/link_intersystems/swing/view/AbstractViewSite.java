package com.link_intersystems.swing.view;

import com.link_intersystems.swing.context.ObjectQualifier;
import com.link_intersystems.swing.context.ViewContext;
import com.link_intersystems.swing.context.ViewContextListener;

import java.awt.*;
import java.util.function.Supplier;

public abstract class AbstractViewSite implements ViewSite {

    public abstract ViewContent getViewContent();

    public abstract ViewContext getViewContext();

    @Override
    public <T> T getService(Class<T> type, String name) {
        return getServiceLocator().getService(type, name);
    }

    @Override
    public void setComponent(Component component) {
        getViewContent().setComponent(component);
    }

    @Override
    public Component getParent() {
        return getViewContent().getParent();
    }

    @Override
    public <T> void addViewContextListener(ObjectQualifier<? super T> objectQualifier, ViewContextListener<T> viewContextListener) {
        getViewContext().addViewContextListener(objectQualifier, viewContextListener);
    }

    @Override
    public <T> void removeViewContextListener(ObjectQualifier<? super T> objectQualifier, ViewContextListener<T> viewContextListener) {
        getViewContext().removeViewContextListener(objectQualifier, viewContextListener);
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
    public void remove(ObjectQualifier<?> objectQualifier) {
        getViewContext().remove(objectQualifier);
    }

    @Override
    public <T, O extends T> void put(ObjectQualifier<? super T> objectQualifier, O object) {
        getViewContext().put(objectQualifier, object);
    }


}
