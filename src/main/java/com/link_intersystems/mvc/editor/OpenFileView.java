package com.link_intersystems.mvc.editor;

import com.link_intersystems.mvc.View;

import javax.swing.*;
import java.awt.*;

public class OpenFileView extends View {

    private JFileChooser fileChooser = new JFileChooser();
    private View parentView;

    public OpenFileView(View parentView) {
        this.parentView = parentView;
    }

    public void open() {
        Component parentComponent = parentView.createComponent();
        int returnVal = fileChooser.showOpenDialog(parentComponent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        }
    }

    @Override
    public Component createComponent() {
        return fileChooser;
    }

}
