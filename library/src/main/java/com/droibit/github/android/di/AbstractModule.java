package com.droibit.github.android.di;


import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import com.droibit.github.android.di.DependencyContainer.Key;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;

@SuppressWarnings("WeakerAccess")
public abstract class AbstractModule {

    private DependencyContainer container;

    protected abstract void configure();

    @RestrictTo(LIBRARY)
    final void bindTo(DependencyContainer container) {
        this.container = container;
        this.configure();
    }

    protected <T> ObjectBinder<T> bind(@NonNull Class<T> target) {
        return bind(target, null);
    }

    protected <T> ObjectBinder<T> bind(@NonNull Class<T> target, String tag) {
        return new ObjectBinder<>(new Key(target, tag), container);
    }

    protected ObjectBinder<Boolean> bindBoolean(@NonNull String tag) {
        return new ObjectBinder<>(new Key(Boolean.class, tag), container);
    }

    protected ObjectBinder<Integer> bindInteger(@NonNull String tag) {
        return new ObjectBinder<>(new Key(Integer.class, tag), container);
    }

    protected ObjectBinder<Long> bindLong(@NonNull String tag) {
        return new ObjectBinder<>(new Key(Long.class, tag), container);
    }

    protected ObjectBinder<Float> bindFloat(@NonNull String tag) {
        return new ObjectBinder<>(new Key(Float.class, tag), container);
    }

    protected ObjectBinder<Double> bindDouble(@NonNull String tag) {
        return new ObjectBinder<>(new Key(Double.class, tag), container);
    }

    protected ObjectBinder<String> bindString(@NonNull String tag) {
        return new ObjectBinder<>(new Key(String.class, tag), container);
    }

    protected <T> T get(@NonNull Class<T> target) {
        return container.get(target, null);
    }

    protected <T> T get(@NonNull Class<T> target, String tag) {
        return container.get(target, tag);
    }

    protected boolean getBoolean(@NonNull String tag) {
        return container.getBoolean(tag);
    }

    protected int getInteger(@NonNull String tag) {
        return container.getInteger(tag);
    }

    protected long getLong(@NonNull String tag) {
        return container.getLong(tag);
    }

    protected float getFloat(@NonNull String tag) {
        return container.getFloat(tag);
    }

    protected double getDouble(@NonNull String tag) {
        return container.getDouble(tag);
    }

    @NonNull
    protected String getString(@NonNull String tag) {
        return container.getString(tag);
    }
}
