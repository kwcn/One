package com.kw.one.repo.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;


/**
 * 可靠的liveData，不会随着参数的变化而变化
 *
 * @author Kang Wei
 * @date 2019/7/23
 */
public abstract class StableRepo<Params, T> extends Repo<Params, T> {
    private MediatorLiveData<Params> mParams = new MediatorLiveData<>();

    // 当设置不同参数时，获取一个不可变的LiveData
    public LiveData<T> getStableLiveData() {
        return Transformations.switchMap(mParams, this::getLiveData);
    }

    public void setParams(Params params) {
        mParams.setValue(params);
    }
}
