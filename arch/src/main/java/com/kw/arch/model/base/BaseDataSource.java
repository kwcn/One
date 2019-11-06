package com.kw.arch.model.base;

import androidx.annotation.AnyThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
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
        // 建立Request和Response联系，并实现两者的异步使用
        mRequest.observeForever(request -> fetchData(request, mResponse::postValue));
    }

    @Override
    public MutableLiveData<P> request() {
        return mRequest;
    }

    @Override
    public MutableLiveData<T> response() {
        return mResponse;
    }

    @Override
    public void onReload(@Nullable Consumer<T> callback) {
        fetchData(mRequest.getValue(), response -> {
            mResponse.postValue(response);
            if (callback != null) callback.accept(response);
        });
    }

    protected abstract void fetchData(@Nullable P request,@AnyThread @NonNull Consumer<T> response);
}
