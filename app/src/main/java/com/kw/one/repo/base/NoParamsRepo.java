package com.kw.one.repo.base;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

/**
 * @author Kang Wei
 * @date 2019/7/23
 */
public abstract class NoParamsRepo<T> {
    public abstract LiveData<T> getLiveData();

    @WorkerThread
    public abstract T getSyncData();

    public abstract void reload();
}
