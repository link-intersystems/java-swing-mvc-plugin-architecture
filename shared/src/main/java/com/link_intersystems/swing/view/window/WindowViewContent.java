package com.link_intersystems.swing.view.window;

import com.link_intersystems.swing.view.ViewContent;

import java.awt.*;

public class WindowViewContent implements ViewContent {

    private Component component;

    @Override
    public void setComponent(Component component) {
        if(this.component != null){
            this.component.setVisible(false);
        }

        this.component = component;

        if(this.component != null){
            this.component.setVisible(true);
        }
    }

    @Override
    public Component getParent() {
        return null;
    }
}
