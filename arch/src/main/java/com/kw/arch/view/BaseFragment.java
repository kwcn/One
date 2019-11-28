package com.kw.arch.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.kw.arch.aspect.ICheckNet;
import com.kw.arch.model.BaseDataSource;
import com.kw.arch.viewmodel.BaseViewModel;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public abstract class BaseFragment<VM extends BaseViewModel, ViewBinding extends ViewDataBinding> extends Fragment implements ILoaded, ICheckNet {
    protected ViewBinding mBinding;
    protected VM mViewModel;
    protected GLoading.Holder mLoadHolder;
    protected LoadedController mLoadedController;
    protected Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = getVModel();
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), null, false);
        mLoadHolder = GLoading.getDefault().wrap(mBinding.getRoot());
        mLoadedController = new LoadedController(this);
        return mLoadHolder.getWrapper();
    }

    protected abstract VM getVModel();

    protected abstract int getLayoutId();

    /**
     * 设置加载失败界面的重试按钮监听事件
     */
    protected void setErrorReloadBtnOnClickListener(GLoading.RetryLoad load) {
        mLoadHolder.withRetry(load);
    }

    /**
     * 数据加载结果
     *
     * @param isValid 是否获得有效数据
     */
    @Override
    public void onLoaded(boolean isValid) {
        if (isValid) {
            mLoadHolder.showLoadSuccess();
        } else {
            mLoadHolder.showLoadFailed();
        }
    }

    /**
     * 开启加载任务
     *
     * @param taskCount 任务数目
     */
    protected void startLoading(int taskCount) {
        mLoadHolder.showLoading();
        mLoadedController.start(taskCount);
    }

    /**
     * 每当有任务完成应调用该方法
     *
     * @param taskKey 任务唯一标识值
     * @param isValid 是否获得有效数据
     */
    protected void loadedOneTask(String taskKey, boolean isValid) {
        mLoadedController.loadedOneTask(taskKey, isValid);
    }

    protected void loadedOneTask(BaseDataSource source, boolean isValid) {
        loadedOneTask(source.toString(), isValid);
    }

    @NonNull
    @Override
    public Context onContext() {
        return mContext;
    }

    /**
     * 网络连接失败
     */
    @Override
    public void onFailNet() {
        mLoadHolder.showLoadFailed();
    }
}
