package com.kw.arch.view.recycler;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kang Wei
 * @date 2019/11/15
 */
public class MultiTypeAdapter extends RecyclerView.Adapter<MultiTypeAdapter.DataBindingViewHolder> {
    private List<ItemController> mItemControllers = new ArrayList<>();
    // position->item
    private List<ItemController> mPositionControllers = new ArrayList<>();
    // viewType->item
    private SparseArray<ItemController> mTypeControllers = new SparseArray<>();

    @NonNull
    @Override
    public DataBindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemController itemController = mTypeControllers.get(viewType);
        ViewDataBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        itemController.getLayout(), parent, false);
        DataBindingViewHolder viewHolder = new MultiTypeAdapter.DataBindingViewHolder<>(binding);
        itemController.onCreated(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataBindingViewHolder holder, int position) {
        mPositionControllers.get(position).onBind(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return mPositionControllers.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mPositionControllers.size();
    }

    public void setItemControllers(List<ItemController> list) {
        mItemControllers.clear();
        mItemControllers.addAll(list);
        generateViews();
        notifyDataSetChanged();
    }

    public void generateViews() {
        mTypeControllers.clear();
        mPositionControllers.clear();
        int startPosition = 0;
        for (ItemController item : mItemControllers) {
            int count = item.getCount();
            item.mStartPosition = startPosition;
            item.mAdapter = this;
            while (count-- > 0) {
                mPositionControllers.add(item);
            }
            mTypeControllers.put(item.getViewType(), item);
            startPosition += item.getCount();
        }
    }

    public static class DataBindingViewHolder<DataBinding extends ViewDataBinding> extends RecyclerView.ViewHolder {
        public DataBinding mBinding;

        public DataBindingViewHolder(@NonNull DataBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
