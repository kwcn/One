package com.kw.one.source;

import androidx.annotation.NonNull;

import com.kw.arch.annotation.Singleton;
import com.kw.arch.model.IRetrofitDataSource;
import com.kw.one.arch.retrofit.WebService;
import com.kw.one.source.bean.CurWeather;

import retrofit2.Call;

/**
 * 设置成单例，所有界面共用一个DataSource
 * @author Kang Wei
 * @date 2019/10/30
 */
@Singleton
public class WeatherSource extends IRetrofitDataSource<String, CurWeather> {
    @NonNull
    @Override
    public Call<CurWeather> getCall(String city) {
        return WebService.getInstance().getWeather(city);
    }
}
