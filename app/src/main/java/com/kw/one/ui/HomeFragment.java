package com.kw.one.ui;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.kw.arch.aspect.CheckNet;
import com.kw.arch.view.BaseFragment;
import com.kw.one.R;
import com.kw.one.databinding.FragmentHomeBinding;
import com.kw.one.db.DiskMapHelper;
import com.kw.one.source.bean.Bus;
import com.kw.one.source.BusSource;
import com.kw.one.source.CalendarSource;
import com.kw.one.source.WeatherSource;
import com.kw.one.viewmodel.HomeViewModel;

import java.util.Calendar;
import java.util.Date;

import static com.kw.one.db.DiskMapHelper.CITY_KEY;

/**
 * @author Kang Wei
 * @date 2019/7/23
 */
public class HomeFragment extends BaseFragment<HomeViewModel, FragmentHomeBinding> {
    private static final int TASK_COUNT = 3;
    private DiskMapHelper mMapHelper;
    private WeatherSource mWeather;
    private CalendarSource mCalendar;
    private BusSource mBus;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMapHelper = DiskMapHelper.getInstance(mContext);
        mWeather = mViewModel.mWeather;
        mCalendar = mViewModel.mCalendar;
        mBus = mViewModel.mBus;
        startLoading(TASK_COUNT);
        bindUI();
        bindEvent();
    }

    private void bindUI() {
        mWeather.response().observe(getViewLifecycleOwner(), curWeather -> {
            if (curWeather != null) {
                mBinding.weather.setWeather(curWeather);
                loadedOneTask(mWeather, true);
            } else {
                loadedOneTask(mWeather, false);
            }
        });

        mCalendar.response().observe(getViewLifecycleOwner(), calendar -> {
            if (calendar != null) {
                mBinding.calendar.setCalendar(calendar);
                loadedOneTask(mCalendar, true);
            } else {
                loadedOneTask(mCalendar, false);
            }
        });

        mBus.response().observe(getViewLifecycleOwner(), bus -> {
            if (bus != null) {
                mBinding.bus.bus.setText(getBusTime(bus));
                loadedOneTask(mBus, true);
            } else {
                loadedOneTask(mBus, false);
            }
        });

        mMapHelper.getLiveValue(CITY_KEY).observe(getViewLifecycleOwner(), city -> {
            mWeather.request().setValue(city);
        });
        mCalendar.request().setValue(getCurTime());
        mBus.request().setValue(isBus0Time() ? BusSource.bus_125_0_url : BusSource.bus_125_1_url);
    }

    private void bindEvent() {
        mBinding.weather.query.setOnClickListener(v -> {
            String city = mBinding.weather.editQuery.getText().toString();
            mMapHelper.putValue(CITY_KEY, city);
        });

        mBinding.swipeRefresh.setOnRefreshListener(this::onReload);
    }

    @Override
    protected HomeViewModel getVModel() {
        return new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @CheckNet
    @Override
    protected void onReload() {
        startLoading(TASK_COUNT);
        mWeather.onReload(null);
        mCalendar.request().setValue(getCurTime());
        mBus.request().setValue(isBus0Time() ? BusSource.bus_125_0_url : BusSource.bus_125_1_url);
    }

    @Override
    public void onLoaded(boolean isValid) {
        super.onLoaded(isValid);
        mBinding.swipeRefresh.setRefreshing(false);
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
        return getString(isBus0Time() ? R.string.bus_1 : R.string.bus_2, time, remainTime);
    }

    private boolean isBus0Time() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour > 0 && hour < 12;
    }

    private String getCurTime() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis()));
    }
}
