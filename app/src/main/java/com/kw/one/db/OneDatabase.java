package com.kw.one.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * @author Kang Wei
 * @date 2019/7/24
 */
@Database(entities = {MapEntity.class}, version = 1, exportSchema = false)
public abstract class OneDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "one";

    public abstract MapDao mapDao();
}
