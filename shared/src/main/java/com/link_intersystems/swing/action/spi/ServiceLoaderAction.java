package com.link_intersystems.swing.action.spi;

import com.link_intersystems.swing.action.concurrent.DefaultTaskAction;
import com.link_intersystems.util.concurrent.task.TaskProgress;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import static java.util.Objects.*;

public class ServiceLoaderAction<T> extends DefaultTaskAction<List<T>, Void> {

    private Class<T> serviceType;

    public ServiceLoaderAction(Class<T> serviceType) {
        this.serviceType = requireNonNull(serviceType);
    }

    @Override
    protected List<T> doInBackground(TaskProgress<Void> taskProgress) {
        return ServiceLoader.load(serviceType).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }

}
