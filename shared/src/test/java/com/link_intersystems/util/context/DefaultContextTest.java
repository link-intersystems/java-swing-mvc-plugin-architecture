package com.link_intersystems.util.context;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefaultContextTest {

    private ContextListenerMock<String> contextListener;
    private DefaultContext context;

    @BeforeEach
    void setUp() {
        context = new DefaultContext();
        contextListener = new ContextListenerMock<>();
    }

    @Test
    void putObjectTwice() {
        context.put(String.class, "A");
        assertThrows(DuplicateObjectException.class, () -> context.put(String.class, new String("A")));
    }

    @Test
    void putSameObjectTwice() {
        context.put(String.class, "A");
        context.put(String.class, "A");
    }

    @Test
    void putNamedObjectTwice() {
        context.put(String.class, "model1", "A");
        assertThrows(DuplicateObjectException.class, () -> context.put(String.class, "model1", new String("A")));
    }

    @Test
    void putNamedObject() {
        context.put(String.class, "A");
        context.put(String.class, "model1", "A");
    }

    @Test
    void put() {
        context.put(String.class, "A");

        assertSame("A", context.get(String.class));
    }

    @Test
    void getNamedObject() {
        context.put(String.class, "A");
        context.put(String.class, "model1", "A");

        assertSame("A", context.get(String.class, "model1"));
    }

    @Test
    void remove() {
        context.put(String.class, "A");

        assertTrue(context.remove(String.class));

        assertThrows(NoSuchObjectException.class, () -> context.get(String.class));
    }

    @Test
    void removeUnknownInstance() {
        context.put(String.class, "A");

        assertFalse(context.remove(String.class, "someName"));
    }

    @Test
    void removeNamedObject() {
        context.put(String.class, "model1", "A");

        context.remove(String.class, "model1");

        assertThrows(NoSuchObjectException.class, () -> context.get(String.class, "model1"));
    }


    @Test
    void addListenerAfterObjectAdded() {
        context.put(String.class, "A");

        context.addViewContextListener(String.class, contextListener);

        assertSame("A", contextListener.getLastest());
    }

    @Test
    void addListenerBeforeObjectAdded() {
        context.addViewContextListener(String.class, contextListener);

        context.put(String.class, "A");

        assertSame("A", contextListener.getLastest());
    }

    @Test
    void addListenerAfterObjectRemoved() {
        context.put(String.class, "A");
        context.remove(String.class);

        context.addViewContextListener(String.class, contextListener);

        assertNull(contextListener.getLastest());
    }

    @Test
    void addListenerBeforeObjectRemoved() {
        context.put(String.class, "A");
        context.addViewContextListener(String.class, contextListener);

        context.remove(String.class);

        assertNull(contextListener.getLastest());
    }

    @Test
    void removeListenerAfterObjectAdded() {
        context.addViewContextListener(String.class, contextListener);
        context.put(String.class, "A");

        context.removeViewContextListener(String.class, contextListener);

        context.remove(String.class);
        context.put(String.class, "A");

        assertEquals(Collections.singletonList("A"), contextListener.getObjects());
    }

    @Test
    void removeListenerBeforeObjectAdded() {
        context.addViewContextListener(String.class, contextListener);

        context.removeViewContextListener(String.class, contextListener);

        context.put(String.class, "A");
        assertEquals(Collections.emptyList(), contextListener.getObjects());
    }

    @Test
    void unspecificObjectListenerAddedBeforeObjects() {
        context.addViewContextListener(String.class, contextListener);
        context.put(String.class, "model1", "A");
        context.put(String.class, "model2", "B");

        assertEquals(Collections.emptyList(), contextListener.getObjects());
    }

    @Test
    void specificObjectListenerAddedBeforeObjects() {
        context.addViewContextListener(String.class, "model2", contextListener);
        context.put(String.class, "model1", "A");
        context.put(String.class, "model2", "B");

        assertEquals(List.of("B"), contextListener.getObjects());
    }

    @Test
    void unspecificObjectListenerAddedAfterObjects() {
        context.put(String.class, "model1", "A");
        context.put(String.class, "model2", "B");

        context.addViewContextListener(String.class, contextListener);

        assertEquals(Collections.emptyList(), contextListener.getObjects());
    }

    @Test
    void specificObjectListenerAddedAfterObjects() {
        context.put(String.class, "model1", "A");
        context.put(String.class, "model2", "B");

        context.addViewContextListener(String.class, "model2", contextListener);

        assertEquals(List.of("B"), contextListener.getObjects());
    }

    @Test
    void contains() {
        context.put(String.class, "A");
        context.put(String.class, "model1", "B");
        context.put(String.class, "model2", "C");


        assertTrue(context.contains(String.class));
        assertTrue(context.contains(String.class, "model1"));
        assertTrue(context.contains(String.class, "model2"));
    }

    @Test
    void stream() {
        context.put(String.class, "model1", "A");
        context.put(String.class, "model2", "B");


        List<?> instances = context.stream().map(QualifiedObject::getObject).toList();
        assertEquals(2, instances.size());
        assertTrue(instances.contains("A"));
        assertTrue(instances.contains("B"));

        context.stream().map(QualifiedObject::getQualifier).toList().forEach(context::remove);
    }
}