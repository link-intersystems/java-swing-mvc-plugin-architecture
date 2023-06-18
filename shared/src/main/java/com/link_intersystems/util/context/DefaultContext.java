package com.link_intersystems.util.context;


import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class DefaultContext extends AbstractContext implements AutoCloseable {

    private Map<ObjectQualifier<?>, QualifiedObject<?>> qualifiedInstances;

    public DefaultContext() {
        this(ConcurrentHashMap::new);
    }

    public DefaultContext(Supplier<Map<ObjectQualifier<?>, QualifiedObject<?>>> mapSupplier) {
        this.qualifiedInstances = mapSupplier.get();
    }

    @Override
    protected boolean putInstance(QualifiedObject<?> qualifiedObject) {
        ObjectQualifier<?> objectQualifier = qualifiedObject.getQualifier();
        QualifiedObject<?> existentQualifiedObject = getInstance(objectQualifier);

        if (existentQualifiedObject != null) {
            return existentQualifiedObject.equals(qualifiedObject);
        }

        return qualifiedInstances.put(objectQualifier, qualifiedObject) == null;
    }

    @Override
    protected QualifiedObject<?> removeInstance(ObjectQualifier<?> objectQualifier) {
        return qualifiedInstances.remove(objectQualifier);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> QualifiedObject<T> getInstance(ObjectQualifier<? super T> objectQualifier) {
        return (QualifiedObject<T>) qualifiedInstances.get(objectQualifier);
    }

    @Override
    public boolean contains(ObjectQualifier<?> objectQualifier) {
        return qualifiedInstances.containsKey(objectQualifier);
    }

    @Override
    public Stream<QualifiedObject<?>> stream() {
        return qualifiedInstances.values().stream();
    }

    @Override
    public void close() throws Exception {
        Collection<QualifiedObject<?>> values = qualifiedInstances.values();
        for (QualifiedObject<?> qualifiedObject : values) {
            Object object = qualifiedObject.getObject();

            if (object != this && object instanceof AutoCloseable autoCloseable) {
                autoCloseable.close();
            }

            remove(qualifiedObject.getQualifier());
        }
    }
}
