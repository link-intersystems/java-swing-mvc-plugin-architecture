package com.link_intersystems.mvc;

import com.link_intersystems.swing.action.AbstractTaskAction;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public abstract class AbstractTaskAction2<T, V> extends AbstractTaskAction<T, V> {

    private ActionCallback<T, V> callback = new ActionCallback<T, V>() {
        @Override
        public void done(T result) {
        }
    };

    public void setCallback(ActionCallback<T, V> callback) {
        this.callback = Objects.requireNonNull(callback);
    }

    @Override
    protected void prepareExecution() throws Exception {
        callback.aboutToRun();
    }

    @Override
    protected void done(T result) {
        callback.done(result);
    }

    @Override
    protected void publishIntermediateResults(List<V> chunks) {
        callback.intermediateResults(chunks);
    }

    @Override
    protected void failed(ExecutionException e) {
        callback.failed(e);
    }

}
