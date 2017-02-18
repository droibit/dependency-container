package com.droibit.github.android.di;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ObjectBinderTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private DependencyContainer.Key key;

    @Mock
    private DependencyContainer container;

    @Captor
    private ArgumentCaptor<SingletonFactory<Data>> argumentCaptor;

    @SuppressWarnings("unchecked")
    @Test
    public void singleton_shouldBindSingletonFactory() throws Exception {
        final ObjectBinder<Data> binder = new ObjectBinder<>(key, container);
        final ObjectFactory<Data> factory = (ObjectFactory<Data>) mock(ObjectFactory.class);
        binder.singleton(factory);

        verify(container).bind(same(key), argumentCaptor.capture());

        final Object value = argumentCaptor.getValue();
        assertThat(value).isInstanceOf(SingletonFactory.class);

        final SingletonFactory<Data> singletonFactory = (SingletonFactory<Data>) value;
        assertThat(singletonFactory.rawFactory).isSameAs(factory);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void provider_shouldBindRawFactory() throws Exception {
        final ObjectBinder<Data> binder = new ObjectBinder<>(key, container);
        final ObjectFactory<Data> factory = (ObjectFactory<Data>) mock(ObjectFactory.class);
        binder.provider(factory);

        verify(container).bind(same(key), same(factory));
    }
}