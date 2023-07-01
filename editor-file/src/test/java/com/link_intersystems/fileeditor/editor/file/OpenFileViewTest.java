package com.link_intersystems.fileeditor.editor.file;

import com.link_intersystems.fileeditor.editor.EditorInput;
import com.link_intersystems.fileeditor.editor.EditorManager;
import com.link_intersystems.swing.view.ViewSiteMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OpenFileViewTest {

    private JFileChooser fileChooserSpy;
    private OpenFileView openFileView;
    private ViewSiteMock viewSiteMock;
    private EditorManager editorManager;
    private File testFile;

    @BeforeEach
    void setUp(@TempDir File tempDir) {
        testFile = new File(tempDir, "test.txt");
        editorManager = mock(EditorManager.class);

        openFileView = new OpenFileView() {
            @Override
            protected int openFileChooser(Component parentComponent, JFileChooser fileChooser) {
                fileChooserSpy = spy(fileChooser);
                doReturn(JFileChooser.APPROVE_OPTION).when(fileChooserSpy).showOpenDialog(parentComponent);
                fileChooser.setSelectedFile(testFile);
                return super.openFileChooser(parentComponent, fileChooserSpy);
            }
        };
        viewSiteMock = new ViewSiteMock();
        viewSiteMock.getViewContext().put(EditorManager.class, editorManager);
    }

    @Test
    void install() {
        openFileView.install(viewSiteMock);

        ArgumentCaptor<EditorInput> editorInputCaptor = ArgumentCaptor.forClass(EditorInput.class);
        verify(editorManager).addEditor(editorInputCaptor.capture());

        EditorInput editorInput = editorInputCaptor.getValue();
        FileEditorInput fileEditorInput = assertInstanceOf(FileEditorInput.class, editorInput);
        File file = fileEditorInput.getFile();
        assertEquals(testFile, file);
    }

}