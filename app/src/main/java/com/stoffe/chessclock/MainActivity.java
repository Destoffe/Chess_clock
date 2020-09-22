package com.stoffe.chessclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import com.stoffe.chessclock.db.ClockDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClockDatabase db = Room.databaseBuilder(getApplicationContext(),
                ClockDatabase.class, "database-name").build();

    }
}
