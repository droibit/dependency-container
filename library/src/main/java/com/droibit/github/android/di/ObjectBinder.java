package com.droibit.github.android.di;


import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import com.droibit.github.android.di.DependencyContainer.Key;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;

public class ObjectBinder<T> {

    private Key key;

    private DependencyContainer container;

    @RestrictTo(LIBRARY)
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
