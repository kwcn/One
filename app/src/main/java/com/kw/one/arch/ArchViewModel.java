package com.kw.one.arch;

import android.app.Application;

import androidx.annotation.NonNull;

import com.kw.arch.annotation.Source;
import com.kw.arch.viewmodel.BaseViewModel;
import com.kw.one.arch.mix.WeatherMixDataSource;
import com.kw.one.arch.myhttp.WeatherHttpSource;
import com.kw.one.arch.retrofit.WeatherRetrofitSource;
import com.kw.one.arch.room.WeatherRoomSource;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public class ArchViewModel extends BaseViewModel {
    @Source
    public WeatherHttpSource mHttpSource;
    @Source
    public WeatherRetrofitSource mRetrofitSource;
    @Source
    public WeatherRoomSource mRoomSource;
    @Source
    public WeatherMixDataSource mMixDataSource;

    public ArchViewModel(@NonNull Application application) {
        super(application);
    }
}
