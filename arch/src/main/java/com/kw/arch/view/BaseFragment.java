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
    protected GLoading.Holder mLoadHolder;
    protected RefreshWorker mRefreshWorker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = getVModel();
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), null, false);
        mLoadHolder = GLoading.getDefault().wrap(mBinding.getRoot());
        mRefreshWorker = new RefreshWorker(getRefreshWorker());
        return mLoadHolder.getWrapper();
    }

    protected abstract VM getVModel();

    protected abstract int getLayoutId();

    // 所有任务都完成后执行的内容
    protected Runnable getRefreshWorker() {
        return () -> mLoadHolder.showLoadSuccess();
    }

    // 开启刷新任务
    protected void startRefresh(int taskCount) {
        mLoadHolder.showLoading();
        mRefreshWorker.start(taskCount);
    }

    // 每当有任务完成应调用该方法
    protected void cutRefreshTask(String taskKey) {
        mRefreshWorker.finishOneTask(taskKey);
    }
}
