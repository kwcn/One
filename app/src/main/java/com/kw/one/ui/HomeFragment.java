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
import com.kw.one.repo.BusRepo;
import com.kw.one.repo.bean.Bus;
import com.kw.one.viewmodel.HomeViewModel;

import java.util.Calendar;

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
            mViewModel.mWeatherProvider.setParam(city);
        });

        mBinding.bus.refresh.setOnClickListener(v -> mViewModel.mBusProvider.reload());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.mWeatherProvider.getLiveData().observe(this, mBinding.weather::setWeather);

        mViewModel.mCalendarProvider.getLiveData().observe(this, mBinding.calendar::setCalendar);

        mViewModel.mBusProvider.getLiveData().observe(this, bus ->
                mBinding.bus.bus.setText(HomeFragment.this.getBusTime(bus)));

        mViewModel.mBusProvider.setParam(isBus1Time() ? BusRepo.bus_125_0_url : BusRepo.bus_125_1_url);

        // 获取本地存储的城市内容,且仅载入一次
        OneUtils.transformSingleObserver(mMapHelper.getLiveValue(CITY_KEY)).observe(this, city -> {
            if (!TextUtils.isEmpty(city)) {
                mViewModel.mWeatherProvider.setParam(city);
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

    @NonNull
    private String getBusTime(Bus bus) {
        String time;
        String remainTime = "";
        try {
            time = bus.jsonr.data.buses.get(0).travels.get(0).recommTip;
            long travelTime = bus.jsonr.data.buses.get(0).travels.get(0).travelTime;
            remainTime = "Re:" + travelTime / 60 + "分钟";
        } catch (Exception e) {
            try {
                time = bus.jsonr.data.depDesc;
            } catch (Exception e1) {
                time = getString(R.string.no_data);
            }
        }
        if (TextUtils.isEmpty(time)) {
            time = getString(R.string.no_data);
            remainTime = "";
        }
        return getString(isBus1Time() ? R.string.bus_1 : R.string.bus_2, time, remainTime);
    }

    private boolean isBus1Time() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        return hour > 0 && hour < 12;
    }
}
