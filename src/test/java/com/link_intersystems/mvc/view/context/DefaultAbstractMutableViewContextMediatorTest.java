package com.link_intersystems.mvc.view.context;

import com.link_intersystems.mvc.view.context.dsl.ViewContextMediator;
import com.link_intersystems.mvc.view.context.dsl.When;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class DefaultAbstractMutableViewContextMediatorTest {

    private static class SomeController {
        private ListModel model;
        private boolean executed;

        public void setModel(ListModel model) {
            this.model = model;
        }

        public void execute() {
            executed = !executed;
        }
    }


    private DefaultAbstractMutableViewContext defaultViewContext;
    private ViewContextMediator viewContextMediator;

    @BeforeEach
    void setUp() {
        defaultViewContext = new DefaultAbstractMutableViewContext();
        viewContextMediator = new ViewContextMediator(defaultViewContext);
    }

    @Test
    void whenChangedRun() {
        SomeController someController = new SomeController();
        DefaultListModel defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, defaultListModel);

        viewContextMediator.when(ListModel.class).changed().then(someController::execute);

        assertTrue(someController.executed);
        defaultViewContext.remove(ListModel.class);
        assertFalse(someController.executed);
    }

    @Test
    void whenAddedRun() {
        SomeController someController = new SomeController();
        DefaultListModel defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, defaultListModel);

        viewContextMediator.when(ListModel.class).added().then(someController::execute);

        assertTrue(someController.executed);
        defaultViewContext.remove(ListModel.class);
        assertTrue(someController.executed);
    }

    @Test
    void whenRemovedRun() {
        SomeController someController = new SomeController();
        DefaultListModel defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, defaultListModel);

        viewContextMediator.when(ListModel.class).removed().then(someController::execute);
        assertFalse(someController.executed);
        defaultViewContext.remove(ListModel.class);
        assertTrue(someController.executed);
    }

    @Test
    void whenAddedCallSetter() {
        SomeController someController = new SomeController();
        DefaultListModel defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, defaultListModel);

        viewContextMediator.when(ListModel.class).added().then(someController::setModel);

        assertSame(defaultListModel, someController.model);
        defaultViewContext.remove(ListModel.class);
        assertEquals(defaultListModel, someController.model);
    }

    @Test
    void whenRemovedCallSetter() {
        SomeController someController = new SomeController();
        DefaultListModel defaultListModel = new DefaultListModel();
        someController.model = defaultListModel;
        defaultViewContext.put(ListModel.class, defaultListModel);

        viewContextMediator.when(ListModel.class).removed().then(someController::setModel);

        assertSame(defaultListModel, someController.model);
        defaultViewContext.remove(ListModel.class);
        assertNull(someController.model);
    }

    @Test
    void whenListenerAddedAfterPut() {
        SomeController someController = new SomeController();
        DefaultListModel defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, defaultListModel);

        viewContextMediator.when(ListModel.class).changed().then(someController::setModel);

        assertSame(defaultListModel, someController.model);
        defaultViewContext.remove(ListModel.class);
        assertNull(someController.model);
    }

    @Test
    void whenListenerAddedBeforePut() {
        SomeController someController = new SomeController();
        viewContextMediator.when(ListModel.class).changed().then(someController::setModel);

        DefaultListModel defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, defaultListModel);

        assertSame(defaultListModel, someController.model);
        defaultViewContext.remove(ListModel.class);

        assertNull(someController.model);
    }

    @Test
    void disposeAll() {
        SomeController someController = new SomeController();
        When<ListModel> when = viewContextMediator.when(ListModel.class);

        when.dispose();
        DefaultListModel defaultListModel2 = new DefaultListModel();
        defaultViewContext.put(ListModel.class, defaultListModel2);

        assertNull(someController.model);
    }
}