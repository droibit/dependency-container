package com.droibit.github.android.di;

import android.support.annotation.NonNull;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class SingletonFactoryTest {

    @Test
    public void getInstance_shouldReturnSameInstance() throws Exception {
        final ObjectFactory<Data> rawFactory = new ObjectFactory<Data>() {
            @NonNull
            @Override
            public Data getInstance() {
                return new Data("test");
            }
        };
        final SingletonFactory<Data> singletonFactory = new SingletonFactory<>(rawFactory);

        assertThat(singletonFactory.rawFactory).isSameAs(rawFactory);

        final Data data1 = singletonFactory.getInstance();
        final Data data2 = singletonFactory.getInstance();

        assertThat(data1).isSameAs(data2);
    }
}