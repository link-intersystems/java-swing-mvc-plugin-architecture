package com.link_intersystems.swing.view.window;

import java.awt.*;

import static java.util.Objects.*;

public class SubWindowViewContent extends WindowViewContent {

    private Component parent;

    public SubWindowViewContent(Component parent) {
        this.parent = requireNonNull(parent);
    }

    @Override
    public Component getParent() {
        return parent;
    }
}
