package com.link_intersystems.mvc;

import com.link_intersystems.mvc.view.context.MutableViewContext;

import java.awt.*;
import java.util.Optional;

public abstract class View {

    private Optional<View> parentView;

    public View() {
        this(null);
    }

    public View(View parentView) {
        this.parentView = Optional.ofNullable(parentView);
    }

    public MutableViewContext getViewContext() {
        return parentView.map(v -> v.getViewContext()).orElse(null);
    }

    public abstract Component createComponent();
}
