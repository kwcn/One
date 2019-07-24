package com.kw.one.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * @author Kang Wei
 * @date 2019/7/24
 */
@Dao
public abstract class MapDao {
    @Query("select mValue from map where mKey=:key")
    public abstract LiveData<String> getValue(String key);

    @Query("select mValue from map where mKey=:key")
    public abstract String getSyncValue(String key);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insert(MapEntity entity);

    @Delete
    public abstract void delete(MapEntity... mapEntities);

    public long insert(String key, String value) {
        return insert(MapEntity.obtain(key, value));
    }
}
