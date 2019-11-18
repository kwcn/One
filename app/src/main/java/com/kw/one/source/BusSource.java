package com.kw.one.source;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.google.gson.Gson;
import com.kw.arch.model.BaseDataSource;
import com.kw.one.arch.retrofit.WebService;
import com.kw.one.source.bean.Bus;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Kang Wei
 * @date 2019/11/16
 */
public class BusSource extends BaseDataSource<String, Bus> {
    // 125外环
    public static final String bus_125_0_url = "https://web.chelaile.net" +
            ".cn/api/bus/line!lineDetail.action?s=h5&wxs=wx_app&src=weixinapp_cx&sign=1&v=3.8" +
            ".56&from=NO_FROM&cityId=006&geo_lat=39.0765&lat=39.0765&geo_lng=117.501028&lng=117" +
            ".501028&gpstype=wgs&unionId=oSpTTjrSAzc75fSNjRzqkEVeSs8g&userId" +
            "=okBHq0I1Ltr9YREGmI5rdTD5GESk&h5Id=okBHq0I1Ltr9YREGmI5rdTD5GESk&targetOrder=7&lineId" +
            "=022-125-0";
    // 125内环
    public static final String bus_125_1_url = "https://web.chelaile.net" +
            ".cn/api/bus/line!lineDetail.action?s=h5&wxs=wx_app&src=weixinapp_cx&sign=1&v=3.8" +
            ".56&from=NO_FROM&cityId=006&geo_lat=39.076469&lat=39.076469&geo_lng=117" +
            ".501043&lng=117.501043&gpstype=wgs&unionId=oSpTTjrSAzc75fSNjRzqkEVeSs8g&userId" +
            "=okBHq0I1Ltr9YREGmI5rdTD5GESk&h5Id=okBHq0I1Ltr9YREGmI5rdTD5GESk&targetOrder=8&lineId" +
            "=002247655200";

    @Override
    protected void fetchData(@Nullable String request, @NonNull Consumer<Bus> response) {
        WebService.getInstance().getBus(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> inResponse) {
                if (inResponse.isSuccessful()) {
                    response.accept(amendResponseBody(inResponse.body()));
                } else {
                    response.accept(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                response.accept(null);
            }
        });
    }

    /**
     * 去掉返回数据带有的YGKJ##前后缀
     *
     * @param body
     * @return
     */
    @Nullable
    private Bus amendResponseBody(@Nullable ResponseBody body) {
        if (body == null) {
            return null;
        }
        String response;
        try {
            response = body.string();
            if (response.length() <= 12) {
                return null;
            }
            response = response.substring(6, response.length() - 6);
            return new Gson().fromJson(response, Bus.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
