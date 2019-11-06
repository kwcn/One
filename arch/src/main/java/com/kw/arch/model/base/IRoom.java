package com.kw.arch.model.base;

import androidx.lifecycle.LiveData;

/**
 * @author Kang Wei
 * @date 2019/11/6
 */
public interface IRoom<P, E> {
    LiveData<E> query(P param);

    void insert(E entity);

    void delete(E entity);

    void update(E entity);
}
