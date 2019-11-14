package com.kw.one.arch.recyclerview;

import android.app.Application;

import androidx.annotation.NonNull;

import com.kw.arch.annotation.Source;
import com.kw.arch.viewmodel.BaseViewModel;

/**
 * @author Kang Wei
 * @date 2019/11/14
 */
public class ArticleViewModel extends BaseViewModel {
    @Source
    public ArticleDataSource mArticleSource;

    public ArticleViewModel(@NonNull Application application) {
        super(application);
    }
}
