package com.stoffe.chessclock.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TimeEntity.class}, version = 1)
public abstract class ClockDatabase extends RoomDatabase {

    private static ClockDatabase INSTANCE;
    public abstract TimeDao timeDao();

    public static ClockDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ClockDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ClockDatabase.class, "database-name")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}