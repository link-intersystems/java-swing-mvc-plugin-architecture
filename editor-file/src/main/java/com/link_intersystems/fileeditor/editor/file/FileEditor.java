package com.link_intersystems.fileeditor.editor.file;

import com.link_intersystems.fileeditor.editor.Editor;
import com.link_intersystems.fileeditor.view.View;

import javax.swing.*;
import java.io.File;

class FileEditor implements Editor {

    private File file;

    public FileEditor(File file) {
        this.file = file;
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public Action getCloseAction() {
        return null;
    }

    @Override
    public View getView() {
        FileEditorView fileEditorView = new FileEditorView(file);
        return fileEditorView;
    }
};