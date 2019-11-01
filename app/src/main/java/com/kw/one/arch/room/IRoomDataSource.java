package com.kw.one.arch.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;

import com.kw.arch.model.IDbDataSource;

/**
 * @author Kang Wei
 * @date 2019/10/30
 */
public abstract class IRoomDataSource<P, T, Dao> extends IDbDataSource<P, T> {
    protected Dao mDao;

    public IRoomDataSource(@NonNull Application application) {
        super(application);
        mDao = getDao();
    }

    @Override
    protected void query(@Nullable P request, @Nullable Consumer<T> callback) {
        if (callback == null) return;
        // 当数据库有数据变更(增删改操作)时，都会驱动LiveData响应新数据
        query(request).observeForever(callback::accept);
    }

    @NonNull
    protected abstract LiveData<T> query(@Nullable P request);

    @NonNull
    protected abstract Dao getDao();
}
