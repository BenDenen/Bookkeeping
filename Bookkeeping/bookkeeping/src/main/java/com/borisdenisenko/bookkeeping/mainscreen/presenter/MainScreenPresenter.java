package com.borisdenisenko.bookkeeping.mainscreen.presenter;

import com.borisdenisenko.bookkeeping.mainscreen.domain.DownloadWebContentUserCase;
import com.borisdenisenko.bookkeeping.mainscreen.router.MainScreenRouter;
import com.borisdenisenko.bookkeeping.mainscreen.view.MainViewCallbacks;
import com.borisdenisenko.rxviper.ViperPresenter;

/**
 * Created by N750 on 16.02.2017.
 */

public class MainScreenPresenter extends ViperPresenter<MainViewCallbacks, MainScreenRouter> {

    private final DownloadWebContentUserCase mInteractor;

    public MainScreenPresenter(DownloadWebContentUserCase downloadWebContentUserCase) {
        mInteractor = downloadWebContentUserCase;
    }

    public void fetchWebSite(String webSiteUrl) {
        getView().showProgress();
        mInteractor.execute(
                // onNext
                webContentViewModel -> {
                    getView().showHttpState(webContentViewModel.isError());
                    getView().hideProgress();
                },
                // onError
                throwable -> {
                    getView().showError();
                    getView().hideProgress();
                },
                // argument
                webSiteUrl);
    }
}
