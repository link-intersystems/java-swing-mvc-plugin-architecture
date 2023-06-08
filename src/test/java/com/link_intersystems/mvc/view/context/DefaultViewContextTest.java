package com.link_intersystems.mvc.view.context;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefaultViewContextTest {

    private ViewContextListenerMock<ListModel<String>> listenerMock;
    private DefaultViewContext defaultViewContext;

    @BeforeEach
    void setUp() {
        defaultViewContext = new DefaultViewContext();
        listenerMock = new ViewContextListenerMock<>();

    }

    @Test
    void registerUnnamedObjectTwice() {
        defaultViewContext.put(ListModel.class, new DefaultListModel());
        assertThrows(IllegalArgumentException.class, () -> defaultViewContext.put(ListModel.class, new DefaultListModel()));
    }

    @Test
    void registerNamedObjectTwice() {
        defaultViewContext.put(ListModel.class, "model1", new DefaultListModel());
        assertThrows(IllegalArgumentException.class, () -> defaultViewContext.put(ListModel.class, "model1", new DefaultListModel()));
    }

    @Test
    void registerObjectsWithDifferentQualifiers() {
        defaultViewContext.put(ListModel.class, new DefaultListModel());
        defaultViewContext.put(ListModel.class, "model1", new DefaultListModel());
    }

    @Test
    void putObject() {
        DefaultListModel defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, defaultListModel);

        ListModel listModel = defaultViewContext.get(ListModel.class);
        assertSame(defaultListModel, listModel);
    }

    @Test
    void putNamedObject() {
        defaultViewContext.put(ListModel.class, new DefaultListModel());
        DefaultListModel defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model1", defaultListModel);

        ListModel listModel = defaultViewContext.get(ListModel.class, "model1");
        assertSame(defaultListModel, listModel);
    }

    @Test
    void removeObject() {
        DefaultListModel defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, defaultListModel);

        defaultViewContext.remove(ListModel.class);

        assertNull(defaultViewContext.get(ListModel.class));
    }

    @Test
    void removeNamedObject() {
        DefaultListModel defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model1", defaultListModel);

        defaultViewContext.remove(ListModel.class, "model1");

        assertNull(defaultViewContext.get(ListModel.class, "model1"));
    }


    @Test
    void addListenerAfterObjectPut() {
        DefaultListModel<String> defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, defaultListModel);

        defaultViewContext.addViewContextListener(ListModel.class, listenerMock);

        ListModel<String> lastAddedModel = listenerMock.getLast();
        assertSame(defaultListModel, lastAddedModel);
    }

    @Test
    void addListenerBeforeObjectPut() {
        DefaultListModel<String> defaultListModel = new DefaultListModel();
        defaultViewContext.addViewContextListener(ListModel.class, listenerMock);

        defaultViewContext.put(ListModel.class, defaultListModel);

        ListModel<String> lastAddedModel = listenerMock.getLast();
        assertSame(defaultListModel, lastAddedModel);
    }

    @Test
    void addListenerAfterObjectRemoved() {
        DefaultListModel<String> defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, defaultListModel);
        defaultViewContext.remove(ListModel.class);

        defaultViewContext.addViewContextListener(ListModel.class, listenerMock);

        ListModel<String> lastAddedModel = listenerMock.getLast();
        assertNull(lastAddedModel);
    }

    @Test
    void addListenerBeforeObjectRemoved() {
        DefaultListModel<String> defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, defaultListModel);
        defaultViewContext.addViewContextListener(ListModel.class, listenerMock);

        defaultViewContext.remove(ListModel.class);

        ListModel<String> lastAddedModel = listenerMock.getLast();
        assertNull(lastAddedModel);
    }

    @Test
    void removeListenerAfterPut() {
        DefaultListModel<String> defaultListModel = new DefaultListModel();
        defaultViewContext.addViewContextListener(ListModel.class, listenerMock);
        defaultViewContext.put(ListModel.class, defaultListModel);

        defaultViewContext.removeViewContextListener(ListModel.class, listenerMock);

        defaultViewContext.remove(ListModel.class);
        defaultViewContext.put(ListModel.class, new DefaultListModel());

        assertEquals(Collections.singletonList(defaultListModel), listenerMock.getModels());
    }

    @Test
    void removeListenerBeforePut() {
        defaultViewContext.addViewContextListener(ListModel.class, listenerMock);

        defaultViewContext.removeViewContextListener(ListModel.class, listenerMock);

        DefaultListModel<String> defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, defaultListModel);
        assertEquals(Collections.emptyList(), listenerMock.getModels());
    }

    @Test
    void unspecificListenerAddedBeforePut() {
        defaultViewContext.addViewContextListener(ListModel.class, listenerMock);
        DefaultListModel<String> defaultListModel1 = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model1", defaultListModel1);
        DefaultListModel<String> defaultListModel2 = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model2", defaultListModel2);

        List<ListModel<String>> models = listenerMock.getModels();
        assertEquals(Collections.emptyList(), models);
    }

    @Test
    void specificListenerAddedBeforePut() {
        defaultViewContext.addViewContextListener(ListModel.class, "model2", listenerMock);
        DefaultListModel<String> defaultListModel1 = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model1", defaultListModel1);
        DefaultListModel<String> defaultListModel2 = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model2", defaultListModel2);

        List<ListModel<String>> models = listenerMock.getModels();
        assertEquals(Arrays.asList( defaultListModel2), models);
    }

    @Test
    void unspecificListenerAddedAfterPut() {
        DefaultListModel<String> defaultListModel1 = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model1", defaultListModel1);
        DefaultListModel<String> defaultListModel2 = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model2", defaultListModel2);

        defaultViewContext.addViewContextListener(ListModel.class, listenerMock);

        List<ListModel<String>> models = listenerMock.getModels();
        assertEquals(Collections.emptyList(), models);
    }

    @Test
    void specificListenerAddedAfterPut() {
        DefaultListModel<String> defaultListModel1 = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model1", defaultListModel1);
        DefaultListModel<String> defaultListModel2 = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model2", defaultListModel2);

        defaultViewContext.addViewContextListener(ListModel.class, "model2", listenerMock);

        List<ListModel<String>> models = listenerMock.getModels();
        assertEquals(Arrays.asList( defaultListModel2), models);
    }


}