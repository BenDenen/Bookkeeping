package com.borisdenisenko.bookkeeping.mainscreen.di;

import com.borisdenisenko.bookkeeping.mainscreen.presenter.MainScreenPresenter;
import com.borisdenisenko.bookkeeping.mainscreen.router.MainScreenRouter;

import dagger.Subcomponent;

/**
 * Created by N750 on 16.02.2017.
 */

@MainScreenScope
@Subcomponent(modules = MainScreenModule.class)
public interface MainScreenSubcomponent {

    MainScreenPresenter mainPresenter();

    MainScreenRouter mainRouter();
}
