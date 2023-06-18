package com.link_intersystems.util.context;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QualifiedObjectTest {

    @Test
    void testEquals() {
        String aString = "a";
        ObjectQualifier<String> aObjectQualifier = new ObjectQualifier<>(String.class, "a");
        QualifiedObject<String> aInstance = new QualifiedObject<>(aObjectQualifier, aString);

        assertEquals(aInstance, new QualifiedObject<>(aObjectQualifier, aString));
        assertNotEquals(aInstance, new QualifiedObject<>(aObjectQualifier, new String(aString)));

        QualifiedObject<String> bInstance = new QualifiedObject<>(new ObjectQualifier<>(String.class, "b"), "b");
        assertNotEquals(aInstance, bInstance);
    }

    @Test
    void testHashCode() {
        String aString = "a";
        ObjectQualifier<String> aObjectQualifier = new ObjectQualifier<>(String.class, "a");
        QualifiedObject<String> aInstance = new QualifiedObject<>(aObjectQualifier, aString);

        assertEquals(aInstance.hashCode(), new QualifiedObject<>(aObjectQualifier, aString).hashCode());
    }
}