package com.borisdenisenko.bookkeeping.mainscreen;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.borisdenisenko.bookkeeping.R;
import com.borisdenisenko.bookkeeping.databinding.ActivityMainBinding;
import com.borisdenisenko.bookkeeping.mainscreen.di.MainScreenModule;
import com.borisdenisenko.bookkeeping.mainscreen.di.MainScreenSubcomponent;
import com.borisdenisenko.bookkeeping.mainscreen.presenter.MainScreenPresenter;
import com.borisdenisenko.bookkeeping.mainscreen.router.MainScreenRouter;
import com.borisdenisenko.bookkeeping.mainscreen.view.MainViewCallbacks;

public class MainActivity extends AppCompatActivity implements MainViewCallbacks, MainScreenRouter {

    ActivityMainBinding mBinding;
    private ScopeHolder mHolder;
    private MainScreenModule mModule;
    private MainScreenSubcomponent mSubcomponent;
    private MainScreenPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mHolder = (ScopeHolder) getLastCustomNonConfigurationInstance();
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void navigateToDownloadedFile() {

    }

    final static class ScopeHolder {
        final MainScreenModule module;
        final MainScreenSubcomponent subcomponent;

        ScopeHolder(MainScreenModule module, MainScreenSubcomponent subcomponent) {
            this.module = module;
            this.subcomponent = subcomponent;
        }
    }
}
