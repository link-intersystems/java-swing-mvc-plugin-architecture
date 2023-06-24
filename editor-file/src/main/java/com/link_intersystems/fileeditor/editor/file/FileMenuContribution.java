package com.link_intersystems.fileeditor.editor.file;

import com.link_intersystems.swing.menu.MenuContribution;
import com.link_intersystems.swing.view.ViewSite;
import com.link_intersystems.swing.view.action.ViewInstallAction;
import com.link_intersystems.util.context.Context;

import javax.swing.*;
import java.util.function.Supplier;

import static com.link_intersystems.fileeditor.main.MainViewLayout.*;

public class FileMenuContribution implements MenuContribution {

    @Override
    public String getMenuPath() {
        return "file";
    }

    @Override
    public Action getAction(Context context) {
        Supplier<ViewSite> mainViewSiteSupplier = context.getSupplier(ViewSite.class, MAIN_VIEW_SITE);
        ViewInstallAction openFileViewAction = new ViewInstallAction(mainViewSiteSupplier, OpenFileView::new);
        openFileViewAction.putValue(Action.NAME, "Open File");
        return openFileViewAction;
    }
}
