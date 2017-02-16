package com.borisdenisenko.bookkeeping.gateway.web;

import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by bdenisenko on 16.02.2017.
 */

public class RetrofitModule {

    @Provides
    static Retrofit.Builder provideBuilder() {
        return new Retrofit.Builder();
    }

}
