package com.link_intersystems.mvc;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.*;

public class RegistryControllerSupplier implements ControllerSupplier {

    private Map<ControllerQualifier<?>, Object> modelsByQualifier = new HashMap<>();

    public <T, O extends T> void put(Class<T> type, O object) {
        put(type, null, object);
    }

    public <T> void put(Class<? super T> type, String name, T object) {
        ControllerQualifier<? super T> modelQualifier = new ControllerQualifier<>(type, name);

        if (modelsByQualifier.put(modelQualifier, object) == null) {
            return;
        }

        String msg = MessageFormat.format("A object {0} is already registered.", modelQualifier);
        throw new IllegalArgumentException(msg);
    }

    public <T> void remove(Class<? super T> type) {
        remove(type, null);
    }

    public <T> void remove(Class<? super T> type, String name) {
        ControllerQualifier<? super T> modelQualifier = new ControllerQualifier<>(type, name);
        modelsByQualifier.remove(modelQualifier);
    }


    @Override
    public <T> T getController(ControllerQualifier<T> controllerQualifier) {
        return (T) modelsByQualifier.get(requireNonNull(controllerQualifier));
    }

}
