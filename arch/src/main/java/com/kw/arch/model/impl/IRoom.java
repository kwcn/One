package com.kw.arch.model.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

/**
 * @author Kang Wei
 * @date 2019/11/6
 */
public interface IRoom<P, E, Dao> {
    @NonNull
    LiveData<E> query(@Nullable P param);

    @NonNull
    Dao getDao();
}
