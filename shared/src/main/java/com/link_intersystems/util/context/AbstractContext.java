package com.link_intersystems.util.context;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.*;

public abstract class AbstractContext implements Context {

    protected abstract QualifiedObject<?> removeInstance(ObjectQualifier<?> objectQualifier);

    protected abstract <T> QualifiedObject<T> getInstance(ObjectQualifier<? super T> objectQualifier);

    @SuppressWarnings("CanBeFinal")
    private static class ViewContextListenerRegistration<T> {

        private ObjectQualifier<? super T> objectQualifier;
        private ContextListener<T> contextListener;

        public ViewContextListenerRegistration(ObjectQualifier<? super T> objectQualifier, ContextListener<T> contextListener) {
            this.objectQualifier = requireNonNull(objectQualifier);
            this.contextListener = requireNonNull(contextListener);
        }

        @SuppressWarnings("unchecked")
        public <T> ContextListener<T> cast(ObjectQualifier<? super T> objectQualifier) {
            if (accept(objectQualifier)) {
                return (ContextListener<T>) contextListener;
            }
            return null;
        }

        public <T> boolean accept(ObjectQualifier<? super T> objectQualifier) {
            return this.objectQualifier.equals(objectQualifier);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ViewContextListenerRegistration<?> that = (ViewContextListenerRegistration<?>) o;
            return Objects.equals(objectQualifier, that.objectQualifier) && Objects.equals(contextListener, that.contextListener);
        }

        @Override
        public int hashCode() {
            return Objects.hash(objectQualifier, contextListener);
        }
    }

    private List<ViewContextListenerRegistration<?>> viewContextListenerRegistrations = new ArrayList<>();

    @Override
    public <T> void addViewContextListener(ObjectQualifier<? super T> objectQualifier, ContextListener<T> contextListener) {
        ViewContextListenerRegistration<T> listenerRegistration = new ViewContextListenerRegistration<>(objectQualifier, contextListener);
        viewContextListenerRegistrations.add(listenerRegistration);

        onViewContextListenerAdded(objectQualifier, contextListener);
    }

    protected <T> void onViewContextListenerAdded(ObjectQualifier<? super T> objectQualifier, ContextListener<T> contextListener) {
        QualifiedObject<T> registration = getInstance(objectQualifier);
        if (registration != null) {
            T object = registration.getObject();
            contextListener.added(this, object);
        }
    }


    @Override
    public <T> void removeViewContextListener(ObjectQualifier<? super T> objectQualifier, ContextListener<T> contextListener) {
        ViewContextListenerRegistration<T> listenerRegistration = new ViewContextListenerRegistration<>(objectQualifier, contextListener);
        if (viewContextListenerRegistrations.remove(listenerRegistration)) {
            onViewContextListenerRemoved(listenerRegistration.contextListener);
        }
    }

    protected <T> void onViewContextListenerRemoved(ContextListener<T> contextListener) {
    }

    @Override
    public <T, O extends T> void put(ObjectQualifier<? super T> objectQualifier, O object) {
        QualifiedObject<O> qualifiedObject = new QualifiedObject<>(objectQualifier, object);

        if (putInstance(qualifiedObject)) {
            onPutInstance(qualifiedObject);
            return;
        }

        throw new DuplicateObjectException(objectQualifier);
    }

    protected abstract boolean putInstance(QualifiedObject<?> qualifiedObject);


    protected <T> void onPutInstance(QualifiedObject<T> qualifiedObject) {
        for (ViewContextListenerRegistration<?> listenerRegistration : viewContextListenerRegistrations) {
            ObjectQualifier<? super T> objectQualifier = qualifiedObject.getQualifier();
            ContextListener<T> contextListener = listenerRegistration.cast(objectQualifier);
            if (contextListener != null) {
                T object = qualifiedObject.getObject();
                contextListener.added(this, object);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(ObjectQualifier<T> objectQualifier) {
        if (objectQualifier == null) {
            return null;
        }

        QualifiedObject<?> qualifiedObject = getInstance(objectQualifier);

        if (qualifiedObject == null) {
            throw new NoSuchObjectException(objectQualifier);
        }

        return (T) qualifiedObject.getObject();
    }

    @Override
    public boolean remove(ObjectQualifier<?> objectQualifier) {
        QualifiedObject<?> removedInstance = removeInstance(objectQualifier);

        if (removedInstance != null) {
            onRemoveInstance(removedInstance);
        }

        return removedInstance != null;
    }

    protected <T> void onRemoveInstance(QualifiedObject<T> removedRegistration) {
        for (ViewContextListenerRegistration<?> listenerRegistration : viewContextListenerRegistrations) {
            ObjectQualifier<? super T> objectQualifier = removedRegistration.getQualifier();
            ContextListener<T> contextListener = listenerRegistration.cast(objectQualifier);
            if (contextListener != null) {
                T removedObject = removedRegistration.getObject();
                contextListener.removed(this, removedObject);
            }
        }
    }

    @Override
    public String toString() {
        return getClass().getName() + "[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }
}
