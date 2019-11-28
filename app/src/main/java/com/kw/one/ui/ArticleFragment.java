package com.kw.one.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kw.arch.view.BaseFragment;
import com.kw.arch.view.recycler.ItemController;
import com.kw.arch.view.recycler.MultiTypeAdapter;
import com.kw.one.R;
import com.kw.one.arch.recyclerview.ArticleController;
import com.kw.one.arch.recyclerview.ArticleTitleController;
import com.kw.one.databinding.FragmentArticleListBinding;
import com.kw.one.source.ArticleDataSource;
import com.kw.one.viewmodel.ArticleViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用RecyclerView的界面
 * @author Kang Wei
 * @date 2019/11/14
 */
public class ArticleFragment extends BaseFragment<ArticleViewModel, FragmentArticleListBinding> {
    private static final int TASK_COUNT = 1;
    private MultiTypeAdapter mBindingAdapter;
    private ArticleDataSource mArticleSource;
    private ArticleController mArticleView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startLoading(TASK_COUNT);
        mArticleSource = mViewModel.mArticleSource;

        mBindingAdapter = new MultiTypeAdapter();
        mBinding.recyclerView2.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView2.setAdapter(mBindingAdapter);
        List<ItemController> itemControllers = new ArrayList<>();
        itemControllers.add(new ArticleTitleController());
        mArticleView = new ArticleController();
        itemControllers.add(mArticleView);
        mBindingAdapter.setItemControllers(itemControllers);
        bindUI();
        bindEvent();
    }

    private void bindUI() {
        mArticleSource.response().observe(getViewLifecycleOwner(), val -> {
            try {
                mArticleView.setNewData(val.data.datas);
                loadedOneTask(mArticleSource, true);
            } catch (Exception e) {
                loadedOneTask(mArticleSource, false);
            }
        });
        mArticleSource.request().setValue(null);
    }

    private void bindEvent() {
        mBinding.swipeRefresh.setOnRefreshListener(() -> {
            startLoading(TASK_COUNT);
            mArticleSource.onReload(null);
        });

        setErrorReloadBtnOnClickListener(s -> {
            startLoading(TASK_COUNT);
            mArticleSource.onReload(null);
        });
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
}
