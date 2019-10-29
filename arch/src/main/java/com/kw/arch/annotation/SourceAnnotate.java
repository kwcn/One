package com.kw.arch.annotation;

import androidx.annotation.NonNull;

import com.kw.arch.model.base.Repository;
import com.kw.arch.viewmodel.BaseViewModel;

/**
 * @author Kang Wei
 * @date 2019/10/29
 */
public class SourceAnnotate {
    public static void initSources(@NonNull BaseViewModel viewModel,
                                   @NonNull Repository repository) {
        //TODO 从viewmodel里面读出注释，并且从repository获取source进行赋值
    }
}
