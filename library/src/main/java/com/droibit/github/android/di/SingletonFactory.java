package com.droibit.github.android.di;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


class SingletonFactory<T> implements ObjectFactory<T> {

    @Nullable
    private T instance;

    private final ObjectFactory<T> rawFactory;

    private final Object lock;

    SingletonFactory(@NonNull ObjectFactory<T> rawFactory) {
        this.rawFactory = rawFactory;
        this.lock = new Object();
    }

    @NonNull
    @Override
    public T get() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = rawFactory.get();
                }
            }
        }
        return instance;
    }
}
