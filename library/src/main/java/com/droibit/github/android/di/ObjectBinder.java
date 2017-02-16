package com.droibit.github.android.di;


import android.support.annotation.NonNull;

import com.droibit.github.android.di.DependencyContainer.Key;

public class ObjectBinder<T> {

    private Key key;

    private DependencyContainer container;

    ObjectBinder(Key key, DependencyContainer container) {
        this.key = key;
        this.container = container;
    }

    public void singleton(@NonNull ObjectFactory<T> factory) {
        container.bind(key, new SingletonFactory<>(factory));
    }

    public void provider(@NonNull ObjectFactory<T> factory) {
        container.bind(key, factory);
    }
}
