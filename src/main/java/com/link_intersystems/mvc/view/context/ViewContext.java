package com.link_intersystems.mvc.view.context;

import com.link_intersystems.mvc.ObjectRegistration;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.*;

public abstract class ViewContext {

    public <T> void remove(Class<? super T> type, String name) {
        ObjectQualifier<? super T> objectQualifier = new ObjectQualifier<>(type, name);

        remove(objectQualifier);
    }

    public void remove(ObjectQualifier<?> objectQualifier) {
        ObjectRegistration<?> removedRegistration = removeRegistration(objectQualifier);
        if (removedRegistration != null) {
            onObjectUnregistered(removedRegistration);
        }
    }

    protected abstract ObjectRegistration<?> removeRegistration(ObjectQualifier<?> objectQualifier);

    protected abstract <T> ObjectRegistration<?> getRegistration(ObjectQualifier<T> objectQualifier);

    protected static class ViewContextListenerRegistration<T> {

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

    private List<DefaultViewContext.ViewContextListenerRegistration<?>> viewContextListenerRegistrations = new ArrayList<>();

    public <T> void addViewContextListener(ObjectQualifier<? super T> objectQualifier, ViewContextListener<T> viewContextListener) {
        ViewContextListenerRegistration<T> listenerRegistration = new ViewContextListenerRegistration<>(objectQualifier, viewContextListener);
        viewContextListenerRegistrations.add(listenerRegistration);

        onViewContextListenerAdded(objectQualifier, viewContextListener, listenerRegistration);
    }


    public <T> void removeViewContextListener(ObjectQualifier<? super T> objectQualifier, ViewContextListener<T> viewContextListener) {
        ViewContextListenerRegistration<T> listenerRegistration = new ViewContextListenerRegistration<>(objectQualifier, viewContextListener);
        viewContextListenerRegistrations.remove(listenerRegistration);
    }

    public <T, O extends T> void put(Class<T> type, O object) {
        put(type, null, object);
    }

    public <T, O extends T> void put(Class<? super T> type, String name, O object) {
        put(new ObjectQualifier<>(type, name), object);
    }

    public <T, O extends T> void put(ObjectQualifier<? super T> objectQualifier, O object) {
        ObjectRegistration<O> objectRegistration = new ObjectRegistration<>(objectQualifier, object);

        if (putRegistration(objectRegistration)) {
            onObjectRegistered(objectRegistration);
            return;
        }

        String msg = MessageFormat.format("Object ''{0}'' is already set.", objectQualifier);
        throw new IllegalArgumentException(msg);
    }

    public <T> T get(Class<T> type) {
        return get(type, null);
    }

    public <T> T get(Class<T> type, String name) {
        return get(new ObjectQualifier<T>(type, name));
    }

    public <T> T get(ObjectQualifier<T> objectQualifier) {
        ObjectRegistration<?> objectRegistration = getRegistration(objectQualifier);

        if (objectRegistration == null) {
            return null;
        }

        return (T) objectRegistration.getObject();
    }

    public <T> void addViewContextListener(Class<? super T> type, ViewContextListener<T> viewContextListener) {
        addViewContextListener(type, null, viewContextListener);
    }

    public <T> void addViewContextListener(Class<? super T> type, String name, ViewContextListener<T> viewContextListener) {
        ObjectQualifier<? super T> objectQualifier = new ObjectQualifier<>(type, name);

        addViewContextListener(objectQualifier, viewContextListener);
    }

    public <T> void removeViewContextListener(Class<? super T> type, ViewContextListener<T> viewContextListener) {
        removeViewContextListener(type, null, viewContextListener);
    }

    public <T> void removeViewContextListener(Class<? super T> type, String name, ViewContextListener<T> viewContextListener) {
        ObjectQualifier<? super T> objectQualifier = new ObjectQualifier<>(type, name);
        removeViewContextListener(objectQualifier, viewContextListener);
    }

    protected abstract boolean putRegistration(ObjectRegistration<?> objectRegistration);

    protected abstract <T> void onViewContextListenerAdded(ObjectQualifier<? super T> objectQualifier, ViewContextListener<T> viewContextListener, ViewContextListenerRegistration<T> listenerRegistration);


    protected <T> void onObjectRegistered(ObjectRegistration<T> objectRegistration) {
        for (ViewContextListenerRegistration<?> listenerRegistration : viewContextListenerRegistrations) {
            ObjectQualifier<? super T> objectQualifier = objectRegistration.getObjectQualifier();
            ViewContextListener<T> viewContextListener = listenerRegistration.cast(objectQualifier);
            if (viewContextListener != null) {
                T object = objectRegistration.getObject();
                viewContextListener.modelAdded(object);
            }
        }
    }


    protected <T> void onObjectUnregistered(ObjectRegistration<T> removedRegistration) {
        for (ViewContextListenerRegistration<?> listenerRegistration : viewContextListenerRegistrations) {
            ObjectQualifier<? super T> objectQualifier = removedRegistration.getObjectQualifier();
            ViewContextListener<T> viewContextListener = listenerRegistration.cast(objectQualifier);
            if (viewContextListener != null) {
                T removedObject = removedRegistration.getObject();
                viewContextListener.modelRemoved(removedObject);
            }
        }
    }
}
