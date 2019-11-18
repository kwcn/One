package com.kw.one.source;

import androidx.annotation.NonNull;

import com.kw.arch.model.IRetrofitDataSource;
import com.kw.one.arch.retrofit.WebService;
import com.kw.one.source.bean.CurWeather;

import retrofit2.Call;

/**
 * @author Kang Wei
 * @date 2019/10/30
 */
public class WeatherSource extends IRetrofitDataSource<String, CurWeather> {
    @NonNull
    @Override
    public Call<CurWeather> getCall(String city) {
        return WebService.getInstance().getWeather(city);
    }
}
