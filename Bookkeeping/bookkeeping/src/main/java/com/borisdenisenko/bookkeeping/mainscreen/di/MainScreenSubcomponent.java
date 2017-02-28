package com.borisdenisenko.bookkeeping.mainscreen.di;

import com.borisdenisenko.bookkeeping.mainscreen.presenter.MainScreenPresenter;
import com.borisdenisenko.bookkeeping.mainscreen.router.MainScreenRouter;
import com.borisdenisenko.bookkeeping.mainscreen.view.MainViewCallbacks;

import dagger.Subcomponent;

/**
 * Created by N750 on 16.02.2017.
 */

@MainScreenScope
@Subcomponent(modules = MainScreenModule.class)
public interface MainScreenSubcomponent {

    void inject(MainViewCallbacks view);

    MainScreenPresenter mainPresenter();

    MainScreenRouter mainRouter();
}
