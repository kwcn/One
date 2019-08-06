package com.kw.one.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kw.one.repo.BusRepo;
import com.kw.one.repo.CalendarRepo;
import com.kw.one.repo.CurWeatherRepo;
import com.kw.one.repo.bean.Bus;
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
    // 公交车数据
    public LiveData<Bus> mBus;
    public LiveData<Bus> mBus2;
    private BusRepo mBusRepo;
    private BusRepo mBusRepo2;

    public HomeViewModel() {
        mCurWeatherRepo = new CurWeatherRepo();
        mCurWeather = mCurWeatherRepo.getStableLiveData();

        mCalendarRepo = new CalendarRepo();
        mCalendar = mCalendarRepo.getLiveData();

        mBusRepo = new BusRepo();
        mBus = mBusRepo.getLiveData("https://web.chelaile.net.cn/api/bus/line!lineDetail.action?s=h5&wxs=wx_app&src=weixinapp_cx&sign=1&v=3.8.56&from=NO_FROM&cityId=006&geo_lat=39.0765&lat=39.0765&geo_lng=117.501028&lng=117.501028&gpstype=wgs&unionId=oSpTTjrSAzc75fSNjRzqkEVeSs8g&userId=okBHq0I1Ltr9YREGmI5rdTD5GESk&h5Id=okBHq0I1Ltr9YREGmI5rdTD5GESk&targetOrder=7&lineId=022-125-0");

        mBusRepo2 = new BusRepo();
        mBus2 = mBusRepo2.getLiveData("https://web.chelaile.net.cn/api/bus/line!lineDetail.action?s=h5&wxs=wx_app&src=weixinapp_cx&sign=1&v=3.8.56&from=NO_FROM&cityId=006&geo_lat=39.076469&lat=39.076469&geo_lng=117.501043&lng=117.501043&gpstype=wgs&unionId=oSpTTjrSAzc75fSNjRzqkEVeSs8g&userId=okBHq0I1Ltr9YREGmI5rdTD5GESk&h5Id=okBHq0I1Ltr9YREGmI5rdTD5GESk&targetOrder=8&lineId=002247655200");
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

    public void reloadBus() {
        mBusRepo.reload();
    }

    public void reloadBus2() {
        mBusRepo2.reload();
    }
}
