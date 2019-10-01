package com.example.stacjapogodowa;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;


@Dao
public interface TemperatureLogDao {

    @Query("SELECT * FROM temperature_logs")
    Single<List<TemperatureLog>> getAll();

    @Query("SELECT * FROM temperature_logs ORDER BY uid DESC LIMIT :limit")
    Single<List<TemperatureLog>> getLast(int limit);


    @Query("SELECT * FROM temperature_logs WHERE date BETWEEN :start AND :end")
    Single<List<TemperatureLog>> getByDate(String start, String end);

    @Insert
    void insertAll(TemperatureLog... logs);

    @Insert
    void insertAll(List<TemperatureLog> logs);

    default Completable insert(final TemperatureLog... logs) {

        return Completable.fromRunnable(() -> insertAll(logs))
                .subscribeOn(Schedulers.io());
    }

}
