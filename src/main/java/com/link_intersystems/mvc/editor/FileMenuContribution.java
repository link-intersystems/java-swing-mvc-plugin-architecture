package com.link_intersystems.mvc.editor;

import com.link_intersystems.mvc.ControllerSupplier;
import com.link_intersystems.mvc.View;
import com.link_intersystems.mvc.action.menu.MenuContribution;
import com.link_intersystems.mvc.main.ApplicationView;

import javax.swing.*;

public class FileMenuContribution implements MenuContribution {
    @Override
    public String getMenuPath() {
        return "file";
    }

    @Override
    public Action getAction(View parentView) {
        FileController fileController = null;
        fileController.putValue(Action.NAME, "Open");
        return fileController;
    }
}
