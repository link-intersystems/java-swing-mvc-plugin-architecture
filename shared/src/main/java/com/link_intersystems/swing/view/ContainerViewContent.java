package com.link_intersystems.swing.view;

import java.awt.*;
import java.util.Objects;

public class ContainerViewContent implements ViewContent {

    private Container container;
    private Object contentLayoutConstraints;

    private Component viewContent;

    public ContainerViewContent(Container container, Object contentLayoutConstraints) {
        this.container = Objects.requireNonNull(container);
        this.contentLayoutConstraints = contentLayoutConstraints;
    }

    @Override
    public void setComponent(Component viewContent) {
        if (this.viewContent != null) {
            container.remove(this.viewContent);
        }

        this.viewContent = viewContent;

        if (this.viewContent != null) {
            this.container.add(this.viewContent, contentLayoutConstraints);
        }
    }

    @Override
    public Component getParent() {
        return container;
    }
}
