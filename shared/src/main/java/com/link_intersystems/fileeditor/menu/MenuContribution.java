package com.link_intersystems.fileeditor.menu;

import com.link_intersystems.fileeditor.context.ViewContext;

import javax.swing.*;

public interface MenuContribution {
    String getMenuPath();

    Action getAction(ViewContext viewContext);
}
