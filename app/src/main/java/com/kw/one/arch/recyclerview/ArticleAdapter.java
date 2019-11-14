package com.kw.one.arch.recyclerview;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kw.one.R;

/**
 * @author Kang Wei
 * @date 2019/11/14
 */
public class ArticleAdapter extends BaseQuickAdapter<ArticleWrapperBean.DataBean.ArticleBean,
        ArticleAdapter.ViewHolder> {
    public ArticleAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(ViewHolder helper, ArticleWrapperBean.DataBean.ArticleBean item) {
        helper.title.setText(item.title);
    }

    protected static class ViewHolder extends BaseViewHolder {
        private TextView title;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.article_id);
        }
    }
}
