package com.link_intersystems.fileeditor.editor.file;

import com.link_intersystems.swing.action.concurrent.DirectTaskExecutor;
import com.link_intersystems.swing.view.ViewSiteMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class FileEditorViewTest {

    @Test
    void install(@TempDir File tempDir) throws IOException {
        File testFile = createFile(tempDir, "test", "Hello World");
        FileEditorView fileEditorView = createTestableFileEditorView(testFile);
        ViewSiteMock viewSite = new ViewSiteMock();

        fileEditorView.install(viewSite);

        JEditorPane editorPane = getEditorPane(viewSite);
        String text = editorPane.getText();
        assertEquals("Hello World", text);
    }

    private JEditorPane getEditorPane(ViewSiteMock viewSite) {
        Component content = viewSite.getContent();
        JScrollPane scrollPane = assertInstanceOf(JScrollPane.class, content);
        return assertInstanceOf(JEditorPane.class, scrollPane.getViewport().getView());
    }

    private File createFile(File tempDir, String filename, String fileContent) throws IOException {
        File testFile = new File(tempDir, filename);
        testFile.createNewFile();
        try (FileOutputStream fout = new FileOutputStream(testFile)) {
            fout.write(fileContent.getBytes(StandardCharsets.UTF_8));
        }
        return testFile;
    }

    private FileEditorView createTestableFileEditorView(File file) {
        FileEditorView fileEditorView = new FileEditorView() {
            @Override
            protected OpenFileAction getOpenFileAction(File file) {
                OpenFileAction openFileAction = super.getOpenFileAction(file);
                openFileAction.setTaskExecutor(new DirectTaskExecutor());
                return openFileAction;
            }
        };
        fileEditorView.setEditorInput(new FileEditorInput(file));
        return fileEditorView;
    }

}