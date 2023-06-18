package com.link_intersystems.util.context;

public class DuplicateObjectException extends ContextObjectException {

    public DuplicateObjectException(ObjectQualifier<?> objectQualifier) {
        super(objectQualifier);
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Object ");
        sb.append(getQualifier());
        sb.append(" does already exist in context.");
        return sb.toString();
    }
}
