package com.kw.one.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.AnyThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kw.one.R;
import com.kw.one.databinding.FragmentRootBinding;
import com.kw.one.ui.widget.RefreshWorker;

/**
 * @author Kang Wei
 * @date 2019/7/23
 */
public abstract class BaseFragment<VM extends ViewModel, Binding extends ViewDataBinding> extends Fragment {
    protected Binding mBinding;
    protected VM mViewModel;
    private SwipeRefreshLayout mRefreshLayout;
    private RefreshWorker mRefreshWorker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentRootBinding rootBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_root, container, false);
        mRefreshLayout = rootBinding.refresh;
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), null, false);
        mRefreshLayout.addView(mBinding.getRoot());
        mRefreshLayout.setEnabled(false);
        mViewModel = getViewModel();
        return rootBinding.getRoot();
    }


    /**
     * 设置刷新逻辑
     *
     * @param taskCount 需要刷新的任务数
     * @param refresh   刷新动作
     */
    protected void setRefresh(int taskCount, @NonNull Runnable refresh) {
        if (taskCount > 0) {
            mRefreshWorker = new RefreshWorker(mRefreshLayout, taskCount, refresh);
        } else {
            mRefreshLayout.setEnabled(false);
        }
    }

    /**
     * 每次完成一个刷新任务，不管是否获得有效数据，必须调用此函数，否则进度不会消失
     */
    @AnyThread
    protected void cutRefreshTask() {
        if (mRefreshWorker != null) {
            mRefreshWorker.cutRefreshTask();
        }
    }

    protected abstract int getLayoutId();

    @Nullable
    protected abstract VM getViewModel();
}
