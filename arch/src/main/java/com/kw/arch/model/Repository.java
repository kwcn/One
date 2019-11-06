package com.kw.arch.model;

import android.app.Application;

import androidx.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public class Repository {
    private ConcurrentHashMap<String, BaseDataSource> mDataSources;

    public static Repository getInstance() {
        return InRepository.inRepository;
    }

    private static class InRepository {
        private static Repository inRepository = new Repository();
    }

    private Repository() {
        mDataSources = new ConcurrentHashMap<>();
    }

    public BaseDataSource appendSource(Class<?> sourceClass) {
        String canonicalName = sourceClass.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        BaseDataSource instance = null;
        try {
            instance = (BaseDataSource) sourceClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Cannot create an instance of " + sourceClass, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot create an instance of " + sourceClass, e);
        }
        mDataSources.put(canonicalName, instance);
        return instance;
    }

    public BaseDataSource appendApplicationSource(Class<?> sourceClass, Application application) {
        String canonicalName = sourceClass.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        BaseApplicationDataSource instance = null;
        try {
            Constructor<?> constructor = sourceClass.getConstructor(Application.class);
            instance = (BaseApplicationDataSource) constructor.newInstance(application);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mDataSources.put(canonicalName, instance);
        return instance;
    }

    public BaseDataSource getDataSource(@NonNull Class<?> sourceClass) {
        String canonicalName = sourceClass.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        return mDataSources.get(canonicalName);
    }
}
