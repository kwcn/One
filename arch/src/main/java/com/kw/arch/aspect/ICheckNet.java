package com.kw.arch.aspect;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * @author Kang Wei
 * @date 2019/11/13
 */
public interface ICheckNet {
    @NonNull
    Context onContext();

    void onFailNet();
}
