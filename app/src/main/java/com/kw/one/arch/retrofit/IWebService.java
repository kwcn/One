package com.kw.one.arch.retrofit;

import com.kw.one.arch.recyclerview.ArticleWrapperBean;
import com.kw.one.source.bean.Calendar;
import com.kw.one.source.bean.CurWeather;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * @author Kang Wei
 * @date 2019/10/30
 */
public interface IWebService {
    @GET("http://www.mxnzp.com/api/weather/current/{city}")
    Call<CurWeather> getWeather(@Path("city") String city);

    @GET("https://www.wanandroid.com/article/list/0/json")
    Call<ArticleWrapperBean> getArticleWrapperBean();

    @GET("http://www.mxnzp.com/api/holiday/single/{date}")
    Call<Calendar> getCalendar(@Path("date") String date);

    @Headers({"referer:https://servicewechat.com/wx71d589ea01ce3321/250/page-frame.html", "User" +
            "-Agent:Mozilla/5.0 (Linux; Android 8.0.0; SM-G9350 Build/R16NW; wv) AppleWebKit/537" +
            ".36 (KHTML, like Gecko) Version/4.0 Chrome/69.0.3497.100 Mobile Safari/537.36 " +
            "MicroMessenger/6.7.3.1360(0x26070333) NetType/WIFI Language/zh_CN Process/appbrand0"})
    @GET
    Call<ResponseBody> getBus(@Url String url);
}
