package com.droibit.github.android.di;

import android.support.annotation.NonNull;

public interface ObjectFactory<T> {

    @NonNull
    T getInstance();
}
