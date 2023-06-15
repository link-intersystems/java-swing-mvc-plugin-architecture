package com.link_intersystems.swing.context;

import java.util.EventListener;

public interface ViewContextListener<T> extends EventListener {

    public void objectAdded(ViewContext viewContext, T object);

    public void objectRemoved(ViewContext viewContext, T object);

}