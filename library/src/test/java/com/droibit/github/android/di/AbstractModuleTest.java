package com.droibit.github.android.di;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

public class AbstractModuleTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private DependencyContainer container;

    private AbstractModule module;

    @Before
    public void setUp() throws Exception {
        module = new AbstractModule() {
            @Override
            protected void configure() {
            }
        };
        module.bindTo(container);
    }

    @Test
    public void bind_shouldReturnObjectBinder() throws Exception {
        final ObjectBinder<Data> binder1 = module.bind(Data.class);
        assertThat(binder1.key).isEqualTo(new DependencyContainer.Key(Data.class, null));
        assertThat(binder1.container).isSameAs(container);

        final ObjectBinder<Data> binder2 = module.bind(Data.class, "test");
        assertThat(binder2.key).isEqualTo(new DependencyContainer.Key(Data.class, "test"));
        assertThat(binder2.container).isSameAs(container);
    }

    @Test
    public void configure() throws Exception {
        final DependencyContainer container = new DependencyContainer();
        container.bind(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Data.class).provider(new ObjectFactory<Data>() {
                    @NonNull
                    @Override
                    public Data getInstance() {
                        return new Data(getString("text"));
                    }
                });
                bindString("text").provider(new ObjectFactory<String>() {
                    @NonNull
                    @Override
                    public String getInstance() {
                        return "test";
                    }
                });
            }
        });

        final Data actualData = container.get(Data.class);
        final String actualText = container.getString("text");

        assertThat(actualData.text).isEqualTo(actualText);
    }

    @Test
    public void bindBoolean_shouldReturnObjectBinder() throws Exception {
        final ObjectBinder<Boolean> binder = module.bindBoolean("bool");
        assertThat(binder.key).isEqualTo(new DependencyContainer.Key(Boolean.class, "bool"));
        assertThat(binder.container).isSameAs(container);
    }

    @Test
    public void bindInteger_shouldReturnObjectBinder() throws Exception {
        final ObjectBinder<Integer> binder = module.bindInteger("int");
        assertThat(binder.key).isEqualTo(new DependencyContainer.Key(Integer.class, "int"));
        assertThat(binder.container).isSameAs(container);
    }

    @Test
    public void bindLong_shouldReturnObjectBinder() throws Exception {
        final ObjectBinder<Long> binder = module.bindLong("long");
        assertThat(binder.key).isEqualTo(new DependencyContainer.Key(Long.class, "long"));
        assertThat(binder.container).isSameAs(container);
    }

    @Test
    public void bindFloat_shouldReturnObjectBinder() throws Exception {
        final ObjectBinder<Float> binder = module.bindFloat("float");
        assertThat(binder.key).isEqualTo(new DependencyContainer.Key(Float.class, "float"));
        assertThat(binder.container).isSameAs(container);
    }

    @Test
    public void bindDouble_shouldReturnObjectBinder() throws Exception {
        final ObjectBinder<Double> binder = module.bindDouble("double");
        assertThat(binder.key).isEqualTo(new DependencyContainer.Key(Double.class, "double"));
        assertThat(binder.container).isSameAs(container);
    }

    @Test
    public void bindString_shouldReturnObjectBinder() throws Exception {
        final ObjectBinder<String> binder = module.bindString("string");
        assertThat(binder.key).isEqualTo(new DependencyContainer.Key(String.class, "string"));
        assertThat(binder.container).isSameAs(container);
    }

    @Test
    public void get_shouldDelegateDependencyContainer() throws Exception {
        module.get(Data.class);
        verify(container).get(same(Data.class), (String) isNull());

        reset(container);

        module.get(Data.class, "data");
        verify(container).get(same(Data.class), eq("data"));
    }

    @Test
    public void getInteger_shouldDelegateDependencyContainer() throws Exception {
        module.getInteger("int");
        verify(container).getInteger("int");
    }

    @Test
    public void getLong_shouldDelegateDependencyContainer() throws Exception {
        module.getLong("long");
        verify(container).getLong("long");
    }

    @Test
    public void getFloat_shouldDelegateDependencyContainer() throws Exception {
        module.getFloat("float");
        verify(container).getFloat("float");
    }

    @Test
    public void getDouble_shouldDelegateDependencyContainer() throws Exception {
        module.getDouble("double");
        verify(container).getDouble("double");
    }

    @Test
    public void getString_shouldDelegateDependencyContainer() throws Exception {
        module.getString("string");
        verify(container).getString("string");
    }
}