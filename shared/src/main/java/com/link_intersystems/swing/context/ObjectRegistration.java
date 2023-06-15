package com.link_intersystems.swing.context;

public class ObjectRegistration<T> {

    private ObjectQualifier<? super T> objectQualifier;
    private T object;

    public ObjectRegistration(ObjectQualifier<? super T> objectQualifier, T object) {
        this.objectQualifier = objectQualifier;
        this.object = object;
    }

    public ObjectQualifier<? super T> getObjectQualifier() {
        return objectQualifier;
    }

    public T getObject() {
        return object;
    }
}
