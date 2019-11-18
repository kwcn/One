package com.kw.one.source;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kw.arch.model.IRetrofitDataSource;
import com.kw.one.source.bean.ArticleWrapperBean;
import com.kw.one.arch.retrofit.WebService;

import retrofit2.Call;

/**
 * @author Kang Wei
 * @date 2019/11/14
 */
public class ArticleDataSource extends IRetrofitDataSource<Void, ArticleWrapperBean> {
    @NonNull
    @Override
    public Call<ArticleWrapperBean> getCall(@Nullable Void request) {
        return WebService.getInstance().getArticleWrapperBean();
    }
}
