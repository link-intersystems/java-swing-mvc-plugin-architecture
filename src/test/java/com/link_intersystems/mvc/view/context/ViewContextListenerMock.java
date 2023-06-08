package com.link_intersystems.mvc.view.context;

import java.util.LinkedList;
import java.util.List;

public class ViewContextListenerMock<T> implements ViewContextListener<T> {

    private LinkedList<T> models = new LinkedList<>();

    @Override
    public void objectAdded(ViewContext viewContext, T object) {
        models.add(object);
    }

    @Override
    public void objectRemoved(ViewContext viewContext, T object) {
        models.remove(object);
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
