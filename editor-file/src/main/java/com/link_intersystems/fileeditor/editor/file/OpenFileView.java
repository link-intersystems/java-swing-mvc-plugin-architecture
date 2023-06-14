package com.link_intersystems.fileeditor.editor.file;

import com.link_intersystems.fileeditor.context.ViewContext;
import com.link_intersystems.fileeditor.editor.EditorManager;
import com.link_intersystems.fileeditor.view.AbstractView;
import com.link_intersystems.fileeditor.view.ViewContent;
import com.link_intersystems.fileeditor.view.ViewSite;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class OpenFileView extends AbstractView {

    private JFileChooser fileChooser = new JFileChooser();

    @Override
    protected void doInstall(ViewSite viewSite) {
        ViewContent viewContent = viewSite.getViewContent();
        Component parentComponent = viewContent.getParent();
        int returnVal = fileChooser.showOpenDialog(parentComponent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File selectedFile = fileChooser.getSelectedFile();
            ViewContext viewContext = viewSite.getViewContext();
            EditorManager editorManager = viewContext.get(EditorManager.class);
            editorManager.addEditor(new FileEditor(selectedFile));
        }

        uninstall();
    }

    @Override
    protected void doUninstall(ViewSite viewSite) {

    }
}
