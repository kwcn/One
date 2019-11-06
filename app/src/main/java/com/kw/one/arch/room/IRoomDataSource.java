package com.kw.one.arch.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.kw.arch.model.base.BaseApplicationDataSource;

/**
 * room查询能返回livedata类型的数据，并且数据变更时，会进行回调
 * @author Kang Wei
 * @date 2019/10/30
 */
public abstract class IRoomDataSource<P, T> extends BaseApplicationDataSource<P, T> {
    private MutableLiveData<P> mRequest;
    private volatile Consumer<T> mCallBack;

    public IRoomDataSource(@NonNull Application application) {
        super(application);
        mRequest = new MutableLiveData<>();
        // 每次查询都会返回一个新的liveData，通过switchMap转化后，始终只有一个liveData在进行回调
        Transformations.switchMap(mRequest, this::query).observeForever(t -> {
            if (mCallBack != null) mCallBack.accept(t);
        });
    }

    @Override
    protected void fetchData(@Nullable P request, @NonNull Consumer<T> callback) {
        mCallBack = callback;
        mRequest.postValue(request);
    }

    @NonNull
    protected abstract LiveData<T> query(@Nullable P request);

    public abstract void insert(T t);

    public abstract void delete(T t);

    public abstract void update(T t);
}
