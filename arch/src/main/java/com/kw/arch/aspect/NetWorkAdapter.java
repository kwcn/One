package com.kw.arch.aspect;

import androidx.annotation.Nullable;

/**
 * @author Kang Wei
 * @date 2019/11/12
 */
public class NetWorkAdapter {
    private IConnect mConnect;

    public static NetWorkAdapter getInstance() {
        return InNetWorkAdapter.inAdapter;
    }

    private static class InNetWorkAdapter {
        private static NetWorkAdapter inAdapter = new NetWorkAdapter();
    }

    @Nullable
    public IConnect getConnect() {
        return mConnect;
    }

    public void setConnect(IConnect connect) {
        mConnect = connect;
    }
}
