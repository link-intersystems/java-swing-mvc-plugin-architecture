package com.link_intersystems.fileeditor.editor;

import com.link_intersystems.swing.view.ViewContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class EditorContent implements ViewContent {

    private EditorView editor;
    private JTabbedPane tabbedPane;
    private Integer tabIndex;
    private EditorTabComponent editorTabComponent = new EditorTabComponent();

    public EditorContent(EditorView editor, JTabbedPane tabbedPane) {
        this.editor = editor;
        this.tabbedPane = tabbedPane;
    }

    @Override
    public void setComponent(Component component) {
        if (component == null) {
            if (tabIndex != null) {
                tabbedPane.removeTabAt(tabIndex);
                tabIndex = null;
            }
        } else {
            if (tabIndex == null) {
                addTab(component);
                initTab();
            }
        }
    }

    private void addTab(Component component) {
        tabbedPane.addTab(editor.getName(), component);
        tabIndex = tabbedPane.getTabCount() - 1;
    }

    public void initTab() {
        setTabComponent(null);

        TabModel tabModel = createTabModel(editor);
        editorTabComponent.setTabModel(tabModel);

        setTabComponent(editorTabComponent);
    }


    private TabModel createTabModel(EditorView editor) {
        return new TabModel() {

            @Override
            public String getTitle() {
                return editor.getName();
            }

            @Override
            public ActionListener getCloseActionListener() {
                return editor.getCloseAction();
            }
        };
    }

    @Override
    public Component getParent() {
        return tabbedPane;
    }

    int getTabIndex() {
        return tabIndex != null ? tabIndex : -1;
    }

    private void setTabComponent(Component tabComponentAt) {
        tabbedPane.setTabComponentAt(getTabIndex(), tabComponentAt);
    }

    public void addCloseListener(ActionListener actionListener) {
        editorTabComponent.addCloseListener(actionListener);
    }
}