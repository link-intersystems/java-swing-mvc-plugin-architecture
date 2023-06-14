package com.link_intersystems.fileeditor.editor.file;


import com.link_intersystems.fileeditor.action.ActionCallback;
import com.link_intersystems.fileeditor.view.AbstractView;
import com.link_intersystems.fileeditor.view.ViewContent;
import com.link_intersystems.fileeditor.view.ViewSite;
import com.link_intersystems.swing.action.ActionTrigger;

import javax.swing.*;
import javax.swing.text.Document;
import java.io.File;

public class FileEditorView extends AbstractView implements ActionCallback<Document, Void> {

    private JEditorPane editorPane;
    private Document document;
    private File file;

    public FileEditorView(File file) {
        this.file = file;
    }

    @Override
    public void doInstall(ViewSite viewSite) {
        editorPane = new JEditorPane();
        JScrollPane editorScrollPane = new JScrollPane(editorPane);

        ViewContent viewContent = viewSite.getViewContent();
        viewContent.setComponent(editorScrollPane);

        OpenFileAction openFileAction = new OpenFileAction(file);
        openFileAction.setCallback(this);

        ActionTrigger.performAction(this, openFileAction);
    }

    @Override
    protected void doUninstall(ViewSite viewSite) {
        super.doUninstall(viewSite);
        ViewContent viewLocation = viewSite.getViewContent();
        viewLocation.setComponent(null);
        editorPane = null;
    }

    @Override
    public void done(Document result) {
        if (editorPane != null) {
            editorPane.setDocument(result);
        }
    }
}
