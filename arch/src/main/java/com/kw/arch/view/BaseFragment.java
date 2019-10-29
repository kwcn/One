package com.kw.arch.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.kw.arch.viewmodel.BaseViewModel;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public abstract class BaseFragment<VM extends BaseViewModel, ViewBinding extends ViewDataBinding> extends Fragment {
    protected ViewBinding mBinding;
    protected VM mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = getVModel();
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), null, false);
        return mBinding.getRoot();
    }

    protected abstract VM getVModel();

    protected abstract int getLayoutId();

}
