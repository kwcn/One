package com.kw.one.arch.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Kang Wei
 * @date 2019/10/30
 */
public class WebService {
    public IWebService mWebService;

    public static IWebService getInstance() {
        return InWebService.inWebService.mWebService;
    }

    private static class InWebService {
        private static WebService inWebService = new WebService();
    }

    private WebService() {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://www.baidu.com").
                addConverterFactory(GsonConverterFactory.create()).build();
        mWebService = retrofit.create(IWebService.class);
    }
}
