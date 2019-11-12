package com.kw.arch.view;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class GLoading {
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_LOAD_SUCCESS = 2;
    public static final int STATUS_LOAD_FAILED = 3;
    public static final int STATUS_EMPTY_DATA = 4;
    public static final int STATUS_NETWORK_ERROR = 5;

    private static volatile GLoading mDefault;
    private Adapter mAdapter;
    private static boolean DEBUG = false;

    public interface Adapter {
        View getView(Holder holder, View convertView, int status);
    }

    public static void debug(boolean debug) {
        DEBUG = debug;
    }

    private GLoading() {
    }

    public static GLoading from(Adapter adapter) {
        GLoading gloading = new GLoading();
        gloading.mAdapter = adapter;
        return gloading;
    }

    public static GLoading getDefault() {
        if (mDefault == null) {
            synchronized (GLoading.class) {
                if (mDefault == null) {
                    mDefault = new GLoading();
                    mDefault.mAdapter = new DefaultGLoadingAdapter();
                }
            }
        }
        return mDefault;
    }

    public static void initDefault(Adapter adapter) {
        getDefault().mAdapter = adapter;
    }

    public Holder wrap(Activity activity) {
        ViewGroup wrapper = activity.findViewById(android.R.id.content);
        return new Holder(mAdapter, activity, wrapper);
    }

    public Holder wrap(View view) {
        FrameLayout wrapper = new FrameLayout(view.getContext());
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp != null) {
            wrapper.setLayoutParams(lp);
        }
        if (view.getParent() != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            int index = parent.indexOfChild(view);
            parent.removeView(view);
            parent.addView(wrapper, index);
        }
        LayoutParams newLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        wrapper.addView(view, newLp);
        return new Holder(mAdapter, view.getContext(), wrapper);
    }

    public static class Holder {
        private Adapter mAdapter;
        private Context mContext;
        private RetryLoad mRetryTask;
        private View mCurStatusView;
        private ViewGroup mWrapper;
        private int curState;
        private SparseArray<View> mStatusViews = new SparseArray<>(4);
        private Object mData;

        private Holder(Adapter adapter, Context context, ViewGroup wrapper) {
            this.mAdapter = adapter;
            this.mContext = context;
            this.mWrapper = wrapper;
        }

        public Holder withRetry(RetryLoad task) {
            mRetryTask = task;
            return this;
        }

        public Holder withData(Object data) {
            this.mData = data;
            return this;
        }

        public void showLoading() {
            showLoadingStatus(STATUS_LOADING);
        }

        public void showLoadSuccess() {
            showLoadingStatus(STATUS_LOAD_SUCCESS);
        }

        public void showLoadFailed() {
            showLoadingStatus(STATUS_LOAD_FAILED);
        }

        public void showEmpty() {
            showLoadingStatus(STATUS_EMPTY_DATA);
        }

        public void showNetWorkError() {
            showLoadingStatus(STATUS_NETWORK_ERROR);
        }

        public void showLoadingStatus(int status) {
            if (curState == status || !validate()) {
                return;
            }
            curState = status;
            //first try to reuse status view
            View convertView = mStatusViews.get(status);
            if (convertView == null) {
                //secondly try to reuse current status view
                convertView = mCurStatusView;
            }
            try {
                //call customer adapter to get UI for specific status. convertView can be reused
                View view = mAdapter.getView(this, convertView, status);
                if (view == null) {
                    printLog(mAdapter.getClass().getName() + ".getView returns null");
                    return;
                }
                if (view != mCurStatusView || mWrapper.indexOfChild(view) < 0) {
                    if (mCurStatusView != null) {
                        mWrapper.removeView(mCurStatusView);
                    }
                    mWrapper.addView(view);
                    ViewGroup.LayoutParams lp = view.getLayoutParams();
                    if (lp != null) {
                        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    }
                } else if (mWrapper.indexOfChild(view) != mWrapper.getChildCount() - 1) {
                    // make sure loading status view at the front
                    view.bringToFront();
                }
                mCurStatusView = view;
                mStatusViews.put(status, view);
            } catch (Exception e) {
                if (DEBUG) {
                    e.printStackTrace();
                }
            }
        }

        private boolean validate() {
            if (mAdapter == null) {
                printLog("GLoading.Adapter is not specified.");
            }
            if (mContext == null) {
                printLog("Context is null.");
            }
            if (mWrapper == null) {
                printLog("The mWrapper of loading status view is null.");
            }
            return mAdapter != null && mContext != null && mWrapper != null;
        }

        public Context getContext() {
            return mContext;
        }

        public ViewGroup getWrapper() {
            return mWrapper;
        }

        public RetryLoad getRetryTask() {
            return mRetryTask;
        }

        public <T> T getData() {
            try {
                return (T) mData;
            } catch (Exception e) {
                if (DEBUG) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public interface RetryLoad {
        void retry(int status);
    }

    private static void printLog(String msg) {
        if (DEBUG) {
            Log.e("GLoading", msg);
        }
    }
}
