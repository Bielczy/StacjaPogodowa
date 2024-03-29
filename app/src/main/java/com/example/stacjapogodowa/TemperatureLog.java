package com.example.stacjapogodowa;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "temperature_logs")
public class TemperatureLog {

    @PrimaryKey(autoGenerate = true)
    protected long uid;

    @ColumnInfo(name = "temperature")
    private float temperature = 0;

    @ColumnInfo(name = "humidity")
    private float humidity = 0;

    @ColumnInfo(name = "system_time")
    private long systemTime;

    @ColumnInfo(name = "date")
    protected String date;

    @ColumnInfo(name = "MIN(temperature")
    private float minTemperature = 0;

    @ColumnInfo(name = "MAX(temperature)")
    private float maxTemperature = 0;

    @ColumnInfo(name = "MAX(humidity)")
    private float maxHumidity = 0;

    @ColumnInfo(name = "AVG(temperature)")
    private float avgTemperature = 0;

    @ColumnInfo(name = "AVG(humidity")
    private float avgHumidity = 0;

    public TemperatureLog() {
    }

   //od sekund
    private String serializeTimeStamp(){
        long secondsInStamp = systemTime% 60;
        long minutesInStamp = (systemTime / (60)) % 60;
        long hoursInStamp =  (systemTime / (60 * 60)) % 24;
        long daysInStamp = systemTime / (60 * 60 * 24);

        return String.valueOf(daysInStamp) + ":" +
                String.valueOf(hoursInStamp) + ":" +
                String.valueOf(minutesInStamp) + ":" +
                String.valueOf(secondsInStamp);
    }


    @Override
    public String toString() {
        return "uid=" + uid +
                ", temperature=  " + temperature +
                ", humidity=  " + humidity +
                ", date='" + date + '\'';
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }


    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getUid() {
        return uid;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public String getDate() {
        return date;
    }

    public float getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(float maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(float minTemperature) {
        this.minTemperature = minTemperature;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public float getAvgTemperature() {
        return avgTemperature;
    }

    public void setAvgTemperature(float avgTemperature) {
        this.avgTemperature = avgTemperature;
    }

    public float getAvgHumidity() {
        return avgHumidity;
    }

    public void setAvgHumidity(float avgHumidity) {
        this.avgHumidity = avgHumidity;
    }
}
