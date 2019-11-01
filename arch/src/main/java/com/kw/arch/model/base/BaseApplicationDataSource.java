package com.kw.arch.model.base;

import android.app.Application;

import androidx.annotation.NonNull;

/**
 * @author Kang Wei
 * @date 2019/10/31
 */
public abstract class BaseApplicationDataSource<P, T> extends BaseDataSource<P, T> {
    protected Application mApplication;

    public BaseApplicationDataSource(@NonNull Application application) {
        mApplication = application;
    }
}
