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
public class WeatherDbDataSource extends IRoomDataSource<String, WeatherEntity> {
    private WeatherDao mDao;
    public WeatherDbDataSource(@NonNull Application application) {
        super(application);
        mDao = DBManager.getInstance(mApplication).mDatabase.mWeatherDao();
    }


    @NonNull
    @Override
    public LiveData<WeatherEntity> query(@Nullable String param) {
        return mDao.query(param);
    }

    @Override
    public void insert(@NonNull WeatherEntity weatherEntity) {
        mDao.insert(weatherEntity);
    }

    @Override
    public void delete(@NonNull WeatherEntity weatherEntity) {
        mDao.delete(weatherEntity);
    }

    @Override
    public void update(@NonNull WeatherEntity weatherEntity) {
        mDao.update(weatherEntity);
    }
}
