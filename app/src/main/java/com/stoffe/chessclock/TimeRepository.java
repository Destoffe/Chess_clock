package com.stoffe.chessclock;

import android.app.Application;
import android.os.AsyncTask;

import com.stoffe.chessclock.db.ClockDatabase;
import com.stoffe.chessclock.db.TimeDao;
import com.stoffe.chessclock.db.TimeEntity;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TimeRepository {

    private TimeDao timeDao;
    private LiveData<List<TimeEntity>> allTimes;

    TimeRepository(Application application){
        ClockDatabase db = ClockDatabase.getDatabase(application);
        timeDao = db.timeDao();
        allTimes = timeDao.getAllTimeformats();
    }

    public void insert(TimeEntity time){
        new insertAsyncTask(timeDao).execute(time);
    }

    LiveData<List<TimeEntity>> getAllWords() {
        return allTimes;
    }


    private static class insertAsyncTask extends AsyncTask<TimeEntity, Void, Void> {

        private TimeDao mAsyncTaskDao;

        insertAsyncTask(TimeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TimeEntity... params) {
            mAsyncTaskDao.insertAll(params[0]);
            return null;
        }
    }

}



