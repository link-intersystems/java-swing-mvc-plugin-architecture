package com.link_intersystems.util.context;

import com.link_intersystems.util.context.Context;
import com.link_intersystems.util.context.ContextListener;

import java.util.LinkedList;
import java.util.List;

public class ContextListenerMock<T> implements ContextListener<T> {


    private LinkedList<T> objects = new LinkedList<>();


    @Override
    public void added(Context context, T object) {
        objects.add(object);
    }

    @Override
    public void removed(Context context, T object) {
        objects.remove(object);
    }

    public List<T> getObjects() {
        return objects;
    }

    public T getLastest() {
        if (objects.isEmpty()) {
            return null;
        }
        return objects.getLast();
    }
}
