package com.link_intersystems.mvc;

public interface ControllerSupplier {
    default public <T> T getController(Class<T> type) {
        return getController(type, null);
    }


    default public <T> T getController(Class<T> type, String name) {
        return getController(new ControllerQualifier<T>(type, name));
    }

    public <T> T getController(ControllerQualifier<T> controllerQualifier);
}
