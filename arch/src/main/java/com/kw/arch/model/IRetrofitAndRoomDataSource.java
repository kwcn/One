package com.kw.arch.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;

import com.kw.arch.aspect.CheckNet;
import com.kw.arch.model.impl.IRetrofit;
import com.kw.arch.model.impl.IRoom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Kang Wei
 * @date 2019/11/2
 */
public abstract class IRetrofitAndRoomDataSource<P, NetT, DbT> extends IDbAndNetDataSource<P,
        NetT, DbT> implements IRetrofit<P, NetT>, IRoom<P, DbT> {
    public IRetrofitAndRoomDataSource(@NonNull Application application) {
        super(application);
    }

    @CheckNet
    @Override
    protected void fromNet(@Nullable P request, @NonNull Consumer<NetT> callback) {
        getCall(request).enqueue(new Callback<NetT>() {
            @Override
            public void onResponse(Call<NetT> call, Response<NetT> response) {
                callback.accept(response.isSuccessful() ? response.body() : null);
            }

            @Override
            public void onFailure(Call<NetT> call, Throwable t) {
                callback.accept(null);
            }
        });
    }


    @Override
    protected LiveData<DbT> fromDb(@Nullable P request) {
        return query(request);
    }

    @Override
    protected void insertToDb(@NonNull DbT dbData) {
        insert(dbData);
    }
}
