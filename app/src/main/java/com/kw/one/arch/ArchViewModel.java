package com.kw.one.arch;

import com.kw.arch.annotation.Source;
import com.kw.arch.viewmodel.BaseViewModel;
import com.kw.one.arch.myhttp.WeatherSource;
import com.kw.one.arch.retrofit.WeatherRetrofitSource;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public class ArchViewModel extends BaseViewModel {
    @Source
    public WeatherSource mWeather;
    @Source
    public WeatherRetrofitSource mRetrofitWeather;
}
