package com.droibit.github.android.di;


import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;
import static android.support.annotation.RestrictTo.Scope.TESTS;

public class DependencyContainer {

    @RestrictTo(LIBRARY)
    static class Key {

        private final Type type;

        private final String tag;

        Key(Type type, String tag) {
            this.type = type;
            this.tag = tag;
        }

        @Override
        public int hashCode() {
            int result = type.hashCode();
            result = 31 * result + (tag != null ? tag.hashCode() : 0);
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            return type.equals(key.type) &&
                    (tag != null ? tag.equals(key.tag) : key.tag == null);

        }

        @Override
        public String toString() {
            return "Key{" +
                    "type=" + type +
                    ", tag='" + tag + '\'' +
                    '}';
        }
    }

    @RestrictTo(TESTS)
    final Map<Key, ObjectFactory<?>> factories;

    private final boolean allowOverride;

    public DependencyContainer() {
        this(new HashMap<Key, ObjectFactory<?>>(), false);
    }

    public DependencyContainer(@NonNull DependencyContainer container) {
        this(container, false);
    }

    public DependencyContainer(@NonNull DependencyContainer container, boolean allowOverride) {
        this(new HashMap<>(container.factories), allowOverride);
    }

    @RestrictTo(TESTS)
    DependencyContainer(@NonNull Map<Key, ObjectFactory<?>> factories, boolean allowOverride) {
        this.factories = factories;
        this.allowOverride = allowOverride;
    }

    public void bind(@NonNull AbstractModule... modules) {
        for (AbstractModule module : modules) {
            module.bindTo(this);
        }
    }

    public boolean getBoolean(@NonNull String tag) {
        return get(Boolean.class, tag);
    }

    public int getInteger(@NonNull String tag) {
        return get(Integer.class, tag);
    }

    public long getLong(@NonNull String tag) {
        return get(Long.class, tag);
    }

    public float getFloat(@NonNull String tag) {
        return get(Float.class, tag);
    }

    public double getDouble(@NonNull String tag) {
        return get(Double.class, tag);
    }

    @NonNull
    public String getString(@NonNull String tag) {
        return get(String.class, tag);
    }

    @NonNull
    public <T> T get(@NonNull Class<T> target) {
        return get(target, null);
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public <T> T get(@NonNull Class<T> target, String tag) {
        final Key key = new Key(target, tag);
        final ObjectFactory<?> factory = factories.get(key);
        if (factory == null) {
            throw new RuntimeException("Does not exist: " + key);
        }
        return (T) factory.get();
    }

    @RestrictTo(LIBRARY)
    <T> void bind(Key key, ObjectFactory<T> factory) {
        if (!allowOverride && factories.containsKey(key)) {
            throw new IllegalArgumentException("Already exist: " + key);
        }
        factories.put(key, factory);
    }
}
