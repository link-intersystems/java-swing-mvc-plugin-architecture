package com.link_intersystems.fileeditor.editor.file;

import com.link_intersystems.fileeditor.editor.EditorInput;

import java.io.File;

public class FileEditorInput implements EditorInput {

    private File file;

    public FileEditorInput(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String getName() {
        return file.getName();
    }
}
