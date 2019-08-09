package com.kw.one.repo.base;

import androidx.annotation.Nullable;
import androidx.lifecycle.ComputableLiveData;

/**
 * @author Kang Wei
 * @date 2019/8/8
 */
public abstract class Repo<P, T> {
    protected abstract T getSyncData(@Nullable P p);

    protected ComputableLiveData<T> getAsyncData(@Nullable P p) {
        return new ComputableLiveData<T>() {
            @Override
            protected T compute() {
                return getSyncData(p);
            }
        };
    }

    public Provider<P, T> getProvider(P param) {
        return new Provider<>(this, param);
    }

    public MutableProvider<P, T> getMutableProvider() {
        return new MutableProvider<>(this);
    }
}
