package com.kw.one.repo;

import android.text.TextUtils;
import android.util.ArrayMap;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.kw.one.net.ByteArrayConverter;
import com.kw.one.net.HttpManager;
import com.kw.one.net.Request;
import com.kw.one.net.Response;
import com.kw.one.repo.base.Repo;
import com.kw.one.repo.bean.Bus;

import java.util.Map;

/**
 * @author Kang Wei
 * @date 2019/8/8
 */
public class BusRepo extends Repo<String, Bus> {
    // 125外环
    public static final String bus_125_0_url = "https://web.chelaile.net.cn/api/bus/line!lineDetail.action?s=h5&wxs=wx_app&src=weixinapp_cx&sign=1&v=3.8.56&from=NO_FROM&cityId=006&geo_lat=39.0765&lat=39.0765&geo_lng=117.501028&lng=117.501028&gpstype=wgs&unionId=oSpTTjrSAzc75fSNjRzqkEVeSs8g&userId=okBHq0I1Ltr9YREGmI5rdTD5GESk&h5Id=okBHq0I1Ltr9YREGmI5rdTD5GESk&targetOrder=7&lineId=022-125-0";
    // 125内环
    public static final String bus_125_1_url = "https://web.chelaile.net.cn/api/bus/line!lineDetail.action?s=h5&wxs=wx_app&src=weixinapp_cx&sign=1&v=3.8.56&from=NO_FROM&cityId=006&geo_lat=39.076469&lat=39.076469&geo_lng=117.501043&lng=117.501043&gpstype=wgs&unionId=oSpTTjrSAzc75fSNjRzqkEVeSs8g&userId=okBHq0I1Ltr9YREGmI5rdTD5GESk&h5Id=okBHq0I1Ltr9YREGmI5rdTD5GESk&targetOrder=8&lineId=002247655200";

    @Override
    protected Bus getSyncData(@Nullable String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        Request request = new Request.Builder().setUrl(url).setMethod(Request.GET).setHeader(getDefaultHeader()).build();
        Response response = HttpManager.getInstance().mOneHttp.SyncRequest(request);
        if (response.mStatus == Response.ERROR || response.mData == null) {
            return null;
        }
        byte[] newBytes = amendByteArray(response.mData);
        if (newBytes == null) {
            return null;
        }
        return ByteArrayConverter.ToObject(newBytes, Bus.class);
    }

    @Override
    protected void getAsyncData(@Nullable String url, Consumer<Bus> callback) {
        if (TextUtils.isEmpty(url)) {
            callback.accept(null);
            return;
        }
        Request request =
                new Request.Builder().setUrl(url).setMethod(Request.GET).setHeader(getDefaultHeader()).build();
        HttpManager.getInstance().mOneHttp.AsyncRequest(request, response -> {
            if (response.mStatus == Response.ERROR || response.mData == null) {
                callback.accept(null);
                return;
            }
            byte[] newBytes = amendByteArray(response.mData);
            if (newBytes == null) {
                callback.accept(null);
                return;
            }
            callback.accept(ByteArrayConverter.ToObject(newBytes, Bus.class));
        });
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

    private Map<String, String> getDefaultHeader() {
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
