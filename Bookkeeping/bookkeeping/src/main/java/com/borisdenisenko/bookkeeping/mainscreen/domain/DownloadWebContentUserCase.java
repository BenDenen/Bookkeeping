package com.borisdenisenko.bookkeeping.mainscreen.domain;

import android.util.Log;

import com.borisdenisenko.bookkeeping.gateway.WebSiteDataRepository;
import com.borisdenisenko.rxviper.Interactor;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by bdenisenko on 16.02.2017.
 */

public class DownloadWebContentUserCase extends Interactor<String, WebContentViewModel> {
    private final WebSiteDataRepository mRepository;
    private final WebContentMapper mMapper;

    public DownloadWebContentUserCase(Scheduler subscribeOn, Scheduler observeOn, WebSiteDataRepository repository, WebContentMapper mapper) {
        super(subscribeOn, observeOn);
        this.mRepository = repository;
        this.mMapper = mapper;
    }

    @Override
    protected Observable<WebContentViewModel> createObservable(String webSiteUrl) {
        return mRepository.downloadWebContent(webSiteUrl).map(mMapper::map);
    }
}
