package com.link_intersystems.mvc.view.context;

import java.util.EventListener;

public interface ViewContextListener<T> extends EventListener {

    public void modelAdded(T model);

    public void modelRemoved(T model);

}
