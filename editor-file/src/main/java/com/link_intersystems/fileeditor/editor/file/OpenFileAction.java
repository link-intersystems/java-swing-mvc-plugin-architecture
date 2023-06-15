package com.link_intersystems.fileeditor.editor.file;

import com.link_intersystems.swing.action.AbstractTaskAction2;
import com.link_intersystems.swing.action.TaskProgress;

import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

import static java.util.Objects.*;

public class OpenFileAction extends AbstractTaskAction2<Document, Void> {

    private File file;

    public OpenFileAction(File file) {
        this.file = requireNonNull(file);
    }

    @Override
    protected Document doInBackground(TaskProgress<Void> taskProgress) throws Exception {

        PlainDocument plainDocument = new PlainDocument();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            CharBuffer charBuffer = CharBuffer.allocate(100);
            while (reader.read(charBuffer) > 0) {
                CharBuffer flippedBuffer = charBuffer.flip();
                String string = flippedBuffer.toString();
                plainDocument.insertString(plainDocument.getLength(), string, null);
            }
        }

        return plainDocument;
    }
}
