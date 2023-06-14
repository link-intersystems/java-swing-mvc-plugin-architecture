package com.link_intersystems.fileeditor.editor;

import javax.swing.border.Border;
import java.awt.*;

class BorderDelegate implements Border {

    private Border border;

    public BorderDelegate(Border border) {
        this.border = border;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        border.paintBorder(c, g, x, y, width, height);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return border.getBorderInsets(c);
    }

    @Override
    public boolean isBorderOpaque() {
        return border.isBorderOpaque();
    }
}
