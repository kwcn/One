package com.kw.arch.model.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import retrofit2.Call;

/**
 * @author Kang Wei
 * @date 2019/11/6
 */
public interface IRetrofit<P, T> {
    @NonNull
    Call<T> getCall(@Nullable P request);
}
