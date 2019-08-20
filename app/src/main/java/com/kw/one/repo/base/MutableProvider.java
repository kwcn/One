package com.kw.one.repo.base;

import androidx.annotation.AnyThread;
import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * 设置一个可根据参数变化的数据，但livedata始终是一个
 * @author Kang Wei
 * @date 2019/8/8
 */
public class MutableProvider<P, T> implements IProvider<T> {

    private Repo<P, T> mRepo;
    private MediatorLiveData<T> mLiveData;
    private MutableLiveData<P> mParam;

    public MutableProvider(Repo<P, T> repo) {
        mRepo = repo;
        mLiveData = new MediatorLiveData<>();
        mParam = new MutableLiveData<>();
        mLiveData.addSource(mParam, p -> mRepo.getAsyncData(p,
                result -> mLiveData.postValue(result)));
    }

    @Override
    public LiveData<T> getLiveData() {
        return mLiveData;
    }

    @Override
    public T getData() {
        return mRepo.getSyncData(mParam.getValue());
    }

    @Override
    public void reload(Consumer<T> callback) {
        mRepo.getAsyncData(mParam.getValue(), t -> {
            mLiveData.postValue(t);
            callback.accept(t);
        });
    }

    // 设置参数会触发数据回调
    @AnyThread
    public void setParam(P param) {
        mParam.postValue(param);
    }
}
