package com.kw.one;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Kang Wei
 * @date 2019/7/23
 */
public class OneUtils {
    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型
     *
     * @param clazz
     * @param index 返回某下标的类型
     */
    @Nullable
    public static Class getSuperClassGenericType(Class clazz, int index)
            throws IndexOutOfBoundsException {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return null;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return null;
        }
        if (!(params[index] instanceof Class)) {
            return null;
        }
        return (Class) params[index];
    }

    public static <T> LiveData<T> transformSingleObserver(LiveData<T> liveData) {
        MediatorLiveData<T> liveData1 = new MediatorLiveData<>();
        liveData1.addSource(liveData, t -> {
            liveData1.setValue(t);
            liveData1.removeSource(liveData);
        });
        return liveData1;
    }
}
