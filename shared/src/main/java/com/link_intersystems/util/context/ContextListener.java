package com.link_intersystems.util.context;

import java.util.EventListener;

public interface ContextListener<T> extends EventListener {

    public void added(Context context, T instance);

    public void removed(Context context, T instance);

}
