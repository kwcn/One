package com.kw.arch.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

/**
 * @author Kang Wei
 * @date 2019/11/2
 */
public abstract class IDbAndNetDataSource<P, NetT, DbT> extends BaseApplicationDataSource<P, DbT> {
    private LiveData<DbT> mDbTLiveData;
    private MediatorLiveData<DbT> mResult = new MediatorLiveData<>();

    public IDbAndNetDataSource(@NonNull Application application) {
        super(application);
        mResult.observeForever(dbT -> {
        });
    }

    @Override
    protected void fetchData(@Nullable P request, @NonNull Consumer<DbT> callback) {
        if (request == null) {
            callback.accept(null);
            return;
        }
        // 每次查询时，先移除之前得观察LiveData
        if (mDbTLiveData != null) {
            mResult.removeSource(mDbTLiveData);
        }
        mDbTLiveData = fromDb(request);
        // 使用mResult是为了解决mDbTLiveData一次性观察，和持久观察的问题
        mResult.addSource(mDbTLiveData, dbT -> {
            mResult.removeSource(mDbTLiveData);
            if (dbT == null) {
                fromNet(request, netT -> {
                    if (netT == null) {
                        callback.accept(null);
                    } else {
                        insertToDb(transformNetToDbData(netT));
                        mDbTLiveData = fromDb(request);
                        mResult.addSource(mDbTLiveData, callback::accept);
                    }
                });
            } else {
                callback.accept(dbT);
                if (isUpdateFromNet(request)) {
                    fromNet(request, netT -> {
                        if (netT == null) {
                            callback.accept(null);
                        } else {
                            insertToDb(transformNetToDbData(netT));
                            mDbTLiveData = fromDb(request);
                            mResult.addSource(mDbTLiveData, callback::accept);
                        }
                    });
                }
            }
        });
    }

    protected abstract void fromNet(@Nullable P request, @NonNull Consumer<NetT> callback);

    protected abstract LiveData<DbT> fromDb(@Nullable P request);

    protected abstract void insertToDb(@NonNull DbT dbData);

    protected abstract boolean isUpdateFromNet(@Nullable P request);

    @NonNull
    protected abstract DbT transformNetToDbData(@NonNull NetT netData);
}
