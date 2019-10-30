package com.kw.one.arch.retrofit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.kw.arch.model.INetDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public abstract class IRetrofitDataSource<P, T> extends INetDataSource<P, T> {
    @Override
    public void getNetData(@Nullable P request, @Nullable Consumer<T> callback) {
        if (callback == null) return;
        if (request == null) callback.accept(null);
        getCall(request).enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    callback.accept(response.body());
                } else {
                    callback.accept(null);
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                callback.accept(null);
            }
        });
    }

    @NonNull
    public abstract Call<T> getCall(P rq);
}
