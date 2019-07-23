package com.kw.one.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

/**
 * @author Kang Wei
 * @date 2019/7/23
 */
public abstract class BaseActivity<VM extends ViewModel, Binding extends ViewDataBinding> extends AppCompatActivity {
    protected Binding mBinding;
    protected VM mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mViewModel = getViewMode();
    }

    protected abstract int getLayoutId();

    @Nullable
    protected abstract VM getViewMode();
}
