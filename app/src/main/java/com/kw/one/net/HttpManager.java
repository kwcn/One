package com.kw.one.net;

/**
 * @author Kang Wei
 * @date 2019/7/23
 */
public class HttpManager {
    public OneHttp mOneHttp;

    private HttpManager() {
        mOneHttp = new OneHttp.Builder().build();
    }

    public static HttpManager getInstance() {
        return OneHttpManagerHolder.sHolder;
    }

    private static class OneHttpManagerHolder {
        private static HttpManager sHolder = new HttpManager();
    }
}
