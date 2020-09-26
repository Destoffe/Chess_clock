package com.stoffe.chessclock.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TimeEntity {
    @NonNull
    @PrimaryKey
    public String uid;

    @ColumnInfo(name = "start_time")
    public int startTime;

    @ColumnInfo(name = "increment")
    public int increment;

    public TimeEntity(String uid, int startTime, int increment) {
        this.uid = uid;
        this.startTime = startTime;
        this.increment = increment;
    }

}
