package com.link_intersystems.fileeditor.editor;


import com.link_intersystems.swing.view.View;

import javax.swing.*;

public interface EditorView extends View {

    public String getName();

    default public Action getCloseAction() {
        return null;
    }

    public void setEditorInput(EditorInput editorInput);
}
