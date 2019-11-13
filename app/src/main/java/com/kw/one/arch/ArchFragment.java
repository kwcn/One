package com.kw.one.arch;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.kw.arch.aspect.CheckNet;
import com.kw.arch.view.BaseFragment;
import com.kw.one.R;
import com.kw.one.arch.room.WeatherEntity;
import com.kw.one.databinding.FragmentArchBinding;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public class ArchFragment extends BaseFragment<ArchViewModel, FragmentArchBinding> {
    private static final int TASK_COUNT = 4;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startLoading(TASK_COUNT);
        bindUI();
        request();
        setEvent();
    }

    private void bindUI() {
        // 普通http
        mViewModel.mWeather.response().observe(this, rp -> {
            try {
                mBinding.weather.setText(rp.data.weather);
                loadedOneTask(mViewModel.mWeather, true);
            } catch (Exception e) {
                loadedOneTask(mViewModel.mWeather, true);
            }
        });

        // Retrofit
        mViewModel.mRetrofitWeather.response().observe(this, rp -> {
            try {
                mBinding.weather2.setText(rp.data.weather);
                loadedOneTask(mViewModel.mRetrofitWeather, true);
            } catch (Exception e) {
                loadedOneTask(mViewModel.mRetrofitWeather, false);
            }
        });

        // DB访问
        mViewModel.mWeatherDbDataSource.response().observe(this, rp -> {
            try {
                mBinding.weatherDb.setText(rp.temp);
                loadedOneTask(mViewModel.mWeatherDbDataSource, true);
            } catch (Exception e) {
                loadedOneTask(mViewModel.mWeatherDbDataSource, false);
            }
        });

        // DB+Net访问
        mViewModel.mWeatherMixDataSource.response().observe(this, rp -> {
            try {
                mBinding.weatherDbNet.setText(rp.temp);
                loadedOneTask(mViewModel.mWeatherMixDataSource, true);
            } catch (Exception e) {
                loadedOneTask(mViewModel.mWeatherMixDataSource, false);
            }
        });
    }

    private void setEvent() {
        mBinding.fetch.setOnClickListener(v -> {
            startLoading(TASK_COUNT);
            request();
        });

        mBinding.updateDb.setOnClickListener(v -> {
            WeatherEntity entity = new WeatherEntity();
            entity.address = "天津";
            entity.temp = System.currentTimeMillis() + "";
            mViewModel.mWeatherDbDataSource.update(entity);
        });

        mBinding.swipeRefresh.setOnRefreshListener(() -> {
            // 不显示刷新界面，只显示下拉刷新进度条
            mLoadedController.start(TASK_COUNT);
            request();
        });

        // 无效数据，重试按钮
        mLoadHolder.withRetry(status -> {
            startLoading(TASK_COUNT);
            request();
        });
    }

    @CheckNet
    private void request() {
        mViewModel.mWeather.request().setValue("天津");
        mViewModel.mRetrofitWeather.request().setValue("天津");
        mViewModel.mWeatherDbDataSource.request().setValue("天津");
        mViewModel.mWeatherMixDataSource.request().setValue("天津");
    }

    @Override
    public void onLoaded(boolean isValid) {
        super.onLoaded(isValid);
        mBinding.swipeRefresh.setRefreshing(false);
    }

    @Override
    protected ArchViewModel getVModel() {
        return ViewModelProviders.of(this).get(ArchViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_arch;
    }
}
