package com.link_intersystems.fileeditor.editor;

import com.link_intersystems.swing.view.ViewContent;

import java.awt.*;

import static java.util.Objects.*;

class TabContent implements ViewContent {

    private EditorContent editorContent;

    public TabContent(EditorContent editorContent) {
        this.editorContent = requireNonNull(editorContent);
    }

    @Override
    public void setComponent(Component component) {
        editorContent.setTabComponentAt(null);
        editorContent.setTabComponentAt(component);
    }

    @Override
    public Component getParent() {
        return editorContent.getParent();
    }
}
