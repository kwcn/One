package com.kw.arch.annotation;

import android.app.Application;

import androidx.annotation.NonNull;

import com.kw.arch.model.BaseApplicationDataSource;
import com.kw.arch.model.BaseDataSource;
import com.kw.arch.model.Repository;
import com.kw.arch.viewmodel.BaseViewModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public class SourceAnnotate {
    public static void initSources(@NonNull BaseViewModel viewModel,
                                   @NonNull Repository repository) {
        Field[] declaredFields = viewModel.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            Source annotation = field.getAnnotation(Source.class);
            if (annotation != null) {
                field.setAccessible(true);
                Class<?> type = field.getType();
                try {
                    BaseDataSource dataSource;
                    Singleton singletonAnnotate = type.getAnnotation(Singleton.class);
                    // 单例的DataSource都会存在仓库里，非单例的会在view生命周期结束后释放
                    if (singletonAnnotate == null) {
                        dataSource = createDateSource(type, viewModel.getApplication());
                    } else {
                        dataSource = repository.getDataSource(type);
                        if (dataSource == null) {
                            dataSource = createDateSource(type, viewModel.getApplication());
                            repository.appendSource(type, dataSource);
                        }
                    }
                    field.set(viewModel, dataSource);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static BaseDataSource createDateSource(Class<?> type, Application application) {
        BaseDataSource dataSource = null;
        try {
            if (BaseApplicationDataSource.class.isAssignableFrom(type)) {
                Constructor<?> constructor = type.getConstructor(Application.class);
                dataSource = (BaseDataSource) constructor.newInstance(application);
            } else {
                dataSource = (BaseDataSource) type.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }
}
