package com.link_intersystems.swing.view;

import com.link_intersystems.util.context.Context;
import com.link_intersystems.util.context.DefaultContext;
import com.link_intersystems.util.context.QualifiedObject;
import com.link_intersystems.util.context.ObjectQualifier;

import java.util.stream.Stream;

import static java.util.Objects.*;

public class DefaultViewSite extends AbstractViewSite {
    private ViewContent viewContent;
    private Context viewContext;

    public DefaultViewSite(ViewContent viewContent) {
        this(viewContent, new DefaultContext());
    }

    public DefaultViewSite(Context viewContext) {
        this(ViewContent.nullInstance(), viewContext);
    }

    public DefaultViewSite(ViewContent viewContent, Context viewContext) {
        this.viewContent = requireNonNull(viewContent);
        this.viewContext = requireNonNull(viewContext);
    }

    @Override
    public ViewContent getViewContent() {
        return viewContent;
    }

    @Override
    public Context getViewContext() {
        return viewContext;
    }

    @Override
    public boolean contains(ObjectQualifier<?> objectQualifier) {
        return getViewContext().contains(objectQualifier);
    }

    @Override
    public Stream<QualifiedObject<?>> stream() {
        return getViewContext().stream();
    }
}
