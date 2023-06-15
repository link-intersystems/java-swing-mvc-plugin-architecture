package com.link_intersystems.swing.action;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public abstract class AbstractTaskAction2<T, V> extends AbstractTaskAction<T, V> {

    private Optional<ActionCallback<T, V>> callback = Optional.empty();

    public void setCallback(ActionCallback<T, V> callback) {
        this.callback = Optional.ofNullable(callback);
    }

    @Override
    protected void prepareExecution() throws Exception {
        callback.ifPresent(ActionCallback::aboutToRun);
    }

    @Override
    protected void done(T result) {

        callback.ifPresent(c -> c.done(result));
    }

    @Override
    protected void publishIntermediateResults(List<V> chunks) {
        callback.ifPresent(c -> c.intermediateResults(chunks));
    }

    @Override
    protected void failed(ExecutionException e) {
        callback.ifPresent(c -> c.failed(e));
    }

}
