package com.kw.one.repo.base;

import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;

/**
 * 一个provider只提供一个数据的操作
 *
 * @author Kang Wei
 * @date 2019/8/8
 */
public interface IProvider<T> {
    LiveData<T> getLiveData();

    T getData();

    void reload(Consumer<T> callback);
}
