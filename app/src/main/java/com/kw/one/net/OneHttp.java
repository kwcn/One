package com.kw.one.net;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import androidx.annotation.AnyThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

/**
 * @author Kang Wei
 * @date 2019/7/20
 */
public class OneHttp {
    private static final int DEFAULT_NETWORK_THREAD_POOL_SIZE = 4;

    private int mCursor;
    private HandlerThread[] mThreadPool;
    private Handler[] mHandlers;
    private NetWork mNetWork;

    private OneHttp(int threadPoolSize, @Nullable NetWork netWork) {
        if (threadPoolSize <= 0) {
            mThreadPool = new HandlerThread[DEFAULT_NETWORK_THREAD_POOL_SIZE];
            mHandlers = new Handler[DEFAULT_NETWORK_THREAD_POOL_SIZE];
        } else {
            mThreadPool = new HandlerThread[threadPoolSize];
            mHandlers = new Handler[threadPoolSize];
        }

        if (netWork == null) {
            mNetWork = new BasicNetwork();
        }

        mCursor = 0;
        for (int i = 0; i < mThreadPool.length; i++) {
            HandlerThread handlerThread = new HandlerThread("OneHttp-Thread-" + (i + 1));
            mThreadPool[i] = handlerThread;
            handlerThread.start();
            setHandler(handlerThread, i);
        }
    }

    // 确保handler设置成功
    private void setHandler(HandlerThread handlerThread, int index) {
        Looper looper = handlerThread.getLooper();
        if (looper == null) {
            setHandler(handlerThread, index);
        } else {
            mHandlers[index] = new Handler(looper);
        }
    }

    private Handler getAvailableHandler() {
        for (Handler handler : mHandlers) {
            if (handler.getLooper().getQueue().isIdle()) {
                return handler;
            }
        }
        if (mCursor >= mThreadPool.length) {
            mCursor = 0;
        }
        return mHandlers[mCursor++];
    }

    @AnyThread
    public void AsyncRequest(@NonNull Request request, @NonNull Callback callback) {
        Handler handler = getAvailableHandler();
        handler.post(new NetWorker(request, mNetWork, callback));
    }

    @WorkerThread
    public Response SyncRequest(Request request) {
        return mNetWork.performRequest(request);
    }

    public static class Builder {
        private int netThreadPoolSize;
        private NetWork netWork;

        public Builder setNetThreadPoolSize(int threadPoolSize) {
            this.netThreadPoolSize = threadPoolSize;
            return this;
        }

        public Builder setNetWork(NetWork netWork) {
            this.netWork = netWork;
            return this;
        }

        public OneHttp build() {
            return new OneHttp(netThreadPoolSize, netWork);
        }
    }
}
