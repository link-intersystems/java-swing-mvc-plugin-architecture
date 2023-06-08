package com.link_intersystems.mvc.view.context.dsl;

public interface State<T> {
    void orDefault(T defaultModel);
}
