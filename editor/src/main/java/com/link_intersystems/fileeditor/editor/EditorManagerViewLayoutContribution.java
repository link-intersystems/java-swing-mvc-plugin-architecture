package com.link_intersystems.fileeditor.editor;

import com.link_intersystems.swing.view.View;
import com.link_intersystems.swing.view.layout.ViewLayoutContribution;

public class EditorManagerViewLayoutContribution implements ViewLayoutContribution {
    @Override
    public String getViewSiteName() {
        return "editorSite";
    }

    @Override
    public View getView() {
        return new EditorManagerView();
    }
}
