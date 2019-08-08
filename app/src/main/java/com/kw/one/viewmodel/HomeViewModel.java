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
    public Provider<String, Bus> mBusProvider;
    public Provider<String, Bus> mBusProvider2;

    public HomeViewModel() {
        mWeatherProvider = new CurWeatherRepo().getMutableProvider();
        mCalendarProvider = new CalendarRepo().getProvider(null);
        BusRepo busRepo = new BusRepo();
        mBusProvider = busRepo.getProvider("https://web.chelaile.net.cn/api/bus/line!lineDetail.action?s=h5&wxs=wx_app&src=weixinapp_cx&sign=1&v=3.8.56&from=NO_FROM&cityId=006&geo_lat=39.0765&lat=39.0765&geo_lng=117.501028&lng=117.501028&gpstype=wgs&unionId=oSpTTjrSAzc75fSNjRzqkEVeSs8g&userId=okBHq0I1Ltr9YREGmI5rdTD5GESk&h5Id=okBHq0I1Ltr9YREGmI5rdTD5GESk&targetOrder=7&lineId=022-125-0");
        mBusProvider2 = busRepo.getProvider("https://web.chelaile.net.cn/api/bus/line!lineDetail.action?s=h5&wxs=wx_app&src=weixinapp_cx&sign=1&v=3.8.56&from=NO_FROM&cityId=006&geo_lat=39.076469&lat=39.076469&geo_lng=117.501043&lng=117.501043&gpstype=wgs&unionId=oSpTTjrSAzc75fSNjRzqkEVeSs8g&userId=okBHq0I1Ltr9YREGmI5rdTD5GESk&h5Id=okBHq0I1Ltr9YREGmI5rdTD5GESk&targetOrder=8&lineId=002247655200");
    }
}
