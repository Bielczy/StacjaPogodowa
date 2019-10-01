package com.example.stacjapogodowa;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import java.util.List;
import io.reactivex.Single;


public class ViewModelTemperature extends AndroidViewModel {

    private TemperatureLogDao mTemperatureLogDao;
    private Single<List<TemperatureLog>> mAllTemperatureLogs;

    public ViewModelTemperature(@NonNull Application application) {
        super(application);

        DB db = DB.getDatabase(application);
        mTemperatureLogDao =  db.temperatureLogs();
        mAllTemperatureLogs = mTemperatureLogDao.getAll();
    }

    Single<List<TemperatureLog>> getAll() {

        return mAllTemperatureLogs;
    }

    public void insertAll (TemperatureLog temperatureLog) {
        new insertAsyncTask(mTemperatureLogDao).execute(temperatureLog);
    }

    private static class insertAsyncTask extends AsyncTask<TemperatureLog, Void, Void> {

        private TemperatureLogDao mAsyncTaskTemperatureLogDao;
        insertAsyncTask(TemperatureLogDao temperatureDao){
            mAsyncTaskTemperatureLogDao = temperatureDao;
        }

        @Override
        protected Void doInBackground(TemperatureLog... logs) {
            mAsyncTaskTemperatureLogDao.insertAll(logs);
            return null;
        }
    }
}
