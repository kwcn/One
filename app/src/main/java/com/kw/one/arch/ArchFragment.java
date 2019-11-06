package com.kw.one.arch;

import android.os.Bundle;
import android.util.Log;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.mWeather.response().observe(this, rp -> {
            if (rp == null) return;
            mBinding.weather.setText(rp.data.weather);
        });
        mViewModel.mWeather.request().setValue("天津");

        mViewModel.mRetrofitWeather.response().observe(this, rp -> {
            if (rp == null) return;
            mBinding.weather2.setText(rp.data.weather);
        });
        mViewModel.mRetrofitWeather.request().setValue("天津");

        mViewModel.mWeatherDbDataSource.response().observe(this, rp -> {
            if (rp == null) return;
            mBinding.weatherDb.setText(rp.temp);
            Log.i("kwdy", "mWeatherDbDataSource: " + rp.temp);
        });
        mViewModel.mWeatherDbDataSource.request().setValue("天津");

        mBinding.fetch.setOnClickListener(v -> {
            mViewModel.mWeatherDbDataSource.request().setValue("天津");
        });

        mBinding.updateDb.setOnClickListener(v -> {
            WeatherEntity entity = new WeatherEntity();
            entity.address = "天津";
            entity.temp = System.currentTimeMillis() + "";
            mViewModel.mWeatherDbDataSource.update(entity);
        });

        mViewModel.mWeatherMixDataSource.response().observe(this, rp -> {
            if (rp == null) return;
            mBinding.weatherDbNet.setText(rp.temp);
            Log.i("kwdy", "mWeatherMixDataSource: " + rp.temp);
        });

        mViewModel.mWeatherMixDataSource.request().setValue("天津");
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
