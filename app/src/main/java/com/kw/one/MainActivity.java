package com.kw.one;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.kw.one.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Nullable
    @Override
    protected ViewModel getViewMode() {
        return null;
    }
}
