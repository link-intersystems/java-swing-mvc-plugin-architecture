package com.link_intersystems.util.context;

import java.util.Objects;

import static java.util.Objects.*;

public class QualifiedInstance<T> {

    private Qualifier<? super T> qualifier;
    private T instance;

    public QualifiedInstance(Qualifier<? super T> qualifier, T instance) {
        this.qualifier = requireNonNull(qualifier);
        this.instance = requireNonNull(instance);
    }

    public Qualifier<? super T> getQualifier() {
        return qualifier;
    }

    public T getInstance() {
        return instance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QualifiedInstance<?> that = (QualifiedInstance<?>) o;
        return Objects.equals(getQualifier(), that.getQualifier()) && getInstance() == that.getInstance();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQualifier(), System.identityHashCode(getInstance()));
    }
}
