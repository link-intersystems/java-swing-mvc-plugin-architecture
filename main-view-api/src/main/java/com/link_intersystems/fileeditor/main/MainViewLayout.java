package com.link_intersystems.fileeditor.main;

import com.link_intersystems.swing.view.layout.DefaultViewLayout;
import com.link_intersystems.swing.view.layout.ViewLayout;
import com.link_intersystems.util.context.Context;

import java.awt.*;

public abstract class MainViewLayout {

    public static final String MAIN_VIEW_LAYOUT = "main";

    public static final String MAIN_VIEW_SITE = "main";
    public static final String EDITOR_VIEW_SITE = "editor";
    public static final String MENU_VIEW_SITE = "menu";

    static ViewLayout createViewLayout(Context viewContext, Container contentPane) {
        DefaultViewLayout viewLayout = new DefaultViewLayout(viewContext, contentPane);

        viewLayout.addViewSite(MENU_VIEW_SITE, BorderLayout.NORTH);
        viewLayout.addViewSite(EDITOR_VIEW_SITE, BorderLayout.CENTER);

        return viewLayout;
    }
}
