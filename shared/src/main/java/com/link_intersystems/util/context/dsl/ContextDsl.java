package com.link_intersystems.util.context.dsl;

import com.link_intersystems.util.context.Context;
import com.link_intersystems.util.context.ContextListener;
import com.link_intersystems.util.context.ObjectQualifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Objects.*;

public class ContextDsl {

    private static class ContextMediatorListenerAdapter<T> implements ContextListener<T> {

        private Consumer<T> onInstanceAdded;
        private Consumer<T> onInstanceRemoved;
        private T setModel;
        private T defaultModel;

        public ContextMediatorListenerAdapter(Consumer<T> modelSetter) {
            this(modelSetter, modelSetter);
        }

        public ContextMediatorListenerAdapter(Consumer<T> onInstanceAdded, Consumer<T> onInstanceRemoved) {
            this.onInstanceAdded = onInstanceAdded;
            this.onInstanceRemoved = onInstanceRemoved;
        }

        @Override
        public void added(Context context, T object) {
            if (onInstanceAdded != null) {
                onInstanceAdded.accept(object);
            }

            this.setModel = object;
        }

        @Override
        public void removed(Context context, T object) {
            if (onInstanceRemoved != null) {
                onInstanceRemoved.accept(object);
            }

            this.setModel = null;
        }

    }

    private static class RunnableListenerAdapter<T> implements ContextListener<T> {

        private Runnable addedRunnable;
        private Runnable removedRunnable;

        public RunnableListenerAdapter(Runnable addedRunnable, Runnable removedRunnable) {
            this.addedRunnable = addedRunnable;
            this.removedRunnable = removedRunnable;
        }

        @Override
        public void added(Context context, T object) {
            if (addedRunnable != null) {
                addedRunnable.run();
            }
        }

        @Override
        public void removed(Context context, T object) {
            if (removedRunnable != null) {
                removedRunnable.run();
            }
        }
    }

    private static abstract class AbstractWhen<T> implements When<T> {

        private final List<Action<?>> actions = new ArrayList<>();

        protected Action<T> add(Action<T> action) {
            actions.add(action);
            return action;
        }

        @Override
        public void dispose() {
            actions.forEach(Action::dispose);
            actions.clear();
        }
    }

    private static abstract class AbstractAction<T> implements Action<T> {

        private Context context;
        private ObjectQualifier<? super T> objectQualifier;

        protected final List<ContextListener<T>> listeners = new ArrayList<>();

        public AbstractAction(Context context, ObjectQualifier<? super T> objectQualifier) {
            this.context = context;
            this.objectQualifier = objectQualifier;
        }


        @Override
        public void dispose() {
            listeners.forEach(l -> context.removeViewContextListener(objectQualifier, l));
            listeners.clear();
        }
    }

    private List<Runnable> unregisterListenersRunnables = new ArrayList<>();

    private Context context;

    public ContextDsl(Context context) {
        this.context = context;
    }

    public <T> When<T> when(Class<? super T> modelType) {
        return when(modelType, null);
    }

    public <T> When<T> when(Class<? super T> modelType, String name) {
        ObjectQualifier<? super T> objectQualifier = new ObjectQualifier<>(modelType, name);
        return when(objectQualifier);
    }

    public <T> When<T> when(ObjectQualifier<? super T> objectQualifier) {
        return new AbstractWhen<>() {
            @Override
            public Action<T> added() {
                return add(new AbstractAction<T>(context, objectQualifier) {

                    @Override
                    public void then(Consumer<T> consumer) {
                        ContextMediatorListenerAdapter<T> listenerAdapter = new ContextMediatorListenerAdapter<>(consumer, null);
                        listeners.add(listenerAdapter);
                        context.addViewContextListener(objectQualifier, listenerAdapter);
                        unregisterListenersRunnables.add(() -> context.removeViewContextListener(objectQualifier, listenerAdapter));
                    }

                    @Override
                    public void then(Runnable runnable) {
                        RunnableListenerAdapter<T> runnableListenerAdapter = new RunnableListenerAdapter<>(requireNonNull(runnable), null);
                        listeners.add(runnableListenerAdapter);
                        context.addViewContextListener(objectQualifier, runnableListenerAdapter);
                        unregisterListenersRunnables.add(() -> context.removeViewContextListener(objectQualifier, runnableListenerAdapter));
                    }
                });
            }

            @Override
            public Action<T> removed() {
                return add(new AbstractAction<T>(context, objectQualifier) {

                    @Override
                    public void then(Consumer<T> consumer) {
                        ContextMediatorListenerAdapter<T> listenerAdapter = new ContextMediatorListenerAdapter<>(null, or -> consumer.accept(null));
                        listeners.add(listenerAdapter);
                        context.addViewContextListener(objectQualifier, listenerAdapter);
                        unregisterListenersRunnables.add(() -> context.removeViewContextListener(objectQualifier, listenerAdapter));
                    }

                    @Override
                    public void then(Runnable runnable) {
                        RunnableListenerAdapter<T> runnableListenerAdapter = new RunnableListenerAdapter<>(null, requireNonNull(runnable));
                        listeners.add(runnableListenerAdapter);
                        context.addViewContextListener(objectQualifier, runnableListenerAdapter);
                        unregisterListenersRunnables.add(() -> context.removeViewContextListener(objectQualifier, runnableListenerAdapter));
                    }
                });
            }

            @Override
            public Action<T> changed() {
                return add(new AbstractAction<T>(context, objectQualifier) {
                    @Override
                    public void then(Consumer<T> consumer) {
                        ContextMediatorListenerAdapter<T> listenerAdapter = new ContextMediatorListenerAdapter<>(consumer, or -> consumer.accept(null));
                        listeners.add(listenerAdapter);
                        context.addViewContextListener(objectQualifier, listenerAdapter);
                        unregisterListenersRunnables.add(() -> context.removeViewContextListener(objectQualifier, listenerAdapter));
                    }

                    @Override
                    public void then(Runnable runnable) {
                        RunnableListenerAdapter<T> runnableListenerAdapter = new RunnableListenerAdapter<>(requireNonNull(runnable), requireNonNull(runnable));
                        listeners.add(runnableListenerAdapter);
                        context.addViewContextListener(objectQualifier, runnableListenerAdapter);
                        unregisterListenersRunnables.add(() -> context.removeViewContextListener(objectQualifier, runnableListenerAdapter));
                    }
                });
            }
        };
    }

    public void dispose() {
        unregisterListenersRunnables.forEach(Runnable::run);
        unregisterListenersRunnables.clear();
    }
}
