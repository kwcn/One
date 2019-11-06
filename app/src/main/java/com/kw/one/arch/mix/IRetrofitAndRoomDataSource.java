package com.kw.one.arch.mix;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;

import com.kw.arch.model.IDbAndNetDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Kang Wei
 * @date 2019/11/2
 */
public abstract class IRetrofitAndRoomDataSource<P, NetT, DbT> extends IDbAndNetDataSource<P,
        NetT, DbT> {
    public IRetrofitAndRoomDataSource(@NonNull Application application) {
        super(application);
    }

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
        return queryDb(request);
    }

    @NonNull
    public abstract Call<NetT> getCall(@Nullable P request);

    @NonNull
    protected abstract LiveData<DbT> queryDb(@Nullable P request);
}
