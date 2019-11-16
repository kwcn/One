package com.kw.arch.view.recycler;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Kang Wei
 * @date 2019/11/16
 */
public abstract class DataBindItemController<T, DataBinding extends ViewDataBinding> extends ItemController<DataBinding> {
    protected List<T> mData = new ArrayList<>();

    public void addData(@NonNull Collection<? extends T> newData) {
        checkNotNull();
        mData.addAll(newData);
        mAdapter.generateViews();
        mAdapter.notifyItemRangeInserted(mData.size() - newData.size() + mStartPosition,
                newData.size());
        compatibilityDataSizeChanged(newData.size());
    }

    public void addData(@IntRange(from = 0) int position,
                        @NonNull Collection<? extends T> newData) {
        checkNotNull();
        mData.addAll(position, newData);
        mAdapter.generateViews();
        mAdapter.notifyItemRangeInserted(position + mStartPosition, newData.size());
        compatibilityDataSizeChanged(newData.size());
    }

    public void addData(@IntRange(from = 0) int position, @NonNull T data) {
        checkNotNull();
        mData.add(position, data);
        mAdapter.generateViews();
        mAdapter.notifyItemInserted(position + mStartPosition);
        compatibilityDataSizeChanged(1);
    }

    public void addData(@NonNull T data) {
        checkNotNull();
        mData.add(data);
        mAdapter.generateViews();
        mAdapter.notifyItemInserted(mData.size() + mStartPosition);
        compatibilityDataSizeChanged(1);
    }

    public void remove(@IntRange(from = 0) int position) {
        checkNotNull();
        mData.remove(position);
        mAdapter.generateViews();
        int internalPosition = position + mStartPosition;
        mAdapter.notifyItemRemoved(internalPosition);
        compatibilityDataSizeChanged(0);
        mAdapter.notifyItemRangeChanged(internalPosition, mData.size() - internalPosition);
    }

    /**
     * change data
     */
    public void setData(@IntRange(from = 0) int index, @NonNull T data) {
        checkNotNull();
        mData.set(index, data);
        mAdapter.generateViews();
        mAdapter.notifyItemChanged(index + mStartPosition);
    }

    public void setNewData(@Nullable List<T> data) {
        checkNotNull();
        mData = data == null ? new ArrayList<>() : data;
        mAdapter.generateViews();
        mAdapter.notifyDataSetChanged();
    }

    private void compatibilityDataSizeChanged(int size) {
        final int dataSize = mData == null ? 0 : mData.size();
        if (dataSize == size) {
            mAdapter.generateViews();
            mAdapter.notifyDataSetChanged();
        }
    }

    public void checkNotNull() {
        if (mAdapter == null) {
            throw new RuntimeException("no set adapter");
        }
    }

    @Override
    protected int getCount() {
        return mData.size();
    }
}
