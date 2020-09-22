package com.stoffe.chessclock.db;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TimeDao {

    @Query("SELECT * FROM timeentity")
    LiveData<List<TimeEntity>> getAllTimeformats();

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insertAll(TimeEntity times);

    @Delete
    void delete(TimeEntity time);
}
