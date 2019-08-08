package com.kw.one.repo;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;
import androidx.lifecycle.ComputableLiveData;

import com.kw.one.net.ByteArrayConverter;
import com.kw.one.net.HttpManager;
import com.kw.one.net.Request;
import com.kw.one.net.Response;
import com.kw.one.repo.base.Repo;
import com.kw.one.repo.bean.CurWeather;

/**
 * @author Kang Wei
 * @date 2019/7/23
 */
@SuppressLint("RestrictedApi")
public class CurWeatherRepo extends Repo<String, CurWeather> {
    private static final String BASE_URL = "http://www.mxnzp.com/api/weather/current/";

    @Override
    protected CurWeather getSyncData(@Nullable String city) {
        Request request = new Request.Builder().setUrl(BASE_URL + city).setMethod(Request.GET).build();
        Response response = HttpManager.getInstance().mOneHttp.SyncRequest(request);
        if (response.mStatus == Response.ERROR) {
            return null;
        }
        return ByteArrayConverter.ToObject(response.mData, CurWeather.class);
    }

    @Override
    protected ComputableLiveData<CurWeather> getAsyncData(@Nullable String city) {
        return new ComputableLiveData<CurWeather>() {
            @Override
            protected CurWeather compute() {
                return getSyncData(city);
            }
        };
    }
}
