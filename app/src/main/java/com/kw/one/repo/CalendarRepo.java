package com.kw.one.repo;

import android.annotation.SuppressLint;

import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;

import com.kw.one.net.ByteArrayConverter;
import com.kw.one.net.HttpManager;
import com.kw.one.net.Request;
import com.kw.one.net.Response;
import com.kw.one.repo.base.NoParamsRepo;
import com.kw.one.repo.bean.Calendar;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Kang Wei
 * @date 2019/7/24
 */
@SuppressLint("RestrictedApi")
public class CalendarRepo extends NoParamsRepo<Calendar> {
    private static final String BASE_URL = "http://www.mxnzp.com/api/holiday/single/";
    private ComputableLiveData<Calendar> mLiveData;

    @Override
    public LiveData<Calendar> getLiveData() {
        mLiveData = new ComputableLiveData<Calendar>() {
            @Override
            protected Calendar compute() {
                return getSyncData();
            }
        };
        return mLiveData.getLiveData();
    }

    @Override
    public Calendar getSyncData() {
        String curDate = new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis()));
        Request request = new Request.Builder().setUrl(BASE_URL + curDate).setMethod(Request.GET).build();
        Response response = HttpManager.getInstance().mOneHttp.SyncRequest(request);
        if (response.mStatus == Response.ERROR) {
            return null;
        }
        return ByteArrayConverter.ToObject(response.mData, Calendar.class);
    }

    @Override
    public void reload() {
        if (mLiveData != null) {
            mLiveData.invalidate();
        }
    }
}
