package com.link_intersystems.mvc;

import com.link_intersystems.mvc.view.context.ObjectQualifier;

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
