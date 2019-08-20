package com.kw.one.repo;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

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
        if (TextUtils.isEmpty(city)) {
            return null;
        }
        Request request = new Request.Builder().setUrl(BASE_URL + city).setMethod(Request.GET).build();
        Response response = HttpManager.getInstance().mOneHttp.SyncRequest(request);
        if (response.mStatus == Response.ERROR || response.mData == null) {
            return null;
        }
        return ByteArrayConverter.ToObject(response.mData, CurWeather.class);
    }

    @Override
    protected void getAsyncData(@Nullable String city, Consumer<CurWeather> callback) {
        if (TextUtils.isEmpty(city)) {
            callback.accept(null);
            return;
        }
        Request request =
                new Request.Builder().setUrl(BASE_URL + city).setMethod(Request.GET).build();
        HttpManager.getInstance().mOneHttp.AsyncRequest(request, response -> {
            if (response.mStatus == Response.ERROR || response.mData == null) {
                callback.accept(null);
                return;
            }
            callback.accept(ByteArrayConverter.ToObject(response.mData, CurWeather.class));
        });
    }
}
