package com.kw.one.arch.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author Kang Wei
 * @date 2019/10/30
 */
@Entity(tableName = "WeatherEntity", indices = @Index(value = "address", unique = true))
public class WeatherEntity {
    // 设置自增属性
    @PrimaryKey
    @NonNull
    public String address;
    public String cityCode;
    public String temp;
    public String weather;
    public String windDirection;
    public String windPower;
    public String humidity;
    public String reportTime;
}
