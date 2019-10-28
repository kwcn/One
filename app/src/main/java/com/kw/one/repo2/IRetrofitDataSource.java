package com.kw.one.repo2;


import androidx.annotation.NonNull;
import androidx.core.util.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Kang Wei
 * @date 2019/10/28
 */
public abstract class IRetrofitDataSource<Rq, Rp> extends INetDataSource<Rq, Rp> {
    @Override
    public void getNetData(Rq request, Consumer<Rp> callback) {
        getCall(request).enqueue(new Callback<Rp>() {
            @Override
            public void onResponse(Call<Rp> call, Response<Rp> response) {
                if (response.isSuccessful()) {
                    callback.accept(response.body());
                } else {
                    callback.accept(null);
                }
            }

            @Override
            public void onFailure(Call<Rp> call, Throwable t) {
                callback.accept(null);
            }
        });
    }

    @NonNull
    public abstract Call<Rp> getCall(Rq rq);
}
