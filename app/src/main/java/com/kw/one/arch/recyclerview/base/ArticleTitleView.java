package com.kw.one.arch.recyclerview.base;

import com.kw.one.R;
import com.kw.one.databinding.ItemArticleTitleBinding;

/**
 * @author Kang Wei
 * @date 2019/11/16
 */
public class ArticleTitleView extends ItemView<ItemArticleTitleBinding> {
    private static final int VIEW_TYPE = 1;

    @Override
    protected int getLayout() {
        return R.layout.item_article_title;
    }

    @Override
    protected int getViewType() {
        return VIEW_TYPE;
    }

    @Override
    protected void onBind(DataBindingAdapter.DataBindingViewHolder<ItemArticleTitleBinding> holder, int position) {
        holder.mBinding.articleTitle.setText("标题栏1");
    }
}
