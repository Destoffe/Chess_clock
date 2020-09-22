package com.stoffe.chessclock.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Time.class}, version = 1)
public abstract class ClockDatabase extends RoomDatabase {
    public abstract TimeDao userDao();
}