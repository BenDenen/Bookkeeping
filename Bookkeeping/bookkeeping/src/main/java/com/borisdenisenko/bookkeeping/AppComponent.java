package com.borisdenisenko.bookkeeping;

import com.borisdenisenko.bookkeeping.mainscreen.di.MainScreenModule;
import com.borisdenisenko.bookkeeping.mainscreen.di.MainScreenSubcomponent;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by N750 on 16.02.2017.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    MainScreenSubcomponent plus(MainScreenModule module);
}
