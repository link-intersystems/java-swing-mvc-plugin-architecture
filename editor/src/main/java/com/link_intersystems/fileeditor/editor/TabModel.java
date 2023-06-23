package com.link_intersystems.fileeditor.editor;

import java.awt.event.ActionListener;

interface TabModel {

    public String getTitle();

    default public ActionListener getCloseActionListener() {
        return null;
    }
}
