package com.kw.one.repo;

import android.annotation.SuppressLint;

import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;

import com.kw.one.net.ByteArrayConverter;
import com.kw.one.net.HttpManager;
import com.kw.one.net.OneHttp;
import com.kw.one.net.Request;
import com.kw.one.net.Response;
import com.kw.one.repo.base.StableRepo;
import com.kw.one.repo.bean.CurWeather;

/**
 * @author Kang Wei
 * @date 2019/7/23
 */
@SuppressLint("RestrictedApi")
public class CurWeatherRepo extends StableRepo<String, CurWeather> {
    private static final String BASE_URL = "http://www.mxnzp.com/api/weather/current/";
    private OneHttp mHttp;
    private ComputableLiveData<CurWeather> mLiveData;

    public CurWeatherRepo() {
        mHttp = HttpManager.getInstance().mOneHttp;
    }

    @Override
    public LiveData<CurWeather> getLiveData(String city) {
        mLiveData = new ComputableLiveData<CurWeather>() {
            @Override
            protected CurWeather compute() {
                return getSyncData(city);
            }
        };
        return mLiveData.getLiveData();
    }

    @Override
    public CurWeather getSyncData(String city) {
        Response response = mHttp.SyncRequest(new Request(Request.GET, BASE_URL + city));
        if (response.mStatus == Response.ERROR) {
            return null;
        }
        return ByteArrayConverter.ToObject(response.mData, CurWeather.class);
    }

    @Override
    public void reload() {
        if (mLiveData != null) {
            mLiveData.invalidate();
        }
    }
}
