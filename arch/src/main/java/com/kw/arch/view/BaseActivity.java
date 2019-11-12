package com.kw.arch.view;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

/**
 * @author Kang Wei
 * @date 2019/10/30
 */
public abstract class BaseActivity<VM extends ViewModel, Binding extends ViewDataBinding> extends AppCompatActivity {
    protected Binding mBinding;
    protected VM mViewModel;
    protected GLoading.Holder mLoadHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getApplicationContext()),
                getLayoutId(), null, false);
        mLoadHolder = GLoading.getDefault().wrap(mBinding.getRoot());
        setContentView(mLoadHolder.getWrapper());
        mViewModel = getViewMode();
    }

    protected abstract int getLayoutId();

    @Nullable
    protected abstract VM getViewMode();
}
