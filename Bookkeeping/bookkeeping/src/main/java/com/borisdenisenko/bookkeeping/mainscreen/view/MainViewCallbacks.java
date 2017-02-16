package com.borisdenisenko.bookkeeping.mainscreen.view;

import com.borisdenisenko.rxviper.ViewCallbacks;

/**
 * Created by bdenisenko on 16.02.2017.
 */

public interface MainViewCallbacks extends ViewCallbacks {

    void hideProgress();

    void showError();

    void showProgress();
}
