package com.kw.one.arch.recyclerview.base;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kang Wei
 * @date 2019/11/15
 */
public class DataBindingAdapter extends RecyclerView.Adapter<DataBindingAdapter.DataBindingViewHolder> {
    // position->item
    private List<ItemView> mItemViews = new ArrayList<>();
    // viewType->item
    private SparseArray<ItemView> mTypeViews = new SparseArray<>();

    @NonNull
    @Override
    public DataBindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return mTypeViews.get(viewType).getViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DataBindingViewHolder holder, int position) {
        mItemViews.get(position).onBind(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return mItemViews.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mItemViews.size();
    }

    public void setItemViews(List<ItemView> list) {
        mItemViews.clear();
        mItemViews.addAll(list);
        generateTypeViews();
        notifyDataSetChanged();
    }

    private void generateTypeViews() {
        mTypeViews.clear();
        mItemViews.forEach(item -> {
            mTypeViews.put(item.getViewType(), item);
        });
    }

    public static class DataBindingViewHolder<DataBinding extends ViewDataBinding> extends RecyclerView.ViewHolder {
        public DataBinding mBinding;

        public DataBindingViewHolder(@NonNull DataBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
