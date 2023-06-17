package com.link_intersystems.fileeditor.editor.file;

import com.link_intersystems.fileeditor.editor.EditorManager;
import com.link_intersystems.swing.view.AbstractView;
import com.link_intersystems.swing.view.Site;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class OpenFileView extends AbstractView {

    @Override
    protected void doInstall(Site viewSite) {
        Component parentComponent = viewSite.getParent();
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(parentComponent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File selectedFile = fileChooser.getSelectedFile();
            EditorManager editorManager = viewSite.get(EditorManager.class);
            editorManager.addEditor(new FileEditor(selectedFile));
        }

        uninstall();
    }
}
