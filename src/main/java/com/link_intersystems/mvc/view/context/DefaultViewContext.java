package com.link_intersystems.mvc.view.context;

import com.link_intersystems.mvc.ObjectRegistration;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.*;

public class DefaultViewContext extends ViewContext {

    private Map<ObjectQualifier<?>, ObjectRegistration<?>> objectsByQualifier = new HashMap<>();

    @Override
    protected boolean putRegistration(ObjectRegistration<?> objectRegistration) {
        return objectsByQualifier.put(objectRegistration.getObjectQualifier(), objectRegistration) == null;
    }

    @Override
    protected <T> void onViewContextListenerAdded(ObjectQualifier<? super T> objectQualifier, ViewContextListener<T> viewContextListener, ViewContextListenerRegistration<T> listenerRegistration) {
        ObjectRegistration<T> registration = (ObjectRegistration<T>) objectsByQualifier.get(requireNonNull(objectQualifier));
        if (registration != null) {
            viewContextListener.modelAdded(registration.getObject());
        }
    }

    public <T> void remove(Class<? super T> type) {
        remove(type, null);
    }

    @Override
    protected ObjectRegistration<?> removeRegistration(ObjectQualifier<?> objectQualifier) {
        if (objectsByQualifier.containsKey(objectQualifier)) {
            return objectsByQualifier.remove(objectQualifier);
        }

        return null;
    }

    @Override
    protected <T> ObjectRegistration<?> getRegistration(ObjectQualifier<T> objectQualifier) {
        return objectsByQualifier.get(requireNonNull(objectQualifier));
    }


}
