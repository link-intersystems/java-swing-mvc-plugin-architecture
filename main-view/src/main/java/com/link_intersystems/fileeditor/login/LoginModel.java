package com.link_intersystems.fileeditor.login;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class LoginModel {

    private BoundedRangeModel initProgress = new DefaultBoundedRangeModel();
    private Document usernameDocument = new PlainDocument();
    private Document passwordDocument = new PlainDocument();

    public BoundedRangeModel getProgressModel() {
        return initProgress;
    }

    public Document getUsernameDocument() {
        return usernameDocument;
    }

    public Document getPasswordDocument() {
        return passwordDocument;
    }
}
