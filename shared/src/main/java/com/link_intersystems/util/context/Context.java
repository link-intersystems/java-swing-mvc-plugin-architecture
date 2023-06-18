package com.link_intersystems.util.context;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface Context {

    default <T> void addViewContextListener(Class<? super T> type, ContextListener<T> contextListener) {
        addViewContextListener(type, null, contextListener);
    }

    default <T> void addViewContextListener(Class<? super T> type, String name, ContextListener<T> contextListener) {
        ObjectQualifier<? super T> objectQualifier = new ObjectQualifier<>(type, name);

        addViewContextListener(objectQualifier, contextListener);
    }

    <T> void addViewContextListener(ObjectQualifier<? super T> objectQualifier, ContextListener<T> contextListener);

    default <T> void removeViewContextListener(Class<? super T> type, ContextListener<T> contextListener) {
        removeViewContextListener(type, null, contextListener);
    }

    default <T> void removeViewContextListener(Class<? super T> type, String name, ContextListener<T> contextListener) {
        ObjectQualifier<? super T> objectQualifier = new ObjectQualifier<>(type, name);
        removeViewContextListener(objectQualifier, contextListener);
    }

    <T> void removeViewContextListener(ObjectQualifier<? super T> objectQualifier, ContextListener<T> contextListener);

    default boolean contains(Class<?> type) {
        return contains(type, null);
    }

    default boolean contains(Class<?> type, String name) {
        return contains(new ObjectQualifier<>(type, name));
    }

    boolean contains(ObjectQualifier<?> objectQualifier);

    default <T> void ifContains(Class<T> type, Consumer<T> objectConsumer) {
        ifContains(type, null, objectConsumer);
    }

    default <T> void ifContains(Class<T> type, String name, Consumer<T> objectConsumer) {
        ifContains(new ObjectQualifier<>(type, name), objectConsumer);
    }

    default <T> void ifContains(ObjectQualifier<T> objectQualifier, Consumer<T> objectConsumer) {
        if (contains(objectQualifier)) {
            T object = get(objectQualifier);
            objectConsumer.accept(object);
        }
    }

    default <T> T get(Class<T> type) throws ContextObjectException {
        return get(type, null);
    }

    default <T> T get(Class<T> type, String name) throws ContextObjectException {
        return get(new ObjectQualifier<>(type, name));
    }

    <T> T get(ObjectQualifier<T> objectQualifier) throws ContextObjectException;

    default public <T> Supplier<T> getSupplier(Class<T> type) {
        return getSupplier(type, null);
    }

    default public <T> Supplier<T> getSupplier(Class<T> type, String name) {
        return getSupplier(new ObjectQualifier<>(type, name));
    }

    default public <T> Supplier<T> getSupplier(ObjectQualifier<T> objectQualifier) {
        return () -> get(objectQualifier);
    }

    default <T> boolean remove(Class<? super T> type) {
        return remove(type, null);
    }

    default <T> boolean remove(Class<? super T> type, String name) {
        ObjectQualifier<? super T> objectQualifier = new ObjectQualifier<>(type, name);
        return remove(objectQualifier);
    }

    boolean remove(ObjectQualifier<?> objectQualifier);

    default <T, O extends T> void put(Class<T> type, O object) throws ContextObjectException {
        put(type, null, object);
    }

    default <T, O extends T> void put(Class<? super T> type, String name, O object) throws ContextObjectException {
        put(new ObjectQualifier<>(type, name), object);
    }

    <T, O extends T> void put(ObjectQualifier<? super T> objectQualifier, O object) throws ContextObjectException;

    public Stream<QualifiedObject<?>> stream();
}
