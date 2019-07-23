package com.kw.one.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kw.one.repo.CurWeatherRepo;
import com.kw.one.repo.bean.CurWeather;

/**
 * @author Kang Wei
 * @date 2019/7/23
 */
public class HomeViewModel extends ViewModel {
    public LiveData<CurWeather> mCurWeather;
    // 天气数据
    private CurWeatherRepo mCurWeatherRepo;

    public HomeViewModel() {
        mCurWeatherRepo = new CurWeatherRepo();
        mCurWeather = mCurWeatherRepo.getStableLiveData();
    }

    public void setCity(String city) {
        mCurWeatherRepo.setParams(city);
    }

    public void reloadWeather() {
        mCurWeatherRepo.reload();
    }
}
