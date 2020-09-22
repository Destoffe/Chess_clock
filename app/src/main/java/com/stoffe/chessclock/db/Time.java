package com.stoffe.chessclock.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Time {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name="start_time")
    public int startTime;

    @ColumnInfo(name="increment")
    public int increment;

}
