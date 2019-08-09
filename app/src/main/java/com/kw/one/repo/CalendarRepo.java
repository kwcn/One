package com.kw.one.repo;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import com.kw.one.net.ByteArrayConverter;
import com.kw.one.net.HttpManager;
import com.kw.one.net.Request;
import com.kw.one.net.Response;
import com.kw.one.repo.base.Repo;
import com.kw.one.repo.bean.Calendar;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Kang Wei
 * @date 2019/7/24
 */
@SuppressLint("RestrictedApi")
public class CalendarRepo extends Repo<Void, Calendar> {
    private static final String BASE_URL = "http://www.mxnzp.com/api/holiday/single/";
    @Override
    protected Calendar getSyncData(@Nullable Void aVoid) {
        String curDate = new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis()));
        Request request = new Request.Builder().setUrl(BASE_URL + curDate).setMethod(Request.GET).build();
        Response response = HttpManager.getInstance().mOneHttp.SyncRequest(request);
        if (response.mStatus == Response.ERROR) {
            return null;
        }
        return ByteArrayConverter.ToObject(response.mData, Calendar.class);
    }
}
