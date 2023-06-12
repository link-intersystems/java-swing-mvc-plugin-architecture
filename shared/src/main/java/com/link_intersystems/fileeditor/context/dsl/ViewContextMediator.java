package com.link_intersystems.fileeditor.context.dsl;

import com.link_intersystems.fileeditor.context.ObjectQualifier;
import com.link_intersystems.fileeditor.context.ViewContext;
import com.link_intersystems.fileeditor.context.ViewContextListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Objects.*;

public class ViewContextMediator {

    private static class ViewContextMediatorListenerAdapter<T> implements ViewContextListener<T> {

        private Consumer<T> onModelAdded;
        private Consumer<T> onModelRemoved;
        private T setModel;
        private T defaultModel;

        public ViewContextMediatorListenerAdapter(Consumer<T> modelSetter) {
            this(modelSetter, modelSetter);
        }

        public ViewContextMediatorListenerAdapter(Consumer<T> onModelAdded, Consumer<T> onModelRemoved) {
            this.onModelAdded = onModelAdded;
            this.onModelRemoved = onModelRemoved;
        }

        @Override
        public void objectAdded(ViewContext viewContext, T object) {
            if (onModelAdded != null) {
                onModelAdded.accept(object);
            }

            this.setModel = object;
        }

        @Override
        public void objectRemoved(ViewContext viewContext, T object) {
            if (onModelRemoved != null) {
                onModelRemoved.accept(object);
            }

            this.setModel = null;
        }

    }

    private static class RumnableListenerAdapter<T> implements ViewContextListener<T> {

        private Runnable modelAddedRunnable;
        private Runnable modelRemovedRunnable;

        public RumnableListenerAdapter(Runnable modelAddedRunnable, Runnable modelRemovedRunnable) {
            this.modelAddedRunnable = modelAddedRunnable;
            this.modelRemovedRunnable = modelRemovedRunnable;
        }

        @Override
        public void objectAdded(ViewContext viewContext, T object) {
            if (modelAddedRunnable != null) {
                modelAddedRunnable.run();
            }
        }

        @Override
        public void objectRemoved(ViewContext viewContext, T object) {
            if (modelRemovedRunnable != null) {
                modelRemovedRunnable.run();
            }
        }
    }

    private static abstract class AbstractWhen<T> implements When<T> {

        private final List<Action<?>> actions = new ArrayList<>();

        protected <T> Action<T> add(Action<T> action) {
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

        private ViewContext viewContext;
        private ObjectQualifier<? super T> objectQualifier;

        protected final List<ViewContextListener<T>> listeners = new ArrayList<>();

        public AbstractAction(ViewContext viewContext, ObjectQualifier<? super T> objectQualifier) {
            this.viewContext = viewContext;
            this.objectQualifier = objectQualifier;
        }


        @Override
        public void dispose() {
            listeners.forEach(l -> viewContext.removeViewContextListener(objectQualifier, l));
            listeners.clear();
        }
    }

    private ViewContext viewContext;

    public ViewContextMediator(ViewContext viewContext) {
        this.viewContext = viewContext;
    }

    public <T> When<T> when(Class<? super T> modelType) {
        return when(modelType, null);
    }

    public <T> When<T> when(Class<? super T> modelType, String name) {
        ObjectQualifier<? super T> objectQualifier = new ObjectQualifier<>(modelType, name);
        return when(objectQualifier);
    }

    public <T> When<T> when(ObjectQualifier<? super T> objectQualifier) {
        return new AbstractWhen<T>() {
            @Override
            public Action<T> added() {
                return add(new AbstractAction<T>(viewContext, objectQualifier) {

                    @Override
                    public void then(Consumer<T> consumer) {
                        ViewContextMediatorListenerAdapter<T> listenerAdapter = new ViewContextMediatorListenerAdapter<>(consumer, null);
                        listeners.add(listenerAdapter);
                        viewContext.addViewContextListener(objectQualifier, listenerAdapter);
                    }

                    @Override
                    public void then(Runnable runnable) {
                        RumnableListenerAdapter<T> rumnableListenerAdapter = new RumnableListenerAdapter<>(requireNonNull(runnable), null);
                        listeners.add(rumnableListenerAdapter);
                        viewContext.addViewContextListener(objectQualifier, rumnableListenerAdapter);
                    }
                });
            }

            @Override
            public Action<T> removed() {
                return add(new AbstractAction<T>(viewContext, objectQualifier) {

                    @Override
                    public void then(Consumer<T> consumer) {
                        ViewContextMediatorListenerAdapter<T> listenerAdapter = new ViewContextMediatorListenerAdapter<>(null, or -> consumer.accept(null));
                        listeners.add(listenerAdapter);
                        viewContext.addViewContextListener(objectQualifier, listenerAdapter);
                    }

                    @Override
                    public void then(Runnable runnable) {
                        RumnableListenerAdapter<T> rumnableListenerAdapter = new RumnableListenerAdapter<>(null, requireNonNull(runnable));
                        listeners.add(rumnableListenerAdapter);
                        viewContext.addViewContextListener(objectQualifier, rumnableListenerAdapter);
                    }
                });
            }

            @Override
            public Action<T> changed() {
                return add(new AbstractAction<T>(viewContext, objectQualifier) {
                    @Override
                    public void then(Consumer<T> consumer) {
                        ViewContextMediatorListenerAdapter<T> listenerAdapter = new ViewContextMediatorListenerAdapter<>(consumer, or -> consumer.accept(null));
                        listeners.add(listenerAdapter);
                        viewContext.addViewContextListener(objectQualifier, listenerAdapter);
                    }

                    @Override
                    public void then(Runnable runnable) {
                        RumnableListenerAdapter<T> rumnableListenerAdapter = new RumnableListenerAdapter<>(requireNonNull(runnable), requireNonNull(runnable));
                        listeners.add(rumnableListenerAdapter);
                        viewContext.addViewContextListener(objectQualifier, rumnableListenerAdapter);
                    }
                });
            }
        };
    }
}
