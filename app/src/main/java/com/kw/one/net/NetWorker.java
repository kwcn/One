package com.kw.one.net;

/**
 * @author Kang Wei
 * @date 2019/7/20
 */
public class NetWorker implements Runnable {
    private Request mRequest;
    private NetWork mNetWork;
    private Callback mCallback;

    public NetWorker(Request request, NetWork netWork, Callback callback) {
        mRequest = request;
        mNetWork = netWork;
        mCallback = callback;
    }

    @Override
    public void run() {
        Response response = mNetWork.performRequest(mRequest);
        mCallback.onCall(response);
    }
}
