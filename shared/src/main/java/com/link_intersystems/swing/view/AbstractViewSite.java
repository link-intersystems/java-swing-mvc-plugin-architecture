package com.link_intersystems.swing.view;

import com.link_intersystems.util.context.Context;

public abstract class AbstractViewSite implements ViewSite {

    public abstract ViewContent getViewContent();

    public abstract Context getViewContext();

}
