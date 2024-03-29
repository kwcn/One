package com.kw.one.arch.retrofit;

import androidx.annotation.NonNull;

import com.kw.arch.model.IRetrofitDataSource;
import com.kw.one.source.bean.CurWeather;

import retrofit2.Call;

/**
 * @author Kang Wei
 * @date 2019/10/30
 */
public class WeatherRetrofitSource extends IRetrofitDataSource<String, CurWeather> {
    @NonNull
    @Override
    public Call<CurWeather> getCall(String request) {
        return WebService.getInstance().getWeather(request);
    }
}
