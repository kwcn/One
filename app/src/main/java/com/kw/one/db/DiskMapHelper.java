package com.kw.one.db;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.Map;

/**
 * 用于存储硬盘信息
 *
 * @author Kang Wei
 * @date 2019/7/24
 */
public class DiskMapHelper {
    public static final String CITY_KEY = "city";
    private static volatile DiskMapHelper sDiskMapHelper;
    private MapDao mMapDao;
    // 使用缓存，避免重复查询数据库，并且利用livedata的观察属性，缓存数据能够及时得到更新
    // 没有同步数据的缓存，因为没有较好的观察变更机制
    private Map<String, LiveData<String>> mCache;

    private DiskMapHelper(Context context) {
        mMapDao = DBManager.getInstance(context).mDatabase.mapDao();
        mCache = new ArrayMap<>();
    }

    public static DiskMapHelper getInstance(Context context) {
        if (sDiskMapHelper == null) {
            synchronized (DiskMapHelper.class) {
                if (sDiskMapHelper == null) {
                    sDiskMapHelper = new DiskMapHelper(context.getApplicationContext());
                }
            }
        }
        return sDiskMapHelper;
    }

    synchronized public void putValue(String key, String value) {
        mMapDao.insert(key, value);
    }

    // 获取同步的值，可能会阻塞线程
    public String getValue(String key) {
        return mMapDao.getSyncValue(key);
    }

    // 获取异步的值
    synchronized public LiveData<String> getLiveValue(String key) {
        LiveData<String> liveData = mCache.get(key);
        if (liveData == null) {
            liveData = mMapDao.getValue(key);
            mCache.put(key, liveData);
            // 当liveData没有observe时，mCache里的liveData不会更新值，这里设置一个默认的observe
            liveData.observeForever(s -> {
                Log.d(DiskMapHelper.class.getSimpleName(), "getLiveValue: key:" + key + " value:" + s);
            });
        }
        return liveData;
    }
}
