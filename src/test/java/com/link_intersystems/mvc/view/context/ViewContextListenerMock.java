package com.link_intersystems.mvc.view.context;

import java.util.LinkedList;
import java.util.List;

public class ViewContextListenerMock<T> implements ViewContextListener<T> {

    private LinkedList<T> models = new LinkedList<>();

    @Override
    public void modelAdded(T model) {
        models.add(model);
    }

    @Override
    public void modelRemoved(T model) {
        models.remove(model);
    }

    public List<T> getModels() {
        return models;
    }

    public T getLast() {
        if (models.isEmpty()) {
            return null;
        }
        return models.getLast();
    }
}
