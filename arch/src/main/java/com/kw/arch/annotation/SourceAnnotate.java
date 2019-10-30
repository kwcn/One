package com.kw.arch.annotation;

import androidx.annotation.NonNull;

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
                try {
                    BaseDataSource dataSource = repository.getDataSource(field.getType());
                    if (dataSource == null) {
                        dataSource = repository.appendSource(field.getType());
                    }
                    field.set(viewModel, dataSource);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
