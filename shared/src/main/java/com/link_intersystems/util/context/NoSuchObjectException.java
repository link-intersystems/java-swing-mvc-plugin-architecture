package com.link_intersystems.util.context;

public class NoSuchObjectException extends ContextObjectException {

    public NoSuchObjectException(ObjectQualifier<?> objectQualifier) {
        super(objectQualifier);
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Object ");
        sb.append(getQualifier());
        sb.append(" does not exist in context.");
        return sb.toString();
    }
}
