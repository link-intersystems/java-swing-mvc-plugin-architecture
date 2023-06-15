package com.link_intersystems.swing.menu;

import com.link_intersystems.swing.context.ViewContext;

import javax.swing.*;

public interface MenuContribution {
    String getMenuPath();

    Action getAction(ViewContext viewContext);
}
