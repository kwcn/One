package com.kw.one.arch.recyclerview.base;

import androidx.annotation.NonNull;

import com.kw.one.R;
import com.kw.one.arch.recyclerview.ArticleWrapperBean;
import com.kw.one.databinding.ItemArticleBinding;

/**
 * @author Kang Wei
 * @date 2019/11/16
 */
public class ArticleView extends ItemView<ItemArticleBinding> {
    private static final int VIEW_TYPE = 2;
    private ArticleWrapperBean.DataBean.ArticleBean mData;

    public ArticleView(@NonNull ArticleWrapperBean.DataBean.ArticleBean data) {
        mData = data;
    }

    @Override
    protected int getLayout() {
        return R.layout.item_article;
    }

    @Override
    protected int getViewType() {
        return VIEW_TYPE;
    }

    @Override
    protected void onBind(DataBindingAdapter.DataBindingViewHolder<ItemArticleBinding> holder,
                          int position) {
        holder.mBinding.articleId.setText(mData.title);
    }
}
