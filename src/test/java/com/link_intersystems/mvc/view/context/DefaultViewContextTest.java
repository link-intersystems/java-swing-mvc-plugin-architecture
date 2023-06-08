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
    void registerUnamedModelTwice() {
        defaultViewContext.put(ListModel.class, new DefaultListModel());
        assertThrows(IllegalArgumentException.class, () -> defaultViewContext.put(ListModel.class, new DefaultListModel()));
    }

    @Test
    void registerNamedModelTwice() {
        defaultViewContext.put(ListModel.class, "model1", new DefaultListModel());
        assertThrows(IllegalArgumentException.class, () -> defaultViewContext.put(ListModel.class, "model1", new DefaultListModel()));
    }

    @Test
    void registerModelsWithDifferentQualifiers() {
        defaultViewContext.put(ListModel.class, new DefaultListModel());
        defaultViewContext.put(ListModel.class, "model1", new DefaultListModel());
    }

    @Test
    void registerModel() {
        DefaultListModel defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, defaultListModel);

        ListModel listModel = defaultViewContext.get(ListModel.class);
        assertSame(defaultListModel, listModel);
    }

    @Test
    void registerNamedModel() {
        defaultViewContext.put(ListModel.class, new DefaultListModel());
        DefaultListModel defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model1", defaultListModel);

        ListModel listModel = defaultViewContext.get(ListModel.class, "model1");
        assertSame(defaultListModel, listModel);
    }

    @Test
    void unregisterModel() {
        DefaultListModel defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, defaultListModel);

        defaultViewContext.remove(ListModel.class);

        assertNull(defaultViewContext.get(ListModel.class));
    }

    @Test
    void unregisterNamedModel() {
        DefaultListModel defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model1", defaultListModel);

        defaultViewContext.remove(ListModel.class, "model1");

        assertNull(defaultViewContext.get(ListModel.class, "model1"));
    }


    @Test
    void addListenerAfterModelAdded() {
        DefaultListModel<String> defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, defaultListModel);

        defaultViewContext.addViewContextListener(ListModel.class, listenerMock);

        ListModel<String> lastAddedModel = listenerMock.getLast();
        assertSame(defaultListModel, lastAddedModel);
    }

    @Test
    void addListenerBeforeModelAdded() {
        DefaultListModel<String> defaultListModel = new DefaultListModel();
        defaultViewContext.addViewContextListener(ListModel.class, listenerMock);

        defaultViewContext.put(ListModel.class, defaultListModel);

        ListModel<String> lastAddedModel = listenerMock.getLast();
        assertSame(defaultListModel, lastAddedModel);
    }

    @Test
    void addListenerAfterModelRemoved() {
        DefaultListModel<String> defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, defaultListModel);
        defaultViewContext.remove(ListModel.class);

        defaultViewContext.addViewContextListener(ListModel.class, listenerMock);

        ListModel<String> lastAddedModel = listenerMock.getLast();
        assertNull(lastAddedModel);
    }

    @Test
    void addListenerBeforeModelRemoved() {
        DefaultListModel<String> defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, defaultListModel);
        defaultViewContext.addViewContextListener(ListModel.class, listenerMock);

        defaultViewContext.remove(ListModel.class);

        ListModel<String> lastAddedModel = listenerMock.getLast();
        assertNull(lastAddedModel);
    }

    @Test
    void removeListenerAfterModelAdded() {
        DefaultListModel<String> defaultListModel = new DefaultListModel();
        defaultViewContext.addViewContextListener(ListModel.class, listenerMock);
        defaultViewContext.put(ListModel.class, defaultListModel);

        defaultViewContext.removeViewContextListener(ListModel.class, listenerMock);

        defaultViewContext.remove(ListModel.class);
        defaultViewContext.put(ListModel.class, new DefaultListModel());

        assertEquals(Collections.singletonList(defaultListModel), listenerMock.getModels());
    }

    @Test
    void removeListenerBeforeModelAdded() {
        defaultViewContext.addViewContextListener(ListModel.class, listenerMock);

        defaultViewContext.removeViewContextListener(ListModel.class, listenerMock);

        DefaultListModel<String> defaultListModel = new DefaultListModel();
        defaultViewContext.put(ListModel.class, defaultListModel);
        assertEquals(Collections.emptyList(), listenerMock.getModels());
    }

    @Test
    void unspecificModelListenerAddedBeforeModels() {
        defaultViewContext.addViewContextListener(ListModel.class, listenerMock);
        DefaultListModel<String> defaultListModel1 = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model1", defaultListModel1);
        DefaultListModel<String> defaultListModel2 = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model2", defaultListModel2);

        List<ListModel<String>> models = listenerMock.getModels();
        assertEquals(Collections.emptyList(), models);
    }

    @Test
    void specificModelListenerAddedBeforeModels() {
        defaultViewContext.addViewContextListener(ListModel.class, "model2", listenerMock);
        DefaultListModel<String> defaultListModel1 = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model1", defaultListModel1);
        DefaultListModel<String> defaultListModel2 = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model2", defaultListModel2);

        List<ListModel<String>> models = listenerMock.getModels();
        assertEquals(Arrays.asList( defaultListModel2), models);
    }

    @Test
    void unspecificModelListenerAddedAfterModels() {
        DefaultListModel<String> defaultListModel1 = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model1", defaultListModel1);
        DefaultListModel<String> defaultListModel2 = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model2", defaultListModel2);

        defaultViewContext.addViewContextListener(ListModel.class, listenerMock);

        List<ListModel<String>> models = listenerMock.getModels();
        assertEquals(Collections.emptyList(), models);
    }

    @Test
    void specificModelListenerAddedAfterModels() {
        DefaultListModel<String> defaultListModel1 = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model1", defaultListModel1);
        DefaultListModel<String> defaultListModel2 = new DefaultListModel();
        defaultViewContext.put(ListModel.class, "model2", defaultListModel2);

        defaultViewContext.addViewContextListener(ListModel.class, "model2", listenerMock);

        List<ListModel<String>> models = listenerMock.getModels();
        assertEquals(Arrays.asList( defaultListModel2), models);
    }


}