package com.link_intersystems.mvc.editor;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class FileEditorView implements EditorView {

    private JEditorPane editorPane = new JEditorPane();
    private String name;

    public FileEditorView(JEditorPane editorPane, String name) {
        this.editorPane = editorPane;
        this.name = name;
    }

    public void setDocument(PlainDocument plainDocument) {
        editorPane.setDocument(plainDocument);
    }


    public Component getComponent() {
        return new JScrollPane(editorPane);
    }

    @Override
    public void install(EditorViewPart editorViewPart) {
        editorViewPart.setCloseAction(getCloseAction());
    }

    private Action getCloseAction() {
        return null;
    }


}
