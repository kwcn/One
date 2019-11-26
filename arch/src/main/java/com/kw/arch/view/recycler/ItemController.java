package com.kw.arch.view.recycler;

import androidx.annotation.LayoutRes;
import androidx.databinding.ViewDataBinding;

/**
 * @author Kang Wei
 * @date 2019/11/15
 */
public abstract class ItemController<DataBinding extends ViewDataBinding> {
    public MultiTypeAdapter mAdapter;
    public int mStartPosition;

    /**
     * 可以利用holder设置点击事件，但需要注意使用baseViewHolder.getAdapterPosition()-startPosition获取position
     *
     * @param holder
     */
    protected void onCreated(MultiTypeAdapter.DataBindingViewHolder<DataBinding> holder) {
    }

    protected abstract void onBind(MultiTypeAdapter.DataBindingViewHolder<DataBinding> holder,
                                   int position);

    @LayoutRes
    protected abstract int getLayout();

    protected abstract int getViewType();

    protected abstract int getCount();

    /**
     * 获取局部的position
     *
     * @param position 全局原始position,通常使用holder.getAdapterPosition()获取
     * @return 局部的position
     */
    protected int getSelfPosition(int position) {
        return position - mStartPosition;
    }
}
