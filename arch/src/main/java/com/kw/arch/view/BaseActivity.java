package com.kw.arch.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author Kang Wei
 * @date 2019/10/30
 */
public abstract class BaseActivity<ViewBinding extends ViewDataBinding> extends AppCompatActivity {
    protected ViewBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
    }

    protected abstract int getLayoutId();
}