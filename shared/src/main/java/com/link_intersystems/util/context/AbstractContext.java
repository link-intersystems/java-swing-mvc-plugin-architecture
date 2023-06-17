package com.link_intersystems.util.context;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.*;

public abstract class AbstractContext implements Context {

    protected abstract QualifiedInstance<?> removeInstance(Qualifier<?> qualifier);

    protected abstract <T> QualifiedInstance<T> getInstance(Qualifier<? super T> qualifier);

    @SuppressWarnings("CanBeFinal")
    private static class ViewContextListenerRegistration<T> {

        private Qualifier<? super T> qualifier;
        private ContextListener<T> contextListener;

        public ViewContextListenerRegistration(Qualifier<? super T> qualifier, ContextListener<T> contextListener) {
            this.qualifier = requireNonNull(qualifier);
            this.contextListener = requireNonNull(contextListener);
        }

        @SuppressWarnings("unchecked")
        public <T> ContextListener<T> cast(Qualifier<? super T> qualifier) {
            if (accept(qualifier)) {
                return (ContextListener<T>) contextListener;
            }
            return null;
        }

        public <T> boolean accept(Qualifier<? super T> qualifier) {
            return this.qualifier.equals(qualifier);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ViewContextListenerRegistration<?> that = (ViewContextListenerRegistration<?>) o;
            return Objects.equals(qualifier, that.qualifier) && Objects.equals(contextListener, that.contextListener);
        }

        @Override
        public int hashCode() {
            return Objects.hash(qualifier, contextListener);
        }
    }

    private List<ViewContextListenerRegistration<?>> viewContextListenerRegistrations = new ArrayList<>();

    @Override
    public <T> void addViewContextListener(Qualifier<? super T> qualifier, ContextListener<T> contextListener) {
        ViewContextListenerRegistration<T> listenerRegistration = new ViewContextListenerRegistration<>(qualifier, contextListener);
        viewContextListenerRegistrations.add(listenerRegistration);

        onViewContextListenerAdded(qualifier, contextListener);
    }

    protected <T> void onViewContextListenerAdded(Qualifier<? super T> qualifier, ContextListener<T> contextListener) {
        QualifiedInstance<T> registration = getInstance(qualifier);
        if (registration != null) {
            T object = registration.getInstance();
            contextListener.added(this, object);
        }
    }


    @Override
    public <T> void removeViewContextListener(Qualifier<? super T> qualifier, ContextListener<T> contextListener) {
        ViewContextListenerRegistration<T> listenerRegistration = new ViewContextListenerRegistration<>(qualifier, contextListener);
        if (viewContextListenerRegistrations.remove(listenerRegistration)) {
            onViewContextListenerRemoved(listenerRegistration.contextListener);
        }
    }

    protected <T> void onViewContextListenerRemoved(ContextListener<T> contextListener) {
    }

    @Override
    public <T, O extends T> void put(Qualifier<? super T> qualifier, O object) {
        QualifiedInstance<O> qualifiedInstance = new QualifiedInstance<>(qualifier, object);

        if (putInstance(qualifiedInstance)) {
            onInstancePut(qualifiedInstance);
            return;
        }

        String msg = MessageFormat.format("Object ''{0}'' is already set.", qualifier);
        throw new IllegalArgumentException(msg);
    }

    protected abstract boolean putInstance(QualifiedInstance<?> qualifiedInstance);


    protected <T> void onInstancePut(QualifiedInstance<T> qualifiedInstance) {
        for (ViewContextListenerRegistration<?> listenerRegistration : viewContextListenerRegistrations) {
            Qualifier<? super T> qualifier = qualifiedInstance.getQualifier();
            ContextListener<T> contextListener = listenerRegistration.cast(qualifier);
            if (contextListener != null) {
                T object = qualifiedInstance.getInstance();
                contextListener.added(this, object);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Qualifier<T> qualifier) {
        if (qualifier == null) {
            return null;
        }

        QualifiedInstance<?> qualifiedInstance = getInstance(qualifier);

        if (qualifiedInstance == null) {
            return null;
        }

        return (T) qualifiedInstance.getInstance();
    }

    @Override
    public boolean remove(Qualifier<?> qualifier) {
        QualifiedInstance<?> removedInstance = removeInstance(qualifier);

        if (removedInstance != null) {
            onInstanceRemoved(removedInstance);
        }

        return removedInstance != null;
    }

    protected <T> void onInstanceRemoved(QualifiedInstance<T> removedRegistration) {
        for (ViewContextListenerRegistration<?> listenerRegistration : viewContextListenerRegistrations) {
            Qualifier<? super T> qualifier = removedRegistration.getQualifier();
            ContextListener<T> contextListener = listenerRegistration.cast(qualifier);
            if (contextListener != null) {
                T removedObject = removedRegistration.getInstance();
                contextListener.removed(this, removedObject);
            }
        }
    }

    @Override
    public String toString() {
        return getClass().getName() + "[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }
}
