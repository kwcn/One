package com.kw.one.repo2;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Kang Wei
 * @date 2019/10/28
 */
public class Repository {
    private ConcurrentHashMap<String, IDataSource> mDataSources;

    private Repository() {
        mDataSources = new ConcurrentHashMap<>();
    }

    public static Repository getInstance() {
        return InRepository.inRepository;
    }

    private static class InRepository {
        private static Repository inRepository = new Repository();
    }

    public <T extends IDataSource> void addDataSource(Class<T> sourceClass) {
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
    }

    public IDataSource getDataSource(Class sourceClass) {
        String canonicalName = sourceClass.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        return mDataSources.get(canonicalName);
    }
}
