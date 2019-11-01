package com.kw.arch.annotation;

import android.util.Log;

import androidx.annotation.NonNull;

import com.kw.arch.model.base.BaseApplicationDataSource;
import com.kw.arch.model.base.BaseDataSource;
import com.kw.arch.model.base.Repository;
import com.kw.arch.viewmodel.BaseViewModel;

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
                    BaseDataSource dataSource = repository.getDataSource(type);
                    if (dataSource == null) {
                        if (BaseApplicationDataSource.class.isAssignableFrom(type)) {
                            dataSource = repository.appendApplicationSource(type,
                                    viewModel.getApplication());
                        } else {
                            dataSource = repository.appendSource(field.getType());
                        }
                    }
                    field.set(viewModel, dataSource);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
