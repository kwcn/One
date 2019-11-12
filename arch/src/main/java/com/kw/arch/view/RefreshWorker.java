package com.kw.arch.view;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.AnyThread;
import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Kang Wei
 * @date 2019/11/11
 */
public class RefreshWorker {
    private ExecutorService mExecutor;
    private IRefresh mRefresh;
    private volatile CountDownLatch mCountDownLatch;
    private HashMap<String, Boolean> mTaskKeyMap;
    private Handler mMainHandler;

    public RefreshWorker(@NonNull @AnyThread IRefresh refresh) {
        mRefresh = refresh;
        mExecutor = Executors.newSingleThreadExecutor();
        mTaskKeyMap = new HashMap<>();
        mMainHandler = new Handler(Looper.getMainLooper());
    }

    public synchronized void start(int taskCount) {
        if (mCountDownLatch != null && mCountDownLatch.getCount() > 0) {
            // 上一次任务还没执行完
            return;
        }
        mCountDownLatch = new CountDownLatch(taskCount);
        mTaskKeyMap.clear();
        mExecutor.execute(() -> {
            try {
                mCountDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mMainHandler.post(mRefresh::onRefresh);
            }
        });
    }

    public synchronized void quit() {
        long count = mCountDownLatch.getCount();
        if (mCountDownLatch != null && count > 0) {
            for (int i = 0; i < count; i++) {
                mCountDownLatch.countDown();
            }
        }
        mCountDownLatch = null;
    }

    public synchronized void finishOneTask(String taskKey) {
        if (mCountDownLatch == null)
            return;
        Boolean exist = mTaskKeyMap.get(taskKey);
        if (exist == null || !exist) {
            mTaskKeyMap.put(taskKey, true);
            mCountDownLatch.countDown();
        }
    }
}
