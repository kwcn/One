package com.kw.one.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.kw.arch.annotation.Source;
import com.kw.arch.viewmodel.BaseViewModel;
import com.kw.one.source.ArticleDataSource;

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
