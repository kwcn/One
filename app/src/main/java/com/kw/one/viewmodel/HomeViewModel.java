package com.kw.one.viewmodel;

import androidx.lifecycle.ViewModel;

import com.kw.one.repo.BusRepo;
import com.kw.one.repo.CalendarRepo;
import com.kw.one.repo.CurWeatherRepo;
import com.kw.one.repo.base.MutableProvider;
import com.kw.one.repo.base.Provider;
import com.kw.one.repo.bean.Bus;
import com.kw.one.repo.bean.Calendar;
import com.kw.one.repo.bean.CurWeather;

/**
 * @author Kang Wei
 * @date 2019/7/23
 */
public class HomeViewModel extends ViewModel {
    // 天气数据
    public MutableProvider<String, CurWeather> mWeatherProvider;
    // 日历数据
    public Provider<Void, Calendar> mCalendarProvider;
    // 公交车数据
    public MutableProvider<String, Bus> mBusProvider;

    public HomeViewModel() {
        mWeatherProvider = new CurWeatherRepo().getMutableProvider();
        mCalendarProvider = new CalendarRepo().getProvider(null);
        mBusProvider = new BusRepo().getMutableProvider();
    }
}
