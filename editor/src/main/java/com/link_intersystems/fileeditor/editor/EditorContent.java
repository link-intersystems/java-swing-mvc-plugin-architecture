package com.link_intersystems.fileeditor.editor;

import com.link_intersystems.swing.view.ViewContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class EditorContent implements ViewContent {

    private EditorView editor;
    private JTabbedPane tabbedPane;
    private EditorTabComponent editorTabComponent = new EditorTabComponent();
    private Component component;

    public EditorContent(EditorView editor, JTabbedPane tabbedPane) {
        this.editor = editor;
        this.tabbedPane = tabbedPane;
    }

    @Override
    public void setComponent(Component component) {
        int tabIndex = getTabIndex();
        this.component = component;

        if (component == null) {
            if (tabIndex > -1) {
                tabbedPane.removeTabAt(tabIndex);
            }
        } else {
            if (tabIndex < 0) {
                addTab(component);
                initTab();
            }
        }

    }

    private void addTab(Component component) {
        tabbedPane.addTab(editor.getName(), component);
    }

    public void initTab() {
        setTabComponent(null);

        TabModel tabModel = createTabModel(editor);
        editorTabComponent.setModel(tabModel);

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
        return tabbedPane.indexOfComponent(component);
    }

    private void setTabComponent(Component tabComponentAt) {
        int tabIndex = getTabIndex();
        tabbedPane.setTabComponentAt(tabIndex, tabComponentAt);
    }

    public void addCloseListener(ActionListener actionListener) {
        editorTabComponent.addCloseListener(actionListener);
    }
}