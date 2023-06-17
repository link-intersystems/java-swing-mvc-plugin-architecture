package com.link_intersystems.util.context;

import java.util.function.Supplier;

public interface Context {
    default <T> void addViewContextListener(Class<? super T> type, ContextListener<T> contextListener) {
        addViewContextListener(type, null, contextListener);
    }

    default <T> void addViewContextListener(Class<? super T> type, String name, ContextListener<T> contextListener) {
        Qualifier<? super T> qualifier = new Qualifier<>(type, name);

        addViewContextListener(qualifier, contextListener);
    }

    <T> void addViewContextListener(Qualifier<? super T> qualifier, ContextListener<T> contextListener);

    default <T> void removeViewContextListener(Class<? super T> type, ContextListener<T> contextListener) {
        removeViewContextListener(type, null, contextListener);
    }

    default <T> void removeViewContextListener(Class<? super T> type, String name, ContextListener<T> contextListener) {
        Qualifier<? super T> qualifier = new Qualifier<>(type, name);
        removeViewContextListener(qualifier, contextListener);
    }

    <T> void removeViewContextListener(Qualifier<? super T> qualifier, ContextListener<T> contextListener);

    default <T> T get(Class<T> type) {
        return get(type, null);
    }

    default <T> T get(Class<T> type, String name) {
        return get(new Qualifier<>(type, name));
    }

    <T> T get(Qualifier<T> qualifier);

    default public <T> Supplier<T> getSupplier(Class<T> type) {
        return getSupplier(type, null);
    }

    default public <T> Supplier<T> getSupplier(Class<T> type, String name) {
        return getSupplier(new Qualifier<>(type, name));
    }

    default public <T> Supplier<T> getSupplier(Qualifier<T> qualifier) {
        return () -> get(qualifier);
    }

    default <T> boolean remove(Class<? super T> type) {
        return remove(type, null);
    }

    default <T> boolean remove(Class<? super T> type, String name) {
        Qualifier<? super T> qualifier = new Qualifier<>(type, name);
        return remove(qualifier);
    }

    boolean remove(Qualifier<?> qualifier);

    default <T> void put(T object) {
        putInstance(object, null);
    }

    @SuppressWarnings("unchecked")
    default <T> void putInstance(T instance, String name) {
        Class<T> aClass = (Class<T>) instance.getClass();
        put(aClass, name, instance);
    }

    default <T, O extends T> void put(Class<T> type, O object) {
        put(type, null, object);
    }

    default <T, O extends T> void put(Class<? super T> type, String name, O object) {
        put(new Qualifier<>(type, name), object);
    }

    <T, O extends T> void put(Qualifier<? super T> qualifier, O object);
}
