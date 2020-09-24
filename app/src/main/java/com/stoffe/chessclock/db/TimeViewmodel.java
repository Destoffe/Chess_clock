package com.stoffe.chessclock.db;

import android.app.Application;

import java.util.List;

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

    public LiveData<List<TimeEntity>> getAllTimes(){
        return allTimes;
    }
    public void insert(TimeEntity time)
    {
        repository.insert(time);
    }
}
