package com.link_intersystems.fileeditor.editor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EditorContentTest {

    private EditorView editorView;
    private JTabbedPane tabbedPane;
    private EditorContent editorContent;
    private JComponent editorComponent;

    @BeforeEach
    void setUp() {
        editorView = mock(EditorView.class);

        tabbedPane = new JTabbedPane();
        editorContent = new EditorContent(editorView, tabbedPane);
        editorComponent = new JComponent() {
        };
    }


    @Test
    void setComponent() {
        when(editorView.getName()).thenReturn("editor");
        Action closeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        };
        when(editorView.getCloseAction()).thenReturn(closeAction);

        editorContent.setComponent(editorComponent);

        assertEquals(1, tabbedPane.getTabCount());
        assertEquals(editorComponent, tabbedPane.getComponentAt(0));

        Component tabComponent = tabbedPane.getTabComponentAt(0);
        assertTrue(tabComponent instanceof EditorTabComponent);
        EditorTabComponent editorTabComponent = (EditorTabComponent) tabComponent;
        TabModel tabModel = editorTabComponent.getModel();
        assertEquals("editor", tabModel.getTitle());
        assertEquals(closeAction, tabModel.getCloseActionListener());
    }

    @Test
    void setAndUnsetMultipleEditorContents() {
        when(editorView.getName()).thenReturn("");
        EditorContent editorContent2 = new EditorContent(editorView, tabbedPane);
        JComponent editorComponent2 = new JComponent() {
        };

        editorContent.setComponent(editorComponent);
        editorContent2.setComponent(editorComponent2);

        // uninstall first
        editorContent.setComponent(null);

        // uninstall second should not be confused about tab index
        editorContent2.setComponent(null);
    }
}