package com.kw.one.arch.mix;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.kw.arch.model.IRetrofitAndRoomDataSource;
import com.kw.one.arch.retrofit.WebService;
import com.kw.one.arch.room.WeatherDao;
import com.kw.one.arch.room.WeatherEntity;
import com.kw.one.db.DBManager;
import com.kw.one.source.bean.CurWeather;

import retrofit2.Call;

/**
 * @author Kang Wei
 * @date 2019/11/2
 */
public class WeatherMixDataSource extends IRetrofitAndRoomDataSource<String, CurWeather,
        WeatherEntity, WeatherDao> {
    private WeatherDao mDao;

    public WeatherMixDataSource(@NonNull Application application) {
        super(application);
        mDao = DBManager.getInstance(mApplication).mDatabase.mWeatherDao();
    }

    @NonNull
    @Override
    public Call<CurWeather> getCall(@Nullable String request) {
        return WebService.getInstance().getWeather(request);
    }

    @Override
    protected boolean isUpdateFromNet(@Nullable String request) {
        return true;
    }

    @NonNull
    @Override
    protected WeatherEntity transformNetToDbData(@NonNull CurWeather netData) {
        WeatherEntity entity = new WeatherEntity();
        entity.address = netData.data.address;
        entity.cityCode = netData.data.cityCode;
        entity.humidity = netData.data.humidity;
        entity.reportTime = netData.data.reportTime;
        entity.temp = netData.data.temp;
        entity.weather = netData.data.weather;
        entity.windDirection = netData.data.windDirection;
        entity.windPower = netData.data.windPower;
        return entity;
    }

    @NonNull
    @Override
    public LiveData<WeatherEntity> query(@Nullable String param) {
        return mDao.query(param);
    }

    @Override
    protected void insertToDb(@NonNull WeatherEntity dbData) {
        mDao.insert(dbData);
    }

    @NonNull
    @Override
    public WeatherDao getDao() {
        return mDao;
    }
}
