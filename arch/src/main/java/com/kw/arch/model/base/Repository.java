package com.kw.arch.model.base;

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

    public <T extends BaseDataSource> Repository appendSource(Class<T> sourceClass) {
        String canonicalName = sourceClass.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        T instance;
        try {
            instance = sourceClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Cannot create an instance of " + sourceClass, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot create an instance of " + sourceClass, e);
        }
        mDataSources.put(canonicalName, instance);
        return this;
    }

    public <T extends BaseDataSource> BaseDataSource getDataSource(Class<T> sourceClass) {
        String canonicalName = sourceClass.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        return (T) mDataSources.get(canonicalName);
    }
}
