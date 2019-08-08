package com.kw.one.repo.base;

import androidx.lifecycle.LiveData;

/**
 * 一个provider只提供一个数据
 *
 * @author Kang Wei
 * @date 2019/8/8
 */
public interface IProvider<T> {
    LiveData<T> getAsyncData();

    T getSyncData();

    void reload();
}
