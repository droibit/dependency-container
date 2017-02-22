package com.droibit.github.android.di;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;
import static android.support.annotation.RestrictTo.Scope.TESTS;

@RestrictTo(LIBRARY)
class SingletonFactory<T> implements ObjectFactory<T> {

    @RestrictTo(TESTS)
    final ObjectFactory<T> rawFactory;

    @Nullable
    private T instance;

    private final Object lock;

    SingletonFactory(@NonNull ObjectFactory<T> rawFactory) {
        this.rawFactory = rawFactory;
        this.lock = new Object();
    }

    @NonNull
    @Override
    public T getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = rawFactory.getInstance();
                }
            }
        }
        return instance;
    }
}
