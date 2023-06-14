package com.link_intersystems.fileeditor.editor;

import java.awt.event.ActionListener;

interface TabModel {

    public String getTitle();

    public ActionListener getCloseActionListener();
}
