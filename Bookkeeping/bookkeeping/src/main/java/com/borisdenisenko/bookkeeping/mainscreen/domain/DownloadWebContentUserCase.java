package com.borisdenisenko.bookkeeping.mainscreen.domain;

import com.borisdenisenko.rxviper.Interactor;

import java.util.Collection;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by bdenisenko on 16.02.2017.
 */

public class DownloadWebContentUserCase extends Interactor<Collection<String>, Collection<ResponseBody>> {
    private final Retrofit.Builder mBuilder;

    public DownloadWebContentUserCase(Scheduler subscribeOn, Scheduler observeOn, Retrofit.Builder builder) {
        super(subscribeOn, observeOn);
        this.mBuilder = builder;
        this.mapper = mapper;
    }

    @Override
    protected Observable<Collection<ResponseBody>> createObservable(Integer amount) {
        return storage.getCheeses(amount)
                .map(mapper::map);
    }

    @Override
    protected Observable<Collection<ResponseBody>> createObservable(Collection<String> strings) {
        return null;
    }
}
