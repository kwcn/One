package com.kw.one.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.kw.one.OneUtils;
import com.kw.one.R;
import com.kw.one.databinding.FragmentHomeBinding;
import com.kw.one.db.DiskMapHelper;
import com.kw.one.viewmodel.HomeViewModel;

import static com.kw.one.db.DiskMapHelper.CITY_KEY;

/**
 * @author Kang Wei
 * @date 2019/7/23
 */
public class HomeFragment extends BaseFragment<HomeViewModel, FragmentHomeBinding> {
    private DiskMapHelper mMapHelper;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mMapHelper = DiskMapHelper.getInstance(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.weather.query.setOnClickListener(v -> {
            String city = mBinding.weather.editQuery.getText().toString();
            // 记录用户搜索历史
            mMapHelper.putValue(CITY_KEY, city);
            mViewModel.setCity(city);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.mCurWeather.observe(this, curWeather -> {
            mBinding.weather.setWeather(curWeather);
        });

        mViewModel.mCalendar.observe(this, calendar -> {
            mBinding.calendar.setCalendar(calendar);
        });

        // 获取本地存储的城市内容,且仅载入一次
        OneUtils.transformSingleObserver(mMapHelper.getLiveValue(CITY_KEY)).observe(this, city -> {
            if (!TextUtils.isEmpty(city)) {
                mViewModel.setCity(city);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Nullable
    @Override
    protected HomeViewModel getViewModel() {
        return ViewModelProviders.of(this).get(HomeViewModel.class);
    }
}
