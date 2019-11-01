package com.kw.one.arch.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

/**
 * @author Kang Wei
 * @date 2019/10/30
 */
@Dao
public interface WeatherDao {
    @Query("select * from weatherentity where address=:address")
    LiveData<WeatherEntity> query(String address);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherEntity... entity);

    @Delete
    void delete(WeatherEntity... entity);

    @Update
    void update(WeatherEntity entity);
}
