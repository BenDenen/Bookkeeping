package com.borisdenisenko.bookkeeping.gateway;

import com.borisdenisenko.bookkeeping.gateway.web.WebApiRepositoryImpl;

import dagger.Provides;

/**
 * Created by bdenisenko on 16.02.2017.
 */

public class RepositoryModule {

    @Provides
    static WebSiteDataRepository provideRepository() {
        return new WebApiRepositoryImpl();
    }
}
