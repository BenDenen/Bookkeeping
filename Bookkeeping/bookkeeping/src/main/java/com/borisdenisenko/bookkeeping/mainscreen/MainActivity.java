package com.borisdenisenko.bookkeeping.mainscreen;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.borisdenisenko.bookkeeping.R;
import com.borisdenisenko.bookkeeping.databinding.ActivityMainBinding;
import com.borisdenisenko.bookkeeping.mainscreen.view.MainViewCallbacks;

public class MainActivity extends AppCompatActivity implements MainViewCallbacks {

    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
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
}
