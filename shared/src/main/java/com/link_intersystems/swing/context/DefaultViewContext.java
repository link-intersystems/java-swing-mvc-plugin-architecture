package com.link_intersystems.swing.context;


import java.util.HashMap;
import java.util.Map;

public class DefaultViewContext extends AbstractViewContext {

    private Map<ObjectQualifier<?>, ObjectRegistration<?>> objectsByQualifier = new HashMap<>();

    @Override
    protected boolean putRegistration(ObjectRegistration<?> objectRegistration) {
        return objectsByQualifier.put(objectRegistration.getObjectQualifier(), objectRegistration) == null;
    }

    @Override
    protected ObjectRegistration<?> removeRegistration(ObjectQualifier<?> objectQualifier) {
        return objectsByQualifier.remove(objectQualifier);
    }

    @Override
    protected <T> ObjectRegistration<T> getRegistration(ObjectQualifier<? super T> objectQualifier) {
        return (ObjectRegistration<T>) objectsByQualifier.get(objectQualifier);
    }


}
