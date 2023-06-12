package com.link_intersystems.fileeditor.view.service;

public interface ActionFactory {

    public <T> T createAction(Class<T> type);
}
