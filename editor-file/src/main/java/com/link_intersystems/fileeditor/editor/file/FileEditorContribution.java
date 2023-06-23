package com.link_intersystems.fileeditor.editor.file;

import com.link_intersystems.fileeditor.editor.EditorContribution;
import com.link_intersystems.fileeditor.editor.EditorInput;
import com.link_intersystems.fileeditor.editor.EditorView;

public class FileEditorContribution implements EditorContribution {
    @Override
    public EditorView accept(Class<? extends EditorInput> editorInputType) {
        if(FileEditorInput.class.isAssignableFrom(editorInputType)){
            return new FileEditorView();
        }
        return null;
    }
}
