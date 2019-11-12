package com.kw.one.arch;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

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
        startRefresh(TASK_COUNT);
        bindUI();
        setEvent();
    }

    private void bindUI() {
        // 普通http访问
        mViewModel.mWeather.response().observe(this, rp -> {
            try {
                mBinding.weather.setText(rp.data.weather);
            } finally {
                cutRefreshTask(mViewModel.mWeather.toString());
            }
        });
        mViewModel.mWeather.request().setValue("天津");
        // Retrofit访问
        mViewModel.mRetrofitWeather.response().observe(this, rp -> {
            if (rp == null) return;
            mBinding.weather2.setText(rp.data.weather);
            cutRefreshTask(mViewModel.mRetrofitWeather.toString());
        });
        mViewModel.mRetrofitWeather.request().setValue("天津");
        // DB访问
        mViewModel.mWeatherDbDataSource.response().observe(this, rp -> {
            if (rp == null) return;
            mBinding.weatherDb.setText(rp.temp);
            cutRefreshTask(mViewModel.mWeatherDbDataSource.toString());
        });
        mViewModel.mWeatherDbDataSource.request().setValue("天津");
        // DB+Net访问
        mViewModel.mWeatherMixDataSource.response().observe(this, rp -> {
            if (rp == null) return;
            mBinding.weatherDbNet.setText(rp.temp);
            cutRefreshTask(mViewModel.mWeatherMixDataSource.toString());
        });
        mViewModel.mWeatherMixDataSource.request().setValue("天津");
    }

    private void setEvent() {
        mBinding.fetch.setOnClickListener(v -> {
            startRefresh(TASK_COUNT);
            mViewModel.mWeather.onReload(null);
            mViewModel.mRetrofitWeather.onReload(null);
            mViewModel.mWeatherDbDataSource.onReload(null);
            mViewModel.mWeatherMixDataSource.onReload(null);
        });

        mBinding.updateDb.setOnClickListener(v -> {
            WeatherEntity entity = new WeatherEntity();
            entity.address = "天津";
            entity.temp = System.currentTimeMillis() + "";
            mViewModel.mWeatherDbDataSource.update(entity);
        });

        mBinding.swipeRefresh.setOnRefreshListener(() -> {
            mRefreshWorker.start(TASK_COUNT);
            mViewModel.mWeather.onReload(null);
            mViewModel.mRetrofitWeather.onReload(null);
            mViewModel.mWeatherDbDataSource.onReload(null);
            mViewModel.mWeatherMixDataSource.onReload(null);
        });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
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
