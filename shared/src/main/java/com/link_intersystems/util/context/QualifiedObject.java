package com.link_intersystems.util.context;

import java.util.Objects;

import static java.util.Objects.*;

public class QualifiedObject<T> {

    private ObjectQualifier<? super T> objectQualifier;
    private T object;

    public QualifiedObject(ObjectQualifier<? super T> objectQualifier, T object) {
        this.objectQualifier = requireNonNull(objectQualifier);
        this.object = requireNonNull(object);
    }

    public ObjectQualifier<? super T> getQualifier() {
        return objectQualifier;
    }

    public T getObject() {
        return object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QualifiedObject<?> that = (QualifiedObject<?>) o;
        return Objects.equals(getQualifier(), that.getQualifier()) && getObject() == that.getObject();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQualifier(), System.identityHashCode(getObject()));
    }
}
