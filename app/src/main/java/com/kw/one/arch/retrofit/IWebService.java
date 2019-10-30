package com.kw.one.arch.retrofit;

import com.kw.one.repo.bean.CurWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author Kang Wei
 * @date 2019/10/30
 */
public interface IWebService {
    @GET("http://www.mxnzp.com/api/weather/current/{city}")
    Call<CurWeather> getWeather(@Path("city") String city);
}
