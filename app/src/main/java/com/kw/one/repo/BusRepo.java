package com.kw.one.repo;

import android.annotation.SuppressLint;
import android.util.ArrayMap;

import androidx.annotation.Nullable;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;

import com.kw.one.net.ByteArrayConverter;
import com.kw.one.net.HttpManager;
import com.kw.one.net.Request;
import com.kw.one.net.Response;
import com.kw.one.repo.base.StableRepo;
import com.kw.one.repo.bean.Bus;

import java.util.Map;

/**
 * @author Kang Wei
 * @date 2019/8/6
 */
@SuppressLint("RestrictedApi")
public class BusRepo extends StableRepo<String, Bus> {
    private ComputableLiveData<Bus> mLiveData;

    @Override
    public LiveData<Bus> getLiveData(String url) {
        mLiveData = new ComputableLiveData<Bus>() {
            @Override
            protected Bus compute() {
                return getSyncData(url);
            }
        };
        return mLiveData.getLiveData();
    }

    @Override
    public Bus getSyncData(String url) {
        Request request = new Request.Builder().setUrl(url).setMethod(Request.GET).setHeader(getDefaultHeader()).build();
        Response response = HttpManager.getInstance().mOneHttp.SyncRequest(request);
        if (response.mStatus == Response.ERROR) {
            return null;
        }
        byte[] newBytes = amendByteArray(response.mData);
        if (newBytes == null) {
            return null;
        }
        return ByteArrayConverter.ToObject(newBytes, Bus.class);
    }

    /**
     * 去掉返回数据带有的YGKJ##前后缀
     *
     * @param data
     * @return
     */
    @Nullable
    private byte[] amendByteArray(byte[] data) {
        if (data.length - 12 < 0) {
            return null;
        }
        byte[] newBytes = new byte[data.length - 12];
        System.arraycopy(data, 6, newBytes, 0, data.length - 12);
        return newBytes;
    }


    @Override
    public void reload() {
        if (mLiveData != null) {
            mLiveData.invalidate();
        }
    }

    public Map<String, String> getDefaultHeader() {
        Map<String, String> map = new ArrayMap<>();
        map.put("charset", "utf-8");
        map.put("Accept-Encoding", "gzip");
        map.put("referer", "https://servicewechat.com/wx71d589ea01ce3321/250/page-frame.html");
        map.put("content-type", " text");
        map.put("User-Agent", "Mozilla/5.0 (Linux; Android 8.0.0; SM-G9350 Build/R16NW; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/69.0.3497.100 Mobile Safari/537.36 MicroMessenger/6.7.3.1360(0x26070333) NetType/WIFI Language/zh_CN Process/appbrand0");
        map.put("Host", "web.chelaile.net.cn");
        map.put("Connection", "Keep-Alive");
        return map;
    }
}
