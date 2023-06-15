package com.link_intersystems.fileeditor.editor.file;


import com.link_intersystems.swing.action.ActionCallback;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.view.AbstractView;
import com.link_intersystems.swing.view.ViewSite;

import javax.swing.*;
import javax.swing.text.Document;
import java.io.File;

public class FileEditorView extends AbstractView implements ActionCallback<Document, Void> {

    private JEditorPane editorPane;
    private File file;

    public FileEditorView(File file) {
        this.file = file;
    }

    @Override
    public void doInstall(ViewSite viewSite) {
        editorPane = new JEditorPane();
        JScrollPane editorScrollPane = new JScrollPane(editorPane);

        viewSite.setComponent(editorScrollPane);

        OpenFileAction openFileAction = new OpenFileAction(file);
        openFileAction.setCallback(this);

        ActionTrigger.performAction(this, openFileAction);
    }

    @Override
    protected void doUninstall(ViewSite viewSite) {
        super.doUninstall(viewSite);
        viewSite.setComponent(null);
        editorPane = null;
    }

    @Override
    public void done(Document result) {
        if (editorPane != null) {
            editorPane.setDocument(result);
        }
    }
}
