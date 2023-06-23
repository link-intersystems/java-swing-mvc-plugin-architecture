package com.link_intersystems.fileeditor.editor;

public interface EditorContribution {
    EditorView accept(Class<? extends EditorInput> editorInputType);
}
