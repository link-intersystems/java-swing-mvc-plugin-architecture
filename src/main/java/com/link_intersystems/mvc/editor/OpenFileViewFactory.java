package com.link_intersystems.mvc.editor;

import com.link_intersystems.mvc.View;
import com.link_intersystems.mvc.ViewFactory;

public class OpenFileViewFactory implements ViewFactory {
    @Override
    public String getName() {
        return "openFile";
    }

    @Override
    public View createView(View parentView) {
        OpenFileView openFileView = new OpenFileView(parentView);
        return openFileView;
    }
}
