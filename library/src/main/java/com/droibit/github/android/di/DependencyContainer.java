package com.droibit.github.android.di;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Type;

public class DependencyContainer {

    static class Key {

        @NonNull
        final Type type;

        @Nullable
        final String tag;

        Key(@NonNull Type type) {
            this(type, null);
        }

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
    }
}
