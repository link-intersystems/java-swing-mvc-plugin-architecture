package com.link_intersystems.swing.context;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.*;

public abstract class AbstractViewContext implements ViewContext {

    @Override
    public void remove(ObjectQualifier<?> objectQualifier) {
        ObjectRegistration<?> removedRegistration = removeRegistration(objectQualifier);
        if (removedRegistration != null) {
            onObjectUnregistered(removedRegistration);
        }
    }

    protected abstract ObjectRegistration<?> removeRegistration(ObjectQualifier<?> objectQualifier);

    protected abstract <T> ObjectRegistration<T> getRegistration(ObjectQualifier<? super T> objectQualifier);

    private static class ViewContextListenerRegistration<T> {

        private ObjectQualifier<? super T> objectQualifier;
        private ViewContextListener<T> viewContextListener;

        public ViewContextListenerRegistration(ObjectQualifier<? super T> objectQualifier, ViewContextListener<T> viewContextListener) {
            this.objectQualifier = requireNonNull(objectQualifier);
            this.viewContextListener = requireNonNull(viewContextListener);
        }

        public <T> ViewContextListener<T> cast(ObjectQualifier<? super T> objectQualifier) {
            if (accept(objectQualifier)) {
                return (ViewContextListener<T>) viewContextListener;
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
            return Objects.equals(objectQualifier, that.objectQualifier) && Objects.equals(viewContextListener, that.viewContextListener);
        }

        @Override
        public int hashCode() {
            return Objects.hash(objectQualifier, viewContextListener);
        }
    }

    private List<ViewContextListenerRegistration<?>> viewContextListenerRegistrations = new ArrayList<>();

    @Override
    public <T> void addViewContextListener(ObjectQualifier<? super T> objectQualifier, ViewContextListener<T> viewContextListener) {
        ViewContextListenerRegistration<T> listenerRegistration = new ViewContextListenerRegistration<>(objectQualifier, viewContextListener);
        viewContextListenerRegistrations.add(listenerRegistration);

        onViewContextListenerAdded(objectQualifier, viewContextListener);
    }


    @Override
    public <T> void removeViewContextListener(ObjectQualifier<? super T> objectQualifier, ViewContextListener<T> viewContextListener) {
        ViewContextListenerRegistration<T> listenerRegistration = new ViewContextListenerRegistration<>(objectQualifier, viewContextListener);
        if (viewContextListenerRegistrations.remove(listenerRegistration)) {
            onViewContextListenerRemoved(listenerRegistration.viewContextListener);
        }
    }

    private <T> void onViewContextListenerRemoved(ViewContextListener<T> viewContextListener) {
    }

    @Override
    public <T, O extends T> void put(ObjectQualifier<? super T> objectQualifier, O object) {
        ObjectRegistration<O> objectRegistration = new ObjectRegistration<>(objectQualifier, object);

        if (putRegistration(objectRegistration)) {
            onObjectRegistered(objectRegistration);
            return;
        }

        String msg = MessageFormat.format("Object ''{0}'' is already set.", objectQualifier);
        throw new IllegalArgumentException(msg);
    }

    @Override
    public <T> T get(ObjectQualifier<T> objectQualifier) {
        if (objectQualifier == null) {
            return null;
        }

        ObjectRegistration<?> objectRegistration = getRegistration(objectQualifier);

        if (objectRegistration == null) {
            return null;
        }

        return (T) objectRegistration.getObject();
    }

    protected abstract boolean putRegistration(ObjectRegistration<?> objectRegistration);

    protected <T> void onViewContextListenerAdded(ObjectQualifier<? super T> objectQualifier, ViewContextListener<T> viewContextListener) {
        ObjectRegistration<T> registration = getRegistration(objectQualifier);
        if (registration != null) {
            T object = registration.getObject();
            viewContextListener.objectAdded(this, object);
        }
    }

    protected <T> void onObjectRegistered(ObjectRegistration<T> objectRegistration) {
        for (ViewContextListenerRegistration<?> listenerRegistration : viewContextListenerRegistrations) {
            ObjectQualifier<? super T> objectQualifier = objectRegistration.getObjectQualifier();
            ViewContextListener<T> viewContextListener = listenerRegistration.cast(objectQualifier);
            if (viewContextListener != null) {
                T object = objectRegistration.getObject();
                viewContextListener.objectAdded(this, object);
            }
        }
    }


    protected <T> void onObjectUnregistered(ObjectRegistration<T> removedRegistration) {
        for (ViewContextListenerRegistration<?> listenerRegistration : viewContextListenerRegistrations) {
            ObjectQualifier<? super T> objectQualifier = removedRegistration.getObjectQualifier();
            ViewContextListener<T> viewContextListener = listenerRegistration.cast(objectQualifier);
            if (viewContextListener != null) {
                T removedObject = removedRegistration.getObject();
                viewContextListener.objectRemoved(this, removedObject);
            }
        }
    }

    @Override
    public String toString() {
        return getClass().getName() + "[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }
}
