package com.droibit.github.android.di;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;

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

    private final Map<Key, ObjectFactory<?>> factories;

    public DependencyContainer() {
        this(new HashMap<Key, ObjectFactory<?>>());
    }

    public DependencyContainer(@NonNull DependencyContainer container) {
        this(new HashMap<>(container.factories));
    }

    private DependencyContainer(@NonNull Map<Key, ObjectFactory<?>> factories) {
        this.factories = factories;
    }

    public void bind(@NonNull AbstractModule... modules) {
        for (AbstractModule module : modules) {
            module.bindTo(this);
        }
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
        factories.put(key, factory);
    }
}
