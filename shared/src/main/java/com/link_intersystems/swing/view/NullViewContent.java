package com.link_intersystems.swing.view;

import java.awt.*;

public class NullViewContent implements ViewContent {
    @Override
    public void setComponent(Component component) {
    }

    @Override
    public Component getParent() {
        return null;
    }
}
