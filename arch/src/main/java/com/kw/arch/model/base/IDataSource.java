package com.kw.arch.model.base;

import androidx.lifecycle.MutableLiveData;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public interface IDataSource<P, T> {
    MutableLiveData<P> request();

    MutableLiveData<T> response();
}
