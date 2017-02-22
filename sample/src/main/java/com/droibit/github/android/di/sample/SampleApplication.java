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

        Module(@NonNull Application application) {
            this.application = application;
        }

        @Override
        protected void configure() {
            bind(Application.class).provider(new ObjectFactory<Application>() {
                @NonNull
                @Override
                public Application getInstance() {
                    return application;
                }
            });

            bind(Context.class, "applicationContext").provider(new ObjectFactory<Context>() {
                @NonNull
                @Override
                public Context getInstance() {
                    return application;
                }
            });
        }
    }

    public static DependencyContainer appContainer = new DependencyContainer();

    @Override
    public void onCreate() {
        super.onCreate();

        appContainer.bind(new Module(this));
    }
}
