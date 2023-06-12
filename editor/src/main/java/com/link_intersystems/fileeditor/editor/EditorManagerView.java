package com.link_intersystems.fileeditor.editor;


import com.link_intersystems.fileeditor.context.ViewContext;
import com.link_intersystems.fileeditor.view.*;

import javax.swing.*;

class EditorManagerView extends AbstractView implements EditorManager {

    private JTabbedPane editorsPane;

    @Override
    public void doInstall(ViewSite viewSite) {
        editorsPane = new JTabbedPane();
        ViewContent viewLocation = viewSite.getViewLocation();
        viewLocation.setComponent(editorsPane);

        ViewContext viewContext = viewSite.getViewContext();
        viewContext.put(EditorManager.class, this);
    }

    @Override
    protected void doUninstall(ViewSite viewSite) {
        super.doUninstall(viewSite);

        ViewContext viewContext = viewSite.getViewContext();
        viewContext.remove(EditorManager.class);

        editorsPane = null;
    }

    @Override
    public void addEditor(Editor editor) {
        if (editorsPane != null) {
            SubViewSite subViewSite = createSubViewSite(new EditorContent(editor, editorsPane));
            View view = editor.getView();
            view.install(subViewSite);
            editorsPane.setSelectedIndex(editorsPane.getTabCount() - 1);
        }
    }
}
