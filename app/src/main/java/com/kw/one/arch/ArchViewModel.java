package com.kw.one.arch;

import android.util.Log;

import com.kw.arch.annotation.Source;
import com.kw.arch.viewmodel.BaseViewModel;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public class ArchViewModel extends BaseViewModel {
    @Source
    public WeatherSource mWeather;
}
