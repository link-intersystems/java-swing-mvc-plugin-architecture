package com.link_intersystems.swing.service;

public interface ServiceLocator {
    default public <T> T getService(Class<T> type) {
        return getService(type, null);
    }

    <T> T getService(Class<T> type, String name);
}
