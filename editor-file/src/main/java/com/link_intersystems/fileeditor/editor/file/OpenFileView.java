package com.link_intersystems.fileeditor.editor.file;

import com.link_intersystems.fileeditor.editor.EditorManager;
import com.link_intersystems.swing.view.AbstractView;
import com.link_intersystems.swing.view.ViewContent;
import com.link_intersystems.swing.view.ViewSite;
import com.link_intersystems.util.context.Context;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class OpenFileView extends AbstractView {

    @Override
    protected void doInstall(ViewSite viewSite) {
        ViewContent viewContent = viewSite.getViewContent();
        Component parentComponent = viewContent.getParent();
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(parentComponent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File selectedFile = fileChooser.getSelectedFile();
            Context viewContext = viewSite.getViewContext();
            EditorManager editorManager = viewContext.get(EditorManager.class);
            editorManager.addEditor(new FileEditorInput(selectedFile));
        }

        uninstall();
    }
}
