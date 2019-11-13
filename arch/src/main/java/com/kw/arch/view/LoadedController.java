package com.kw.arch.view;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.AnyThread;
import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 当有多个任务同时加载时，控制其一致性
 *
 * @author Kang Wei
 * @date 2019/11/11
 */
public class LoadedController {
    private ExecutorService mExecutor;
    private ILoaded mOnLoaded;
    private volatile CountDownLatch mCountDownLatch;
    private HashMap<String, Boolean> mTaskKeyMap;
    private Handler mMainHandler;

    public LoadedController(@NonNull @AnyThread ILoaded onLoaded) {
        mOnLoaded = onLoaded;
        mExecutor = Executors.newSingleThreadExecutor();
        mTaskKeyMap = new HashMap<>();
        mMainHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 开启任务群，当任务都完成时，执行onLoaded回调
     *
     * @param taskCount
     */
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
                mMainHandler.post(() -> mOnLoaded.onLoaded(isAllValid()));
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

    /**
     * 完成一次任务，并设置任务的key值来避免一个任务群中一个任务多次执行
     *
     * @param taskKey 任务唯一标识值
     * @param isValid 是否获得有效数据
     */
    public synchronized void loadedOneTask(String taskKey, boolean isValid) {
        if (mCountDownLatch == null)
            return;
        Boolean exist = mTaskKeyMap.get(taskKey);
        if (exist == null) {
            mCountDownLatch.countDown();
        }
        mTaskKeyMap.put(taskKey, isValid);
    }

    private boolean isAllValid() {
        for (Map.Entry<String, Boolean> e : mTaskKeyMap.entrySet()) {
            if (!e.getValue()) {
                return false;
            }
        }
        return true;
    }

    ;
}
