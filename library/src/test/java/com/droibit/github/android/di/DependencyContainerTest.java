package com.droibit.github.android.di;

import com.droibit.github.android.di.DependencyContainer.Key;

import org.assertj.core.internal.cglib.proxy.Factory;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DependencyContainerTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private Map<Key, ObjectFactory<?>> factories;

    @Test
    public void constructor_shouldCopyContainer() throws Exception {
        final DependencyContainer parentContainer = new DependencyContainer();
        parentContainer.bind(new Key(Integer.class, "int"), mock(ObjectFactory.class));
        parentContainer.bind(new Key(Data.class, null), mock(ObjectFactory.class));

        final DependencyContainer container = new DependencyContainer(parentContainer);
        assertThat(container.factories).containsOnlyKeys(
                new Key(Integer.class, "int"),
                new Key(Data.class, null)
        );
    }

    @Test
    public void bind_bindTo() throws Exception {
        final DependencyContainer container1 = new DependencyContainer(factories, false);
        final AbstractModule module1 = mock(AbstractModule.class);
        container1.bind(module1);

        verify(module1).bindTo(container1);
        reset(module1);

        final DependencyContainer container2 = new DependencyContainer(factories, false);
        final AbstractModule module2 = mock(AbstractModule.class);
        container2.bind(module1, module2);

        verify(module2).bindTo(container2);
        verify(module2).bindTo(container2);
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Test
    public void bind_notAllowOverride() throws Exception {
        final DependencyContainer container = new DependencyContainer(factories, false);
        when(factories.containsKey(any())).thenReturn(true);

        try {
            container.bind(mock(Key.class), mock(ObjectFactory.class));
        } catch (Exception e) {
            assertThat(e).isExactlyInstanceOf(IllegalArgumentException.class);
        }

        when(factories.containsKey(any())).thenReturn(false);
        final Key key = mock(Key.class);
        final ObjectFactory factory = mock(ObjectFactory.class);
        container.bind(key, factory);

        verify(factories).put(key, factory);
    }

    @Test
    public void bind_allowOverride() throws Exception {
        final DependencyContainer container = new DependencyContainer(factories, true);
        final Key key = mock(Key.class);
        final ObjectFactory factory = mock(ObjectFactory.class);
        container.bind(key, factory);

        verify(factories).put(any(Key.class), any(ObjectFactory.class));
    }

    @Test
    public void get() throws Exception {
        final DependencyContainer container1 = new DependencyContainer();
        final Key key1 = new Key(Data.class, null);
        final Data data1 = mock(Data.class);
        final ObjectFactory<Data> factory1 = when(mock(ObjectFactory.class).get()).thenReturn(data1).getMock();
        container1.bind(key1, factory1);

        final Data actualData1 = container1.get(Data.class);
        assertThat(actualData1).isSameAs(data1);

        final DependencyContainer container2 = new DependencyContainer();
        final Key key2 = new Key(Data.class, "data");
        final Data data2 = mock(Data.class);
        final ObjectFactory<Data> factory2 = when(mock(ObjectFactory.class).get()).thenReturn(data2).getMock();
        container2.bind(key2, factory2);

        final Data actualData2 = container2.get(Data.class, "data");
        assertThat(actualData2).isSameAs(data2);
    }

    @Test(expected = RuntimeException.class)
    public void get_notExistKey() throws Exception {
        final DependencyContainer container = new DependencyContainer();
        container.get(Data.class);
        fail("error");
    }

    @Test
    public void getInteger() throws Exception {
        final DependencyContainer container = new DependencyContainer();
        final Key key = new Key(Integer.class, "int");
        final ObjectFactory<Integer> factory = when(mock(ObjectFactory.class).get()).thenReturn(Integer.MAX_VALUE).getMock();
        container.bind(key, factory);

        final int value = container.getInteger("int");
        assertThat(value).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    public void getLong() throws Exception {
        final DependencyContainer container = new DependencyContainer();
        final Key key = new Key(Long.class, "long");
        final ObjectFactory<Long> factory = when(mock(ObjectFactory.class).get()).thenReturn(Long.MAX_VALUE).getMock();
        container.bind(key, factory);

        final long value = container.getLong("long");
        assertThat(value).isEqualTo(Long.MAX_VALUE);
    }

    @Test
    public void getFloat() throws Exception {
        final DependencyContainer container = new DependencyContainer();
        final Key key = new Key(Float.class, "float");
        final ObjectFactory<Float> factory = when(mock(ObjectFactory.class).get()).thenReturn(Float.MAX_VALUE).getMock();
        container.bind(key, factory);

        final float value = container.getFloat("float");
        assertThat(value).isEqualTo(Float.MAX_VALUE);
    }

    @Test
    public void getDouble() throws Exception {
        final DependencyContainer container = new DependencyContainer();
        final Key key = new Key(Double.class, "float");
        final ObjectFactory<Double> factory = when(mock(ObjectFactory.class).get()).thenReturn(Double.MAX_VALUE).getMock();
        container.bind(key, factory);

        final double value = container.getDouble("float");
        assertThat(value).isEqualTo(Double.MAX_VALUE);
    }

    @Test
    public void getString() throws Exception {
        final DependencyContainer container = new DependencyContainer();
        final Key key = new Key(String.class, "string");
        final ObjectFactory<String> factory = when(mock(ObjectFactory.class).get()).thenReturn("test").getMock();
        container.bind(key, factory);

        final String value = container.getString("string");
        assertThat(value).isEqualTo("test");
    }
}