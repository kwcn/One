package com.kw.one.arch.myhttp;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.kw.arch.model.INetDataSource;
import com.kw.one.net.ByteArrayConverter;
import com.kw.one.net.HttpManager;
import com.kw.one.net.Request;
import com.kw.one.net.Response;
import com.kw.one.repo.bean.CurWeather;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public class WeatherSource extends INetDataSource<String, CurWeather> {
    private static final String BASE_URL = "http://www.mxnzp.com/api/weather/current/";

    @Override
    public void getNetData(@Nullable String city, @Nullable Consumer<CurWeather> callback) {
        if (callback == null) return;
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
