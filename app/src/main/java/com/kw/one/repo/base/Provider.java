package com.kw.one.repo.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;

/**
 * @author Kang Wei
 * @date 2019/8/8
 */
public class Provider<P, T> implements IProvider<T> {
    private Repo<P, T> mRepo;
    private ComputableLiveData<T> mLiveData;
    private P mParam;

    public Provider(@NonNull Repo<P, T> repo, @Nullable P param) {
        mRepo = repo;
        mParam = param;
    }

    @Override
    public LiveData<T> getAsyncData() {
        if (mLiveData == null) {
            mLiveData = mRepo.getAsyncData(mParam);
        }
        return mLiveData.getLiveData();
    }

    @Override
    public T getSyncData() {
        return mRepo.getSyncData(mParam);
    }

    @Override
    public void reload() {
        if (mLiveData != null) {
            mLiveData.invalidate();
        }
    }
}
