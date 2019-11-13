package com.kw.one;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.kw.arch.aspect.NetWorkAdapter;
import com.kw.one.arch.NetWorkConnection;
import com.kw.one.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private NetWorkConnection mNetWorkConnection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNetWorkConnection = new NetWorkConnection(this);
        NetWorkAdapter.getInstance().setConnect(mNetWorkConnection);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Nullable
    @Override
    protected ViewModel getViewMode() {
        return null;
    }

    @Override
    protected void onDestroy() {
        mNetWorkConnection.unRegisterNetReceiver();
        super.onDestroy();
    }
}
