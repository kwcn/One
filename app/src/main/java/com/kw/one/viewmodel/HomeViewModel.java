package com.kw.one.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.kw.arch.annotation.Source;
import com.kw.arch.viewmodel.BaseViewModel;
import com.kw.one.source.BusSource;
import com.kw.one.source.CalendarSource;
import com.kw.one.source.WeatherSource;

/**
 * @author Kang Wei
 * @date 2019/7/23
 */
public class HomeViewModel extends BaseViewModel {
    @Source
    public BusSource mBus;
    @Source
    public CalendarSource mCalendar;
    @Source
    public WeatherSource mWeather;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }
}
