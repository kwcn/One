package com.kw.one.net;

/**
 * @author Kang Wei
 * @date 2019/7/23
 */
public class OneHttpManager {
    public OneHttp mOneHttp;

    private OneHttpManager() {
        mOneHttp = new OneHttp.Builder().build();
    }

    public static OneHttpManager getInstance() {
        return OneHttpManagerHolder.sHolder;
    }

    private static class OneHttpManagerHolder {
        private static OneHttpManager sHolder = new OneHttpManager();
    }
}
