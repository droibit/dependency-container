package com.droibit.github.android.di;


import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import com.droibit.github.android.di.DependencyContainer.Key;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;
import static android.support.annotation.RestrictTo.Scope.TESTS;

public class ObjectBinder<T> {

    @RestrictTo(TESTS)
    Key key;

    @RestrictTo(TESTS)
    DependencyContainer container;

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
