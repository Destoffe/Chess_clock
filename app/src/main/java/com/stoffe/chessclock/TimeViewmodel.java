package com.stoffe.chessclock;

import android.app.Application;
import android.app.TimePickerDialog;

import com.stoffe.chessclock.db.TimeEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TimeViewmodel extends AndroidViewModel {
    private LiveData<List<TimeEntity>> allTimes;
    private TimeRepository repository;
    public TimeViewmodel(Application application) {
        super(application);
        repository = new TimeRepository(application);
        allTimes = repository.getAllWords();
    }

    LiveData<List<TimeEntity>> getAllTimes(){
        return allTimes;
    }
    public void insert(TimeEntity time)
    {
        repository.insert(time);
    }
}
