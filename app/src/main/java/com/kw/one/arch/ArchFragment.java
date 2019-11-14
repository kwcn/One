package com.kw.one.arch;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.kw.arch.aspect.CheckNet;
import com.kw.arch.view.BaseFragment;
import com.kw.one.R;
import com.kw.one.arch.mix.WeatherMixDataSource;
import com.kw.one.arch.myhttp.WeatherHttpSource;
import com.kw.one.arch.retrofit.WeatherRetrofitSource;
import com.kw.one.arch.room.WeatherEntity;
import com.kw.one.arch.room.WeatherRoomSource;
import com.kw.one.databinding.FragmentArchBinding;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public class ArchFragment extends BaseFragment<ArchViewModel, FragmentArchBinding> {
    private static final int TASK_COUNT = 4;
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
        mHttpSource.response().observe(this, rp -> {
            try {
                mBinding.weather.setText(rp.data.weather);
                loadedOneTask(mHttpSource, true);
            } catch (Exception e) {
                loadedOneTask(mHttpSource, false);
            }
        });

        mRetrofitSource.response().observe(this, rp -> {
            try {
                mBinding.weather2.setText(rp.data.weather);
                loadedOneTask(mRetrofitSource, true);
            } catch (Exception e) {
                loadedOneTask(mRetrofitSource, false);
            }
        });

        mRoomSource.response().observe(this, rp -> {
            try {
                mBinding.weatherDb.setText(rp.temp);
                loadedOneTask(mRoomSource, true);
            } catch (Exception e) {
                loadedOneTask(mRoomSource, false);
            }
        });

        mMixDataSource.response().observe(this, rp -> {
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
            entity.address = "天津";
            entity.temp = System.currentTimeMillis() + "";
            mRoomSource.update(entity);
        });

        mBinding.fetch.setOnClickListener(v -> {
            startLoading(TASK_COUNT);
            request();
        });

        mBinding.swipeRefresh.setOnRefreshListener(() -> {
            // 不显示刷新界面，只显示下拉刷新进度条
            mLoadedController.start(TASK_COUNT);
            request();
        });

        // 重试按钮
        mLoadHolder.withRetry(status -> {
            startLoading(TASK_COUNT);
            request();
        });
    }

    // 设置网络状态检查
    @CheckNet
    private void request() {
        mHttpSource.request().setValue("天津");
        mRetrofitSource.request().setValue("天津");
        mRoomSource.request().setValue("天津");
        mMixDataSource.request().setValue("天津");
    }

    @Override
    public void onLoaded(boolean isValid) {
        super.onLoaded(isValid);
        // 设置下拉刷新
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
