package com.link_intersystems.mvc.view.context;

import java.util.function.Supplier;

public interface ViewContext {
    default <T> void addViewContextListener(Class<? super T> type, ViewContextListener<T> viewContextListener) {
        addViewContextListener(type, null, viewContextListener);
    }

    default <T> void addViewContextListener(Class<? super T> type, String name, ViewContextListener<T> viewContextListener) {
        ObjectQualifier<? super T> objectQualifier = new ObjectQualifier<>(type, name);

        addViewContextListener(objectQualifier, viewContextListener);
    }

    <T> void addViewContextListener(ObjectQualifier<? super T> objectQualifier, ViewContextListener<T> viewContextListener);

    default <T> void removeViewContextListener(Class<? super T> type, ViewContextListener<T> viewContextListener) {
        removeViewContextListener(type, null, viewContextListener);
    }

    default <T> void removeViewContextListener(Class<? super T> type, String name, ViewContextListener<T> viewContextListener) {
        ObjectQualifier<? super T> objectQualifier = new ObjectQualifier<>(type, name);
        removeViewContextListener(objectQualifier, viewContextListener);
    }

    <T> void removeViewContextListener(ObjectQualifier<? super T> objectQualifier, ViewContextListener<T> viewContextListener);

    default <T> T get(Class<T> type) {
        return get(type, null);
    }

    default <T> T get(Class<T> type, String name) {
        return get(new ObjectQualifier<T>(type, name));
    }

    <T> T get(ObjectQualifier<T> objectQualifier);

    default public <T> Supplier<T> getSupplier(Class<T> type) {
        return getSupplier(type, null);
    }

    default public <T> Supplier<T> getSupplier(Class<T> type, String name) {
        return getSupplier(new ObjectQualifier<T>(type, name));
    }

    default public <T> Supplier<T> getSupplier(ObjectQualifier<T> objectQualifier) {
        return () -> get(objectQualifier);
    }
}
