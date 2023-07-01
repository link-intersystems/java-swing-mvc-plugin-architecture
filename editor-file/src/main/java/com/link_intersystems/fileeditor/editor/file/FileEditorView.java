package com.link_intersystems.fileeditor.editor.file;


import com.link_intersystems.fileeditor.editor.EditorInput;
import com.link_intersystems.fileeditor.editor.EditorView;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.concurrent.TaskActionListener;
import com.link_intersystems.swing.view.AbstractView;
import com.link_intersystems.swing.view.ViewContent;
import com.link_intersystems.swing.view.ViewSite;

import javax.swing.*;
import javax.swing.text.Document;
import java.io.File;

public class FileEditorView extends AbstractView implements TaskActionListener<Document, Void>, EditorView {

    private JEditorPane editorPane;
    private FileEditorInput fileEditorInput;

    @Override
    public void setEditorInput(EditorInput editorInput) {
        if (editorInput instanceof FileEditorInput fileEditorInput) {
            this.fileEditorInput = fileEditorInput;
            return;
        }

        throw new IllegalArgumentException("Can not handle " + fileEditorInput);
    }

    @Override
    public String getName() {
        if (fileEditorInput != null) {
            return fileEditorInput.getName();
        }

        return "";
    }

    @Override
    public void doInstall(ViewSite viewSite) {
        editorPane = new JEditorPane();
        JScrollPane editorScrollPane = new JScrollPane(editorPane);

        ViewContent viewContent = viewSite.getViewContent();
        viewContent.setComponent(editorScrollPane);

        File file = fileEditorInput.getFile();
        OpenFileAction openFileAction = getOpenFileAction(file);
        openFileAction.setTaskActionListener(this);

        ActionTrigger.performAction(this, openFileAction);
    }

    protected OpenFileAction getOpenFileAction(File file) {
        return new OpenFileAction(file);
    }

    @Override
    protected void doUninstall(ViewSite viewSite) {
        super.doUninstall(viewSite);
        ViewContent viewContent = viewSite.getViewContent();
        viewContent.setComponent(null);
        editorPane = null;
    }

    @Override
    public void done(Document result) {
        if (editorPane != null) {
            editorPane.setDocument(result);
        }
    }
}
