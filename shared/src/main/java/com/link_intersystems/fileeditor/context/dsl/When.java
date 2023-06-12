package com.link_intersystems.fileeditor.context.dsl;

public interface When<T> {
    Action<T> removed();

    Action<T> added();

    Action<T> changed();

    void dispose();
}
