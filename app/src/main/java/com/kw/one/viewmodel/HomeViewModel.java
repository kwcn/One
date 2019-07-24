package com.kw.one.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kw.one.repo.CalendarRepo;
import com.kw.one.repo.CurWeatherRepo;
import com.kw.one.repo.bean.Calendar;
import com.kw.one.repo.bean.CurWeather;

/**
 * @author Kang Wei
 * @date 2019/7/23
 */
public class HomeViewModel extends ViewModel {
    // 天气数据
    public LiveData<CurWeather> mCurWeather;
    private CurWeatherRepo mCurWeatherRepo;
    // 日历数据
    public LiveData<Calendar> mCalendar;
    private CalendarRepo mCalendarRepo;

    public HomeViewModel() {
        mCurWeatherRepo = new CurWeatherRepo();
        mCurWeather = mCurWeatherRepo.getStableLiveData();
        mCalendarRepo = new CalendarRepo();
        mCalendar = mCalendarRepo.getLiveData();

    }

    public void setCity(String city) {
        mCurWeatherRepo.setParams(city);
    }

    public void reloadWeather() {
        mCurWeatherRepo.reload();
    }

    public void reloadCalendar() {
        mCalendarRepo.reload();
    }
}
