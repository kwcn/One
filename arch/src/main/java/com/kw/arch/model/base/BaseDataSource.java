package com.kw.arch.model.base;

import androidx.lifecycle.MutableLiveData;

/**
 * @author Kang Wei
 * @date 2019/10/29
 * <p>
 * P 请求参数类型
 * T 响应参数类型
 */
public abstract class BaseDataSource<P, T> implements IDataSource<P, T>, IRefresh<T> {
    private MutableLiveData<P> mRequest;
    private MutableLiveData<T> mResponse;

    public BaseDataSource() {
        mRequest = new MutableLiveData<>();
        mResponse = new MutableLiveData<>();
    }

    @Override
    public MutableLiveData<P> request() {
        return mRequest;
    }

    @Override
    public MutableLiveData<T> response() {
        return mResponse;
    }
}
