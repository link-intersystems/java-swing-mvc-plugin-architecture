package com.link_intersystems.fileeditor.action;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ActionCallback<T, V> {

    default void aboutToRun(){
    }

    void done(T result);

    default void intermediateResults(List<V> chunks) {
    }


    default void failed(ExecutionException e) {
    }
}
