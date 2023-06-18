package com.link_intersystems.swing.view;

import java.awt.*;

public interface ViewContent {

    public static ViewContent nullInstance() {
        return new ViewContent(){

            @Override
            public void setComponent(Component component) {
            }

            @Override
            public Component getParent() {
                return null;
            }
        };
    }

    void setComponent(Component component);

    Component getParent();
}
