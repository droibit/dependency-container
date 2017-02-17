package com.droibit.github.android.di.sample;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.droibit.github.android.di.AbstractModule;
import com.droibit.github.android.di.DependencyContainer;
import com.droibit.github.android.di.ObjectFactory;


public class SampleApplication extends Application {

    private static class Module extends AbstractModule {

        private final Application application;

        public Module(@NonNull Application application) {
            this.application = application;
        }

        @Override
        public void bind() {
            bind(Application.class).provider(new ObjectFactory<Application>() {
                @NonNull
                @Override
                public Application get() {
                    return application;
                }
            });

            bind(Context.class, "applicationContext").provider(new ObjectFactory<Context>() {
                @NonNull
                @Override
                public Context get() {
                    return application;
                }
            });
        }
    }

    public static DependencyContainer applicationContainer = new DependencyContainer();

    @Override
    public void onCreate() {
        super.onCreate();

        applicationContainer.bind(new Module(this));
    }
}
