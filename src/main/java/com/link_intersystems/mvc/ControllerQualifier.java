package com.link_intersystems.mvc;

import java.util.Objects;

import static java.util.Objects.*;

public class ControllerQualifier<T> {

    private Class<T> type;
    private String name;

    public ControllerQualifier(Class<T> type) {
        this(type, null);
    }

    public ControllerQualifier(Class<T> type, String name) {
        this.type = requireNonNull(type);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Class<T> getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ControllerQualifier<?> that = (ControllerQualifier<?>) o;
        return Objects.equals(getType(), that.getType()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getName());
    }

    @Override
    public String toString() {
        return "ServiceQualifier{" +
                getType().getName() +
                "<" + getName() + '>' +
                '}';
    }
}
