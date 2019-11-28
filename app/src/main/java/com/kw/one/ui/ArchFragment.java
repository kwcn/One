package com.kw.one.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.kw.arch.aspect.CheckNet;
import com.kw.arch.view.BaseFragment;
import com.kw.one.R;
import com.kw.one.arch.mix.WeatherMixDataSource;
import com.kw.one.arch.myhttp.WeatherHttpSource;
import com.kw.one.arch.retrofit.WeatherRetrofitSource;
import com.kw.one.arch.room.WeatherEntity;
import com.kw.one.arch.room.WeatherRoomSource;
import com.kw.one.databinding.FragmentArchBinding;
import com.kw.one.viewmodel.ArchViewModel;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public class ArchFragment extends BaseFragment<ArchViewModel, FragmentArchBinding> {
    private static final int TASK_COUNT = 4;
    private static final String CITY = "天津市";
    private WeatherHttpSource mHttpSource;
    private WeatherRetrofitSource mRetrofitSource;
    private WeatherRoomSource mRoomSource;
    private WeatherMixDataSource mMixDataSource;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHttpSource = mViewModel.mHttpSource;
        mRetrofitSource = mViewModel.mRetrofitSource;
        mRoomSource = mViewModel.mRoomSource;
        mMixDataSource = mViewModel.mMixDataSource;
        startLoading(TASK_COUNT);
        bindUI();
        request();
        setEvent();
    }

    private void bindUI() {
        mHttpSource.response().observe(getViewLifecycleOwner(), rp -> {
            try {
                mBinding.weather.setText(rp.data.weather);
                loadedOneTask(mHttpSource, true);
            } catch (Exception e) {
                loadedOneTask(mHttpSource, false);
            }
        });

        mRetrofitSource.response().observe(getViewLifecycleOwner(), rp -> {
            try {
                mBinding.weather2.setText(rp.data.weather);
                loadedOneTask(mRetrofitSource, true);
            } catch (Exception e) {
                loadedOneTask(mRetrofitSource, false);
            }
        });

        mRoomSource.response().observe(getViewLifecycleOwner(), rp -> {
            try {
                mBinding.weatherDb.setText(rp.temp);
                loadedOneTask(mRoomSource, true);
            } catch (Exception e) {
                loadedOneTask(mRoomSource, false);
            }
        });

        mMixDataSource.response().observe(getViewLifecycleOwner(), rp -> {
            try {
                mBinding.weatherDbNet.setText(rp.temp);
                loadedOneTask(mMixDataSource, true);
            } catch (Exception e) {
                loadedOneTask(mMixDataSource, false);
            }
        });
    }

    private void setEvent() {
        mBinding.updateDb.setOnClickListener(v -> {
            WeatherEntity entity = new WeatherEntity();
            entity.address = CITY;
            entity.temp = System.currentTimeMillis() + "";
            mRoomSource.getDao().update(entity);
        });

        // 界面上的获取按钮
        mBinding.fetch.setOnClickListener(v -> {
            startLoading(TASK_COUNT);
            request();
        });

        // 下拉刷新
        mBinding.swipeRefresh.setOnRefreshListener(() -> {
            startLoading(TASK_COUNT);
            request();
        });

        setErrorReloadBtnOnClickListener(s -> {
            startLoading(TASK_COUNT);
            request();
        });
    }

    // 设置网络状态检查
    @CheckNet
    private void request() {
        mHttpSource.request().setValue(CITY);
        mRetrofitSource.request().setValue(CITY);
        mRoomSource.request().setValue(CITY);
        mMixDataSource.request().setValue(CITY);
    }

    @Override
    public void onLoaded(boolean isValid) {
        super.onLoaded(isValid);
        // 设置结束下拉刷新
        mBinding.swipeRefresh.setRefreshing(false);
    }

    @Override
    protected ArchViewModel getVModel() {
        return new ViewModelProvider(this).get(ArchViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_arch;
    }
}
