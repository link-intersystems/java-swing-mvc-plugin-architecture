package com.link_intersystems.fileeditor.view;

import com.link_intersystems.fileeditor.context.ViewContext;

public interface ViewSite {
    default public <T> T getService(Class<T> type) {
        return getService(type, null);
    }

    public <T> T getService(Class<T> type, String name);

    public ViewContent getViewLocation();

    public ViewContext getViewContext();


}
