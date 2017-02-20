package com.borisdenisenko.bookkeeping.gateway;

import com.borisdenisenko.bookkeeping.AppModule;
import com.borisdenisenko.bookkeeping.gateway.web.WebApiRepositoryImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by bdenisenko on 16.02.2017.
 */

@Module(includes = AppModule.class)
public class RepositoryModule {

    @Provides
    static WebSiteDataRepository provideRepository() {
        return new WebApiRepositoryImpl();
    }
}
