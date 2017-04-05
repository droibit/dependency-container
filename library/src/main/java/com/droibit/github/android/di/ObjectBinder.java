package com.droibit.github.android.di;


import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;

import com.droibit.github.android.di.DependencyContainer.Key;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;
import static android.support.annotation.VisibleForTesting.PRIVATE;

public class ObjectBinder<T> {

    @VisibleForTesting(otherwise = PRIVATE)
    final Key key;

    @VisibleForTesting(otherwise = PRIVATE)
    final DependencyContainer container;

    @RestrictTo(LIBRARY)
    ObjectBinder(@NonNull Key key, @NonNull DependencyContainer container) {
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
