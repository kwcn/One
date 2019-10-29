package com.kw.arch.model.base;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public interface IRefresh<T> {
    void onReload(@Nullable Consumer<T> callback);
}
