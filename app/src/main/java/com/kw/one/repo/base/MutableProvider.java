package com.kw.one.repo.base;

import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;

/**
 * @author Kang Wei
 * @date 2019/8/8
 */
public class MutableProvider<P, T> implements IProvider<T> {

    private Repo<P, T> mRepo;
    private ComputableLiveData<T> mComputableLiveData;
    private LiveData<T> mLiveData;
    private MediatorLiveData<P> mParam = new MediatorLiveData<>();

    public MutableProvider(Repo<P, T> repo) {
        mRepo = repo;
    }

    @Override
    public LiveData<T> getAsyncData() {
        if (mLiveData == null) {
            mLiveData = Transformations.switchMap(mParam, p -> {
                mComputableLiveData = mRepo.getAsyncData(p);
                return mComputableLiveData.getLiveData();
            });
        }
        return mLiveData;
    }

    @Override
    public T getSyncData() {
        return mRepo.getSyncData(mParam.getValue());
    }

    @Override
    public void reload() {
        if (mComputableLiveData != null) {
            mComputableLiveData.invalidate();
        }
    }

    public void setParam(P param) {
        mParam.setValue(param);
    }
}
