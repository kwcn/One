package com.kw.one.repo.base;

import androidx.lifecycle.LiveData;

/**
 * @author Kang Wei
 * @date 2019/8/20
 */
public abstract class AbstractMutableLiveData<T> extends LiveData<T> {
    @Override
    public void postValue(T value) {
        super.postValue(value);
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
    }
}