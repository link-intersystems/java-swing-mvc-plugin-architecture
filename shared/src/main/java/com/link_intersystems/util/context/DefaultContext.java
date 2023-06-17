package com.link_intersystems.util.context;


import java.util.HashMap;
import java.util.Map;

public class DefaultContext extends AbstractContext {

    private Map<Qualifier<?>, QualifiedInstance<?>> qualifiedInstances = new HashMap<>();

    @Override
    protected boolean putInstance(QualifiedInstance<?> qualifiedInstance) {
        Qualifier<?> qualifier = qualifiedInstance.getQualifier();
        QualifiedInstance<?> existentQualifiedInstance = getInstance(qualifier);

        if (existentQualifiedInstance != null) {
            return existentQualifiedInstance.equals(qualifiedInstance);
        }

        return qualifiedInstances.put(qualifier, qualifiedInstance) == null;
    }

    @Override
    protected QualifiedInstance<?> removeInstance(Qualifier<?> qualifier) {
        return qualifiedInstances.remove(qualifier);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> QualifiedInstance<T> getInstance(Qualifier<? super T> qualifier) {
        return (QualifiedInstance<T>) qualifiedInstances.get(qualifier);
    }


}
