package com.kw.one.arch.recyclerview.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author Kang Wei
 * @date 2019/11/15
 */
public abstract class ItemView<DataBinding extends ViewDataBinding> {
    @LayoutRes
    protected abstract int getLayout();

    protected abstract int getViewType();

    protected abstract void onBind(DataBindingAdapter.DataBindingViewHolder<DataBinding> holder,
                                   int position);

    protected DataBindingAdapter.DataBindingViewHolder<DataBinding> getViewHolder(@NonNull ViewGroup parent) {
        DataBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayout(),
                        parent, false);
        return new DataBindingAdapter.DataBindingViewHolder<>(binding);
    }
}
