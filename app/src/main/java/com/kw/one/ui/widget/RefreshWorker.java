package com.kw.one.ui.widget;

import android.view.View;

import androidx.annotation.AnyThread;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RefreshWorker {
    private CyclicBarrier mCyclicBarrier;
    private ExecutorService mExecutor;

    public RefreshWorker(@NonNull SwipeRefreshLayout refreshLayout,
                         @IntRange(from = 1) int taskCount, @NonNull Runnable refresh) {
        mExecutor = Executors.newFixedThreadPool(taskCount);
        refreshLayout.setEnabled(true);
        refreshLayout.setVisibility(View.VISIBLE);
        mCyclicBarrier = new CyclicBarrier(taskCount, () -> refreshLayout.setRefreshing(false));
        refreshLayout.setOnRefreshListener(() -> {
            // 直到上一次任务全部完成后再执行新的刷新
            if (mCyclicBarrier.getNumberWaiting() == 0) {
                refresh.run();
            }
        });
    }

    @AnyThread
    public void cutRefreshTask() {
        mExecutor.submit(() -> {
            try {
                mCyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
