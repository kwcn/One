package com.kw.one.repo.base;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

/**
 * 数据模型
 *
 * @author Kang Wei
 * @date 2019/7/23
 */
public abstract class Repo<R, T> {
    // 适用于ui controller的异步数据
    public abstract LiveData<T> getLiveData(R r);

    // 适用于非ui的数据访问，且是同步访问
    @WorkerThread
    public abstract T getSyncData(R r);

    public abstract void reload();
}
