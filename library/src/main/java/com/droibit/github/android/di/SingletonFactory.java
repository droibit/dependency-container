package com.droibit.github.android.di;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;
import static android.support.annotation.VisibleForTesting.PRIVATE;

@RestrictTo(LIBRARY)
class SingletonFactory<T> implements ObjectFactory<T> {

    @VisibleForTesting(otherwise = PRIVATE)
    final ObjectFactory<T> rawFactory;

    @Nullable
    private T instance;

    SingletonFactory(@NonNull ObjectFactory<T> rawFactory) {
        this.rawFactory = rawFactory;
    }

    @NonNull
    @Override
    public T getInstance() {
        if (instance == null) {
            synchronized (rawFactory) {
                if (instance == null) {
                    instance = rawFactory.getInstance();
                }
            }
        }
        return instance;
    }
}
