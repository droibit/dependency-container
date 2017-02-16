package com.droibit.github.android.di;


import android.support.annotation.NonNull;

import com.droibit.github.android.di.DependencyContainer.Key;

public abstract class AbstractModule {

    private DependencyContainer container;

    public abstract void bind();

    final void bind(DependencyContainer container) {
        this.container = container;
        this.bind();
    }

    protected  <T> ObjectBinder<T> bind(@NonNull Class<T> target) {
        return bind(target, null);
    }

    protected <T> ObjectBinder<T> bind(@NonNull Class<T> target, String tag) {
        return new ObjectBinder<>(new Key(target, tag), container);
    }

    protected ObjectBinder<Integer> bindInt(@NonNull String tag) {
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
