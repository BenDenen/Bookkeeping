package com.borisdenisenko.bookkeeping.mainscreen.router;

import com.borisdenisenko.bookkeeping.mainscreen.MainActivity;

/**
 * Created by bdenisenko on 16.02.2017.
 */

public class MainScreenRouterImpl implements MainScreenRouter {

    private final MainActivity mActivity;

    public MainScreenRouterImpl(MainActivity activity) {
        this.mActivity = activity;
    }


    @Override
    public void navigateToDownloadedFile() {
        // TODO: Add param
    }
}
