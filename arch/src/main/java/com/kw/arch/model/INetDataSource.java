package com.kw.arch.model;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.kw.arch.model.base.BaseDataSource;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public abstract class INetDataSource<P, T> extends BaseDataSource<P, T> {
    public INetDataSource() {
        request().observeForever(request -> getNetData(request, response()::postValue));
    }

    @Override
    public void onReload(@Nullable Consumer<T> callback) {
        if (callback == null) return;
        P request = request().getValue();
        getNetData(request, response -> {
            response().postValue(response);
            callback.accept(response);
        });
    }

    public abstract void getNetData(@Nullable P request, @Nullable Consumer<T> callback);
}
