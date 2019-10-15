package com.example.stacjapogodowa;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Database(
        entities = {
                TemperatureLog.class,

        },
        version = 12,
        exportSchema = false
)
public abstract class DB extends RoomDatabase {
    public static String DB_NAME = "stacja_database12";

    public abstract TemperatureLogDao temperatureLogs();

    private static volatile DB INSTANCE;

    static DB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DB.class, "Logs_databaset")
                            .fallbackToDestructiveMigration()
                            .addCallback(DataBaseCallback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback DataBaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TemperatureLogDao temperatureLogDao;

        PopulateDbAsync(DB db) {
            temperatureLogDao = db.temperatureLogs();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            generateTemperatureLogs();
            return null;
        }

        void generateTemperatureLogs() {
            List<TemperatureLog> logs = new ArrayList<>();

            long baseTimeMs = new Date(2019, 8, 20, 10, 0, 0).getTime();

            int dateStep = 1000 * 60 * 5;

            for (int i = 0; i < 10000; i++) {

                TemperatureLog log = new TemperatureLog();

                log.setHumidity(new Random().nextFloat() % 100);
                log.setTemperature((new Random().nextFloat() % 100) + 5);

                baseTimeMs += dateStep;
                Date nextDate = new Date(baseTimeMs);
                log.setDate(DateFormatter.toString(nextDate));

                logs.add(log);
            }
            temperatureLogDao.insertAll(logs);
        }
    }
}
