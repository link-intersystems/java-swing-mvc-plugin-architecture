package com.link_intersystems.fileeditor.editor;

import com.link_intersystems.fileeditor.view.ViewContent;

import javax.swing.*;
import java.awt.*;

class EditorContent implements ViewContent {

    private Editor editor;
    private JTabbedPane tabbedPane;

    public EditorContent(Editor editor, JTabbedPane tabbedPane) {
        this.editor = editor;
        this.tabbedPane = tabbedPane;
    }

    @Override
    public void setComponent(Component component) {
        tabbedPane.addTab(editor.getName(), component);
    }

    @Override
    public Component getParent() {
        return tabbedPane;
    }
}