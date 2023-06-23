package com.link_intersystems.fileeditor.editor;


import com.link_intersystems.swing.view.AbstractView;
import com.link_intersystems.swing.view.View;
import com.link_intersystems.swing.view.ViewContent;
import com.link_intersystems.swing.view.ViewSite;
import com.link_intersystems.util.context.Context;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

class EditorManagerView extends AbstractView implements EditorManager {

    private JTabbedPane editorsPane;
    private List<View> subViews = new ArrayList<>();

    @Override
    public void doInstall(ViewSite viewSite) {
        editorsPane = new JTabbedPane();
        ViewContent viewContent = viewSite.getViewContent();
        viewContent.setComponent(editorsPane);

        Context viewContext = viewSite.getViewContext();
        viewContext.put(EditorManager.class, this);
    }

    @Override
    protected void doUninstall(ViewSite viewSite) {
        super.doUninstall(viewSite);

        Context viewContext = viewSite.getViewContext();
        viewContext.remove(EditorManager.class);

        subViews.forEach(View::uninstall);
        subViews.clear();

        editorsPane = null;
    }

    @Override
    public void addEditor(EditorInput editorInput) {
        if (editorsPane != null) {
            EditorView editorView = findEditorView(editorInput);

            if (editorView == null) {
                return;
            }

            editorView.setEditorInput(editorInput);

            EditorContent editorContent = new EditorContent(editorView, editorsPane);
            ViewSite editorViewSite = createSubViewSite(editorContent);
            editorView.install(editorViewSite);

            editorContent.addCloseListener(ae -> {
                editorView.uninstall();
            });

            subViews.add(editorView);
            editorsPane.setSelectedIndex(editorContent.getTabIndex());
        }
    }

    private EditorView findEditorView(EditorInput editorInput) {
        ServiceLoader<EditorContribution> editorContributions = ServiceLoader.load(EditorContribution.class);

        EditorView editorView = null;

        for (EditorContribution editorContribution : editorContributions) {
            editorView = editorContribution.accept(editorInput.getClass());
            if (editorView != null) {
                break;
            }
        }

        return editorView;
    }

}
