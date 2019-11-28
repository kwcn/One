package com.kw.one.arch.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.kw.arch.model.IRoomDataSource;
import com.kw.one.db.DBManager;

/**
 * @author Kang Wei
 * @date 2019/10/30
 */
public class WeatherRoomSource extends IRoomDataSource<String, WeatherEntity, WeatherDao> {
    private WeatherDao mDao;
    public WeatherRoomSource(@NonNull Application application) {
        super(application);
        mDao = DBManager.getInstance(mApplication).mDatabase.mWeatherDao();
    }


    @NonNull
    @Override
    public LiveData<WeatherEntity> query(@Nullable String param) {
        return mDao.query(param);
    }

    @NonNull
    @Override
    public WeatherDao getDao() {
        return mDao;
    }
}
