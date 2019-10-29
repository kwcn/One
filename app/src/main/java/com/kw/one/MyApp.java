package com.kw.one;

import android.app.Application;

import com.kw.arch.model.base.Repository;
import com.kw.one.arch.WeatherSource;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Repository.getInstance().appendSource(WeatherSource.class);
    }
}
