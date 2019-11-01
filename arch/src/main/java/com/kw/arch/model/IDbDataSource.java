package com.kw.arch.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.kw.arch.model.base.BaseApplicationDataSource;

/**
 * @author Kang Wei
 * @date 2019/10/30
 */
public abstract class IDbDataSource<P, T> extends BaseApplicationDataSource<P, T> {
    public IDbDataSource(@NonNull Application application) {
        super(application);
        request().observeForever(request -> query(request, response()::postValue));
    }

    @Override
    public void onReload(@Nullable Consumer<T> callback) {
        if (callback == null) return;
        P request = request().getValue();
        query(request, response -> {
            response().postValue(response);
            callback.accept(response);
        });
    }

    protected abstract void query(@Nullable P request, @Nullable Consumer<T> callback);

    public abstract void insert(T t);

    public abstract void delete(T t);

    public abstract void update(T t);
}
