package com.link_intersystems.fileeditor.editor;


import com.link_intersystems.swing.view.View;

import javax.swing.*;

public interface Editor {

    public String getName();

    public Action getCloseAction();

    public View getView();
}
