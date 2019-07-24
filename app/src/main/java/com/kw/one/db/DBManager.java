package com.kw.one.db;

import android.content.Context;

import androidx.room.Room;

/**
 * @author Kang Wei
 * @date 2019/7/24
 */
public class DBManager {
    private static volatile DBManager sDBManager;
    public OneDatabase mDatabase;

    private DBManager(Context context) {
        mDatabase = Room.databaseBuilder(context, OneDatabase.class, OneDatabase.DATABASE_NAME).allowMainThreadQueries().build();
    }

    public static DBManager getInstance(Context context) {
        if (sDBManager == null) {
            synchronized (DBManager.class) {
                if (sDBManager == null) {
                    sDBManager = new DBManager(context.getApplicationContext());
                }
            }
        }
        return sDBManager;
    }
}
