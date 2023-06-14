package com.link_intersystems.fileeditor.editor;

import com.link_intersystems.fileeditor.view.ViewContent;

import javax.swing.*;
import java.awt.*;

class EditorContent implements ViewContent {

    private Editor editor;
    private JTabbedPane tabbedPane;
    private Integer tabIndex;

    public EditorContent(Editor editor, JTabbedPane tabbedPane) {
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
            }
        }
    }

    private void addTab(Component component) {
        tabbedPane.addTab(editor.getName(), component);
        tabIndex = tabbedPane.getTabCount() - 1;
    }

    @Override
    public Component getParent() {
        return tabbedPane;
    }

    public int getTabIndex() {
        return tabIndex != null ? tabIndex : -1;
    }

    public void setTabComponentAt(Component tabComponentAt) {
        tabbedPane.setTabComponentAt(getTabIndex(), tabComponentAt);
    }
}