package com.droibit.github.android.di;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class DependencyContainer {

    static class Key {

        @NonNull
        private final Type type;

        @Nullable
        private final String tag;

        Key(@NonNull Type type, @Nullable String tag) {
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

            if (!type.equals(key.type)) return false;
            return tag != null ? tag.equals(key.tag) : key.tag == null;

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
        factories = new HashMap<>();
    }

    public void bind(@NonNull AbstractModule... modules) {
        for (AbstractModule module : modules) {
            module.bind(this);
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

    public <T> T get(@NonNull Class<T> target) {
        return get(target, null);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(@NonNull Class<T> target, String tag) {
        final Key key = new Key(target, tag);
        final ObjectFactory<?> factory = factories.get(key);
        if (factory == null) {
            throw new RuntimeException("Does not exist: " + key);
        }
        return (T) factory.get();
    }

    <T> void bind(Key key, ObjectFactory<T> factory) {
        factories.put(key, factory);
    }
}
