package com.kw.one.arch.recyclerview;

import com.kw.arch.view.recycler.ItemController;
import com.kw.arch.view.recycler.MultiTypeAdapter;
import com.kw.one.R;
import com.kw.one.databinding.ItemArticleTitleBinding;

/**
 * @author Kang Wei
 * @date 2019/11/16
 */
public class ArticleTitleController extends ItemController<ItemArticleTitleBinding> {
    private static final int VIEW_TYPE = 1;

    @Override
    protected void onCreated(MultiTypeAdapter.DataBindingViewHolder<ItemArticleTitleBinding> holder) {

    }

    @Override
    protected void onBind(MultiTypeAdapter.DataBindingViewHolder<ItemArticleTitleBinding> holder,
                          int position) {
        holder.mBinding.articleTitle.setText("标题栏1");
    }

    @Override
    protected int getCount() {
        return 1;
    }

    @Override
    protected int getLayout() {
        return R.layout.item_article_title;
    }

    @Override
    protected int getViewType() {
        return VIEW_TYPE;
    }

}
