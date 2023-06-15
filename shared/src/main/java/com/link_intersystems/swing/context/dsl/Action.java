package com.link_intersystems.swing.context.dsl;

import java.util.function.Consumer;

public interface Action<T> {
    void then(Consumer<T> consumer);

    void then(Runnable runnable);

    void dispose();
}
