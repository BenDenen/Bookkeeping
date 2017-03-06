package com.borisdenisenko.bookkeeping.mainscreen;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.borisdenisenko.bookkeeping.R;
import com.borisdenisenko.bookkeeping.WebContentApplication;
import com.borisdenisenko.bookkeeping.databinding.ActivityMainBinding;
import com.borisdenisenko.bookkeeping.mainscreen.di.MainScreenModule;
import com.borisdenisenko.bookkeeping.mainscreen.di.MainScreenSubcomponent;
import com.borisdenisenko.bookkeeping.mainscreen.presenter.MainScreenPresenter;
import com.borisdenisenko.bookkeeping.mainscreen.router.MainScreenRouter;
import com.borisdenisenko.bookkeeping.mainscreen.view.MainViewCallbacks;

public class MainActivity extends AppCompatActivity implements MainViewCallbacks, MainScreenRouter {

    private static final int PERMISSION_REQUEST_CODE = 0;

    ActivityMainBinding mBinding;
    private ScopeHolder mHolder;
    private MainScreenModule mModule;
    private MainScreenSubcomponent mSubcomponent;
    private MainScreenPresenter mPresenter;
    private MainScreenRouter mRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mHolder = (ScopeHolder) getLastCustomNonConfigurationInstance();

        if (mHolder == null) {
            mModule = new MainScreenModule();
            mSubcomponent = ((WebContentApplication) getApplication()).getComponent()
                    .plus(mModule);
            mHolder = new ScopeHolder(mModule, mSubcomponent);
        } else {
            mModule = mHolder.module;
            mSubcomponent = mHolder.subcomponent;
        }

        mModule.setMainActivity(this);
        mSubcomponent.inject(this);
        mPresenter = mSubcomponent.mainPresenter();
        mRouter = mSubcomponent.mainRouter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.takeView(this);
        mPresenter.takeRouter(mRouter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length == 2) {
            if ((grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    && (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                mPresenter.fetchWebSite(mBinding.testWebSite.getText().toString());
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            mModule.setMainActivity(null);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.dropView(this);
        mPresenter.dropRouter(mRouter);
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
    public void showHttpState(boolean isSuccess) {
        mBinding.testWebSiteStatus.setText(String.valueOf(isSuccess));
    }

    @Override
    public void navigateToDownloadedFile() {

    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, PERMISSION_REQUEST_CODE);
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
