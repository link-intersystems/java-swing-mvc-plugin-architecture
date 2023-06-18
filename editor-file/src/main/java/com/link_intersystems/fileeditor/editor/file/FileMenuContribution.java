package com.link_intersystems.fileeditor.editor.file;

import com.link_intersystems.util.context.Context;
import com.link_intersystems.swing.menu.MenuContribution;
import com.link_intersystems.swing.view.RootViewSite;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FileMenuContribution implements MenuContribution {
    @Override
    public String getMenuPath() {
        return "file";
    }

    @Override
    public Action getAction(Context context) {
        AbstractAction abstractAction = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                OpenFileView openFileView = new OpenFileView();
                openFileView.install(new RootViewSite() {
                    @Override
                    public Context getViewContext() {
                        return context;
                    }
                });
            }
        };


        abstractAction.putValue(Action.NAME, "Open File");
        return abstractAction;
    }
}
