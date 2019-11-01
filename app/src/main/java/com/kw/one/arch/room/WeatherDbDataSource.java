package com.kw.one.arch.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.kw.one.db.DBManager;

/**
 * @author Kang Wei
 * @date 2019/10/30
 */
public class WeatherDbDataSource extends IRoomDataSource<String, WeatherEntity, WeatherDao> {
    public WeatherDbDataSource(@NonNull Application application) {
        super(application);
    }

    @NonNull
    @Override
    protected WeatherDao getDao() {
        return DBManager.getInstance(mApplication).mDatabase.mWeatherDao();
    }

    @NonNull
    @Override
    protected LiveData<WeatherEntity> query(@Nullable String request) {
        return mDao.query(request);
    }

    @Override
    public void insert(WeatherEntity weatherEntity) {
        mDao.insert(weatherEntity);
    }

    @Override
    public void delete(WeatherEntity weatherEntity) {
        mDao.delete(weatherEntity);
    }

    @Override
    public void update(WeatherEntity weatherEntity) {
        mDao.update(weatherEntity);
    }
}
