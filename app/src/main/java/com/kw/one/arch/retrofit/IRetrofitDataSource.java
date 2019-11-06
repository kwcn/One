package com.kw.one.arch.retrofit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.kw.arch.model.base.BaseDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public abstract class IRetrofitDataSource<P, T> extends BaseDataSource<P, T> {
    @Override
    public void fetchData(@Nullable P request, @NonNull Consumer<T> callback) {
        getCall(request).enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                callback.accept(response.isSuccessful() ? response.body() : null);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                callback.accept(null);
            }
        });
    }

    @NonNull
    public abstract Call<T> getCall(@Nullable P request);
}
