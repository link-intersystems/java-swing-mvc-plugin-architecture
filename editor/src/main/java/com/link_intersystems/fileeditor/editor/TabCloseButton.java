package com.link_intersystems.fileeditor.editor;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;

class TabCloseButton extends JButton {

    public static final String CROSS_MARK = "\u2715";

    public TabCloseButton() {
        super(CROSS_MARK);

        Border border = getBorder();
        BorderDelegate borderDelegate = new BorderDelegate(border) {

            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                if(!getModel().isArmed()){
                    return;
                }
                super.paintBorder(c, g, x, y, width, height);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(0,3,3,3);
            }
        };
        CompoundBorder compoundBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 5, 0, 0), borderDelegate);
        setBorder(compoundBorder);
        setFocusPainted(false);
        setContentAreaFilled(false);
    }
}
