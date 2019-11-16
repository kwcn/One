package com.kw.one.arch.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kw.one.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kang Wei
 * @date 2019/11/14
 */
public class ArticleAdapterV2 extends RecyclerView.Adapter<ArticleAdapterV2.ViewHolder> {
    private ArrayList<ArticleWrapperBean.DataBean.ArticleBean> mList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = mList.get(position).title;
        holder.title.setText(title);
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    public void setList(List<ArticleWrapperBean.DataBean.ArticleBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        protected ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.article_id);
        }
    }
}
