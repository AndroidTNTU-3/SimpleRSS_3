package com.example.simplerss;

import android.app.Application;
import android.content.Context;

public class RssReaderApp extends Application {

    private static Context context;

    @Override
    public final void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
