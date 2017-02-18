package com.droibit.github.android.di;

import android.support.annotation.NonNull;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class SingletonFactoryTest {

    @Test
    public void get_shouldReturnSameInstance() throws Exception {
        final ObjectFactory<Data> rawFactory = new ObjectFactory<Data>() {
            @NonNull
            @Override
            public Data get() {
                return new Data("test");
            }
        };
        final SingletonFactory<Data> singletonFactory = new SingletonFactory<>(rawFactory);

        assertThat(singletonFactory.rawFactory).isSameAs(rawFactory);

        final Data data1 = singletonFactory.get();
        final Data data2 = singletonFactory.get();

        assertThat(data1).isSameAs(data2);
    }
}