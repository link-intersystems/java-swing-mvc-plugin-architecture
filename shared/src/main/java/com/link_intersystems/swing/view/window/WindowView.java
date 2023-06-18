package com.link_intersystems.swing.view.window;

import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.view.AbstractView;
import com.link_intersystems.swing.view.ViewSite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.WindowConstants.*;

public abstract class WindowView extends AbstractView {

    public static final String ACTION_CLOSE = WindowView.class.getName() + ".closeAction";
    private ActionTrigger actionTrigger = new ActionTrigger(this);

    private Window window;

    private WindowAdapter closeHandler = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            onCloseWindow(getViewSite());
        }
    };

    @Override
    protected void doInstall(ViewSite viewSite) {
        window = createWindow(viewSite);
        setDefaultCloseOperation(window, DO_NOTHING_ON_CLOSE);
        window.addWindowListener(closeHandler);
        viewSite.setComponent(window);
    }

    protected void setDefaultCloseOperation(Window window, int closeOperation) {
        if (window instanceof JFrame frame) {
            frame.setDefaultCloseOperation(closeOperation);
        } else if (window instanceof JDialog dialog) {
            dialog.setDefaultCloseOperation(closeOperation);
        }
    }

    protected abstract Window createWindow(ViewSite viewSite);

    @Override
    protected void doUninstall(ViewSite viewSite) {
        super.doUninstall(viewSite);

        window.removeWindowListener(closeHandler);
        closeHandler = null;

        window.dispose();
        window = null;
    }

    protected void onCloseWindow(ViewSite viewSite) {
        uninstall();
        viewSite.ifContains(Action.class, ACTION_CLOSE, actionTrigger::performAction);
    }
}
