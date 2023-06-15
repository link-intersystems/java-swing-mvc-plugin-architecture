package com.link_intersystems.swing.action.spi;

import com.link_intersystems.swing.action.AbstractTaskAction2;
import com.link_intersystems.swing.action.TaskProgress;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import static java.util.Objects.*;

public class ServiceLoaderAction<T> extends AbstractTaskAction2<List<T>, Void> {

    private Class<T> serviceType;

    public ServiceLoaderAction(Class<T> serviceType) {
        this.serviceType = requireNonNull(serviceType);
    }

    @Override
    protected List<T> doInBackground(TaskProgress<Void> taskProgress) throws Exception {
        return ServiceLoader.load(serviceType).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }

}
