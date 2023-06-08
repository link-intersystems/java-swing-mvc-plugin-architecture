package com.link_intersystems.mvc;

public interface Models {

    default public <T> T getModel(Class<T> type) {
        return getModel(type, null);
    }

    public <T> T getModel(Class<T> type, String name);
}
