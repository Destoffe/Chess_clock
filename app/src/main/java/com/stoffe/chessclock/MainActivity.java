package com.stoffe.chessclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.stoffe.chessclock.db.TimeEntity;
import com.stoffe.chessclock.db.TimeViewmodel;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {
    private ArrayList<TimeItem> times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimeViewmodel viewModel = ViewModelProviders.of(this).get(TimeViewmodel.class);

        SharedPreferences sharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("firstStart", true)) {
            setupFirstStart(sharedPreferences);
            for (int i = 0; i < times.size(); i++) {
                String id = times.get(i).getTime() + "-" + times.get(i).getIncrement();
                viewModel.insert(new TimeEntity(id, times.get(i).getTime(), times.get(i).getIncrement()));
            }
        }
    }

    private void setupFirstStart(SharedPreferences sharedPreferences) {
        times = new ArrayList<>();
        times.add(new TimeItem(1, 0));
        times.add(new TimeItem(2, 1));
        times.add(new TimeItem(3, 0));
        times.add(new TimeItem(3, 2));
        times.add(new TimeItem(5, 0));
        times.add(new TimeItem(5, 3));
        times.add(new TimeItem(10, 0));
        times.add(new TimeItem(10, 5));
        times.add(new TimeItem(15, 5));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }
}
