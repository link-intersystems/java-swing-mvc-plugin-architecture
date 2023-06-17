package com.link_intersystems.util.context.dsl;

public interface When<T> {
    Action<T> removed();

    Action<T> added();

    Action<T> changed();

    void dispose();
}
