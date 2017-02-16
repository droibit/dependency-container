package com.droibit.github.android.di.sample;


import android.support.annotation.NonNull;

public class Item {

    public final String text;

    public final int value;

    public Item(@NonNull String text, int value) {
        this.text = text;
        this.value = value;
    }
}
