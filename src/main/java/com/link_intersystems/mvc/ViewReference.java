package com.link_intersystems.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import static java.util.Objects.*;

public class ViewReference {

    private String viewName;
    private ControllerSupplier controllerSupplier;

    public ViewReference(String viewName, ControllerSupplier controllerSupplier) {
        this.viewName = requireNonNull(viewName);
        this.controllerSupplier = requireNonNull(controllerSupplier);
    }

    public View create(View parentView) {
        ServiceLoader<ViewFactory> viewFactories = ServiceLoader.load(ViewFactory.class);

        List<ViewFactory> matchingViewFactories = new ArrayList<>();

        for (ViewFactory viewFactory : viewFactories) {
            if (viewFactory.getName().equals(viewName)) {
                matchingViewFactories.add(viewFactory);
            }
        }

        switch (matchingViewFactories.size()) {
            case 0:
                throw new IllegalArgumentException("No view named " + viewName + " exists.");
            case 1:
                return matchingViewFactories.get(0).createView(parentView);
            default:
                throw new IllegalArgumentException("Multiple views named " + viewName + " exists: " + matchingViewFactories);
        }
    }
}
