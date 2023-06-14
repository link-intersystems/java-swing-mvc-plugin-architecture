package com.link_intersystems.fileeditor.editor;


import com.link_intersystems.fileeditor.context.ViewContext;
import com.link_intersystems.fileeditor.view.*;

import javax.swing.*;
import java.awt.event.ActionListener;

class EditorManagerView extends AbstractView implements EditorManager {

    private JTabbedPane editorsPane;
    private View editorView;
    private TabView tabView;

    @Override
    public void doInstall(ViewSite viewSite) {
        editorsPane = new JTabbedPane();
        ViewContent viewLocation = viewSite.getViewContent();
        viewLocation.setComponent(editorsPane);

        ViewContext viewContext = viewSite.getViewContext();
        viewContext.put(EditorManager.class, this);
    }

    @Override
    protected void doUninstall(ViewSite viewSite) {
        super.doUninstall(viewSite);

        ViewContext viewContext = viewSite.getViewContext();
        viewContext.remove(EditorManager.class);

        tabView.uninstall();
        tabView = null;

        editorView.uninstall();
        editorView = null;

        editorsPane = null;
    }

    @Override
    public void addEditor(Editor editor) {
        if (editorsPane != null) {
            EditorContent editorContent = new EditorContent(editor, editorsPane);
            SubViewSite subViewSite = createSubViewSite(editorContent);

            editorView = editor.getView();
            editorView.install(subViewSite);

            tabView = new TabView();
            TabModel tabModel = new TabModel(){

                @Override
                public String getTitle() {
                    return editor.getName();
                }

                @Override
                public ActionListener getCloseActionListener() {
                    return editor.getCloseAction();
                }
            };

            tabView.setTabModel(tabModel);
            TabContent tabContent = new TabContent(editorContent);
            tabView.install(createSubViewSite(tabContent));

            editorsPane.setSelectedIndex(editorContent.getTabIndex());
        }
    }
}
