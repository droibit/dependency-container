package com.droibit.github.android.di.sample;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.droibit.github.android.di.AbstractModule;
import com.droibit.github.android.di.DependencyContainer;
import com.droibit.github.android.di.ObjectFactory;

import static com.droibit.github.android.di.sample.SampleApplication.applicationContainer;

public class MainActivity extends AppCompatActivity {

    static class Module extends AbstractModule {

        @NonNull
        private final Activity activity;

        Module(@NonNull Activity activity) {
            this.activity = activity;
        }

        @Override
        protected void configure() {
            bind(Activity.class).provider(new ObjectFactory<Activity>() {
                @NonNull
                @Override
                public Activity get() {
                    return activity;
                }
            });

            bind(Item.class).singleton(new ObjectFactory<Item>() {
                @NonNull
                @Override
                public Item get() {
                    return new Item(
                        /*text=*/getString("text1"),
                        /*value=*/getInteger("int")
                    );
                }
            });

            bindString("text1").provider(new ObjectFactory<String>() {
                @NonNull
                @Override
                public String get() {
                    return "text_1";
                }
            });

            bindString("text2").provider(new ObjectFactory<String>() {
                @NonNull
                @Override
                public String get() {
                    return "text_2";
                }
            });

            bindInt("int").provider(new ObjectFactory<Integer>() {
                @NonNull
                @Override
                public Integer get() {
                    return Integer.MAX_VALUE;
                }
            });
        }
    }

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DependencyContainer container = new DependencyContainer(applicationContainer);
        container.bind(new Module(this));

        Application application = container.get(Application.class);
        Log.d(TAG, "application: " + application.toString());

        Item item1 = container.get(Item.class);
        Item item2 = container.get(Item.class);

        Log.d(TAG, "same instance: " + (item1 == item2));

        Activity activity = container.get(Activity.class);
        Log.d(TAG, "activity: " + activity.getClass().getSimpleName());

        String text1 = container.getString("text1");
        int value = container.getInteger("int");
        Log.d(TAG, "text1=" + text1 + ", value=" + value);

        String text2 = container.getString("text2");
        Log.d(TAG, "text2=" + text2);
    }
}
