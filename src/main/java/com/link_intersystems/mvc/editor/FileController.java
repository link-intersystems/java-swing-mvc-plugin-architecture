package com.link_intersystems.mvc.editor;

import com.link_intersystems.swing.action.AbstractTaskAction;
import com.link_intersystems.swing.action.TaskProgress;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.io.*;
import java.nio.CharBuffer;

public class FileController extends AbstractTaskAction<String, Void> {

    private File file;

    public FileController(){

    }

    @Override
    protected void prepareExecution() throws Exception {
        if (file == null) {
            throw new RuntimeException("Abort File open");
        }
    }

    @Override
    protected String doInBackground(TaskProgress<Void> taskProgress) throws Exception {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            CharBuffer charBuffer = CharBuffer.allocate(4096);
            while(reader.read(charBuffer) != 0){
                charBuffer.flip();
                sw.append(charBuffer);
            }

        }
        pw.flush();
        pw.close();
        return sw.toString();
    }


    @Override
    protected void done(String result) {
        PlainDocument plainDocument = new PlainDocument();
        try {
            plainDocument.insertString(0, result, null);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }

//        FileEditorView fileEditor = new FileEditorView();
//        fileEditor.setDocument(plainDocument);
//
//        editorManager.openEditor(fileEditor.getComponent());
    }
}
