package com.link_intersystems.swing.view;

import com.link_intersystems.swing.context.ViewContext;
import com.link_intersystems.swing.service.ServiceLocator;

public interface ViewSite extends ServiceLocator, ViewContent, ViewContext {

    default public ServiceLocator getServiceLocator() {
        return this;
    }

}
