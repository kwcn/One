package com.kw.one.repo.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 返回一个固定的数据查询，设置初始参数后将不再变化
 * @author Kang Wei
 * @date 2019/8/8
 */
public class Provider<P, T> implements IProvider<T> {
    private Repo<P, T> mRepo;
    private AbstractMutableLiveData<T> mLiveData;
    private P mParam;

    public Provider(@NonNull Repo<P, T> repo, @Nullable P param) {
        mRepo = repo;
        mParam = param;
        mLiveData = new AbstractMutableLiveData<T>() {
            private AtomicBoolean started = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    mRepo.getAsyncData(mParam, result -> mLiveData.postValue(result));
                }
            }
        };
    }

    @Override
    public LiveData<T> getLiveData() {
        return mLiveData;
    }

    @Override
    public T getData() {
        return mRepo.getSyncData(mParam);
    }

    @Override
    public void reload(Consumer<T> callback) {
        mRepo.getAsyncData(mParam, t -> {
            mLiveData.postValue(t);
            callback.accept(t);
        });
    }
}
