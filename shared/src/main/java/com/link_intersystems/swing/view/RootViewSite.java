package com.link_intersystems.swing.view;

import com.link_intersystems.swing.view.window.WindowViewContent;
import com.link_intersystems.util.context.Context;
import com.link_intersystems.util.context.DefaultContext;

public class RootViewSite extends DefaultViewSite {

    public RootViewSite() {
        this(new DefaultContext());
    }

    public RootViewSite(Context viewContext) {
        super(new WindowViewContent(), viewContext);
    }

}
