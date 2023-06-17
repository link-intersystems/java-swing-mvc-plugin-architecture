package com.link_intersystems.util.context;

import com.link_intersystems.util.context.QualifiedInstance;
import com.link_intersystems.util.context.Qualifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QualifiedInstanceTest {

    @Test
    void testEquals() {
        String aString = "a";
        Qualifier<String> aQualifier = new Qualifier<>(String.class, "a");
        QualifiedInstance<String> aInstance = new QualifiedInstance<>(aQualifier, aString);

        assertEquals(aInstance, new QualifiedInstance<>(aQualifier, aString));
        assertNotEquals(aInstance, new QualifiedInstance<>(aQualifier, new String(aString)));

        QualifiedInstance<String> bInstance = new QualifiedInstance<>(new Qualifier<>(String.class, "b"), "b");
        assertNotEquals(aInstance, bInstance);
    }

    @Test
    void testHashCode() {
        String aString = "a";
        Qualifier<String> aQualifier = new Qualifier<>(String.class, "a");
        QualifiedInstance<String> aInstance = new QualifiedInstance<>(aQualifier, aString);

        assertEquals(aInstance.hashCode(), new QualifiedInstance<>(aQualifier, aString).hashCode());
    }
}