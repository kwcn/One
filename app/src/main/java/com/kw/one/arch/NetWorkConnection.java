package com.kw.one.arch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.kw.arch.aspect.IConnect;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * @author Kang Wei
 * @date 2019/11/13
 */
public class NetWorkConnection implements IConnect {
    private static final String CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
    private Context mContext;
    private boolean mIsConnect;
    private ConnectivityManager mManager;

    public NetWorkConnection(@NonNull Context context) {
        mContext = context.getApplicationContext();
        mManager = (ConnectivityManager) mContext.getSystemService(CONNECTIVITY_SERVICE);
        mIsConnect = getCurNetWorkStatus();
        mContext.registerReceiver(mReceiver, new IntentFilter(CONNECTIVITY_CHANGE));
    }

    private boolean getCurNetWorkStatus() {
        NetworkInfo activeNetworkInfo = mManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            Toast.makeText(mContext, "无网络连接，请检查网络", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void unRegisterNetReceiver() {
        mContext.unregisterReceiver(mReceiver);
    }

    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && action.equals(CONNECTIVITY_CHANGE)) {
                mIsConnect = getCurNetWorkStatus();
            }
        }
    };

    @Override
    public boolean onConnect() {
        return mIsConnect;
    }
}
