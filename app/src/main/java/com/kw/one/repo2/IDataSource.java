package com.kw.one.repo2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author Kang Wei
 * @date 2019/10/28
 */
public interface IDataSource<Rq, Rp> {
    @NonNull
    MutableLiveData<Rq> getRequest();

    @NonNull
    MutableLiveData<Rp> getResponse();

    void reload(@Nullable Consumer<Rp> callback);
}
