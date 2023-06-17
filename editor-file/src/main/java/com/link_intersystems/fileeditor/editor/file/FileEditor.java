package com.link_intersystems.fileeditor.editor.file;

import com.link_intersystems.fileeditor.editor.Editor;
import com.link_intersystems.swing.view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

class FileEditor implements Editor {

    private final FileEditorView fileEditorView;
    private File file;

    public FileEditor(File file) {
        this.file = file;
        fileEditorView = new FileEditorView(file);
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public Action getCloseAction() {

        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileEditorView.uninstall();
            }
        };
    }

    @Override
    public View getView() {
        return fileEditorView;
    }
}