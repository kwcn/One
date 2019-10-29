package com.kw.arch.annotation;

import androidx.annotation.NonNull;

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
                    field.set(viewModel, repository.getDataSource(field.getType()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
