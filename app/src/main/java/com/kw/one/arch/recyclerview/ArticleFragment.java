package com.kw.one.arch.recyclerview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kw.arch.aspect.CheckNet;
import com.kw.arch.view.BaseFragment;
import com.kw.one.R;
import com.kw.one.arch.recyclerview.base.ArticleTitleView;
import com.kw.one.arch.recyclerview.base.ArticleView;
import com.kw.one.arch.recyclerview.base.DataBindingAdapter;
import com.kw.one.arch.recyclerview.base.ItemView;
import com.kw.one.databinding.FragmentArticleListBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kang Wei
 * @date 2019/11/14
 */
public class ArticleFragment extends BaseFragment<ArticleViewModel, FragmentArticleListBinding> {
    private static final int TASK_COUNT = 1;
    //    private ArticleAdapter mAdapter;
//    private ArticleAdapterV2 mAdapterV2;
    private DataBindingAdapter mBindingAdapter;
    private ArticleDataSource mArticleSource;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startLoading(TASK_COUNT);
//        mAdapter = new ArticleAdapter(R.layout.item_article);
//        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        mBinding.recyclerView.setAdapter(mAdapter);

//        mAdapterV2 = new ArticleAdapterV2();
//        mBinding.recyclerView2.setLayoutManager(new LinearLayoutManager(mContext));
//        mBinding.recyclerView2.setAdapter(mAdapterV2);
        mBindingAdapter = new DataBindingAdapter();
        mBinding.recyclerView2.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView2.setAdapter(mBindingAdapter);
        mArticleSource = mViewModel.mArticleSource;
        event();
        bindUI();
    }

    private void bindUI() {
        mArticleSource.response().observe(getViewLifecycleOwner(), val -> {
            try {
//                mAdapter.setNewData(val.data.datas);
//                mAdapterV2.setList(val.data.datas);
                List<ItemView> itemViews = new ArrayList<>();
                itemViews.add(new ArticleTitleView());
                val.data.datas.forEach(articleBean -> {
                    itemViews.add(new ArticleView(articleBean));
                    mBindingAdapter.setItemViews(itemViews);
                });
                loadedOneTask(mArticleSource, true);
            } catch (Exception e) {
                loadedOneTask(mArticleSource, false);
            }
        });
        mArticleSource.request().setValue(null);
    }

    private void event() {
        mBinding.swipeRefresh.setOnRefreshListener(this::reload);
    }

    @Override
    public void onLoaded(boolean isValid) {
        super.onLoaded(isValid);
        mBinding.swipeRefresh.setRefreshing(false);
    }

    @Override
    protected ArticleViewModel getVModel() {
        return new ViewModelProvider(this).get(ArticleViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article_list;
    }

    @CheckNet
    @Override
    protected void reload() {
        startLoading(TASK_COUNT);
        mArticleSource.onReload(null);
    }
}
