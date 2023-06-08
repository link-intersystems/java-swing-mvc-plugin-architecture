package com.link_intersystems.mvc.view.context;

public interface MutableViewContext extends ViewContext {
    default <T> void remove(Class<? super T> type) {
        remove(type, null);
    }

    default <T> void remove(Class<? super T> type, String name) {
        ObjectQualifier<? super T> objectQualifier = new ObjectQualifier<>(type, name);
        remove(objectQualifier);
    }

    void remove(ObjectQualifier<?> objectQualifier);

    default <T, O extends T> void put(Class<T> type, O object) {
        put(type, null, object);
    }

    default <T, O extends T> void put(Class<? super T> type, String name, O object) {
        put(new ObjectQualifier<>(type, name), object);
    }

    <T, O extends T> void put(ObjectQualifier<? super T> objectQualifier, O object);

}
