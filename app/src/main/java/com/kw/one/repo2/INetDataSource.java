package com.kw.one.repo2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.lifecycle.MutableLiveData;

/**
 * @author Kang Wei
 * @date 2019/10/28
 */
public abstract class INetDataSource<Rq, Rp> implements IDataSource<Rq, Rp> {
    private MutableLiveData<Rq> mRequest;
    private MutableLiveData<Rp> mResponse;

    public INetDataSource() {
        mRequest = new MutableLiveData<>();
        mResponse = new MutableLiveData<>();

        mRequest.observeForever(request -> {
            if (request == null) return;
            getNetData(request, mResponse::postValue);
        });
    }

    @NonNull
    @Override
    public MutableLiveData<Rq> getRequest() {
        return mRequest;
    }

    @NonNull
    @Override
    public MutableLiveData<Rp> getResponse() {
        return mResponse;
    }

    @Override
    public void reload(@Nullable Consumer<Rp> callback) {
        if (callback == null) return;
        Rq rq = mRequest.getValue();
        if (rq == null) {
            callback.accept(null);
            return;
        }
        getNetData(rq, response -> {
            mResponse.postValue(response);
            callback.accept(response);
        });
    }

    public abstract void getNetData(Rq request, Consumer<Rp> callback);
}
