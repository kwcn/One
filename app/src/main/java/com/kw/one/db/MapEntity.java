package com.kw.one.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author Kang Wei
 * @date 2019/7/24
 */
@Entity(tableName = "Map", indices = @Index(value = "mKey", unique = true))
public class MapEntity {
    @NonNull
    @PrimaryKey
    public String mKey;
    public String mValue;

    public static MapEntity obtain(String key, String value) {
        MapEntity mapEntity = new MapEntity();
        mapEntity.mKey = key;
        mapEntity.mValue = value;
        return mapEntity;
    }
}
