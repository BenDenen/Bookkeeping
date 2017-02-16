package com.borisdenisenko.bookkeeping;

import android.app.Application;

/**
 * Created by N750 on 16.02.2017.
 */

public class WebContentApplication extends Application {
    private AppComponent component;

    public AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}