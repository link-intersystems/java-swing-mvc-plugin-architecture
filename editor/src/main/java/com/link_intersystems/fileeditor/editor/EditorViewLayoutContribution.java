package com.link_intersystems.fileeditor.editor;

import com.link_intersystems.swing.view.layout.AbstractViewLayoutContribution;
import com.link_intersystems.swing.view.layout.ViewLayout;

public class EditorViewLayoutContribution extends AbstractViewLayoutContribution {

    @Override
    public void doInstall(ViewLayout viewLayout) {
        EditorManagerView editorManagerView = new EditorManagerView();
        viewLayout.install("editorSite", editorManagerView);
    }
}
