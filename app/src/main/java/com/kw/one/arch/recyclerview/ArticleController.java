package com.kw.one.arch.recyclerview;

import android.util.Log;
import android.widget.TextView;

import com.kw.arch.view.recycler.DataBindItemController;
import com.kw.arch.view.recycler.MultiTypeAdapter;
import com.kw.one.R;
import com.kw.one.databinding.ItemArticleBinding;
import com.kw.one.source.bean.ArticleWrapperBean;

/**
 * @author Kang Wei
 * @date 2019/11/16
 */
public class ArticleController extends DataBindItemController<ArticleWrapperBean.DataBean.ArticleBean,
        ItemArticleBinding> {
    private static final int VIEW_TYPE = 2;

    @Override
    protected void onCreated(MultiTypeAdapter.DataBindingViewHolder<ItemArticleBinding> holder) {
        // item点击事件
        holder.itemView.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            Log.i("kwdy", "onCreated: " + mData.get(getSelfPosition(position)).title);
        });
        // 子控件点击事件
        holder.mBinding.articleId.setOnClickListener(v -> {
            Log.i("kwdy", "onCreated child: " + ((TextView) v).getText());
            int position = getSelfPosition(holder.getAdapterPosition());
            if (position >= 0) {
                remove(position);
            }
        });
    }

    @Override
    protected void onBind(MultiTypeAdapter.DataBindingViewHolder<ItemArticleBinding> holder,
                          int position) {
        holder.mBinding.articleId.setText(mData.get(getSelfPosition(position)).title);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_article;
    }

    @Override
    protected int getViewType() {
        return VIEW_TYPE;
    }
}
