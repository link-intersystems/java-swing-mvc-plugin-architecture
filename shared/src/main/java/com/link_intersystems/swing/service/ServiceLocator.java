package com.link_intersystems.swing.service;

public interface ServiceLocator {

    public static ServiceLocator nullInstance(){
        return new ServiceLocator() {
            @Override
            public <T> T getService(Class<T> type, String name) {
                return null;
            }
        };
    }

    default public <T> T getService(Class<T> type) {
        return getService(type, null);
    }

    <T> T getService(Class<T> type, String name);
}
