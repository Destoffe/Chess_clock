package com.stoffe.chessclock.db;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface TimeDao {

    @Query("SELECT * FROM time")
    List<Time> getAllTimeformats();

    @Insert
    void insertAll(Time... times);

    @Delete
    void delete(Time time);
}
