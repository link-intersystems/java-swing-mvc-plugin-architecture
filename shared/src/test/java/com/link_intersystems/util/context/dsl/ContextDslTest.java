package com.link_intersystems.util.context.dsl;

import com.link_intersystems.util.context.Context;
import com.link_intersystems.util.context.DefaultContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContextDslTest {

    private ClassWithProperty classWithProperty;

    private static class ClassWithProperty {
        private String text;
        private boolean executed;

        public void setText(String text) {
            this.text = text;
        }

        public void execute() {
            executed = !executed;
        }
    }


    private Context context;
    private ContextDsl contextDsl;

    @BeforeEach
    void setUp() {
        context = new DefaultContext();
        contextDsl = new ContextDsl(context);

        classWithProperty = new ClassWithProperty();
    }

    @Test
    void whenModelChangedRun() {
        context.put(String.class, "A");

        contextDsl.when(String.class).changed().then(classWithProperty::execute);

        assertTrue(classWithProperty.executed);
        context.remove(String.class);
        assertFalse(classWithProperty.executed);
    }

    @Test
    void whenModelAddedRun() {
        context.put(String.class, "A");

        contextDsl.when(String.class).added().then(classWithProperty::execute);

        assertTrue(classWithProperty.executed);
        context.remove(String.class);
        assertTrue(classWithProperty.executed);
    }

    @Test
    void whenModelRemovedRun() {
        context.put(String.class, "A");

        contextDsl.when(String.class).removed().then(classWithProperty::execute);
        assertFalse(classWithProperty.executed);
        context.remove(String.class);
        assertTrue(classWithProperty.executed);
    }

    @Test
    void whenModelAddedSetModel() {
        context.put(String.class, "A");

        contextDsl.when(String.class).added().then(classWithProperty::setText);

        assertSame("A", classWithProperty.text);
        context.remove(String.class);
        assertEquals("A", classWithProperty.text);
    }

    @Test
    void whenModelRemovedSetModel() {
        classWithProperty.text = "A";
        context.put(String.class, "A");

        contextDsl.when(String.class).removed().then(classWithProperty::setText);

        assertSame("A", classWithProperty.text);
        context.remove(String.class);
        assertNull(classWithProperty.text);
    }

    @Test
    void whenAddedAfterModelAvailable() {
        context.put(String.class, "A");

        contextDsl.when(String.class).changed().then(classWithProperty::setText);

        assertSame("A", classWithProperty.text);
        context.remove(String.class);
        assertNull(classWithProperty.text);
    }

    @Test
    void whenAddedBeforeModelAvailable() {
        contextDsl.when(String.class).changed().then(classWithProperty::setText);

        context.put(String.class, "A");

        assertSame("A", classWithProperty.text);
        context.remove(String.class);
        assertNull(classWithProperty.text);
    }

    @Test
    void disposeAll() {

        classWithProperty.text = "A";

        When<String> when = contextDsl.when(String.class);
        Action<String> addedModelAction = when.added();
        addedModelAction.then(classWithProperty::setText);

        when.dispose();
        context.put(String.class, "B");

        assertEquals("A", classWithProperty.text);
    }
}