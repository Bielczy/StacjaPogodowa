package com.example.stacjapogodowa;

public class DataFetch {

    float axisX, axisY;

    public DataFetch() {
    }

    public DataFetch(float axisX, float axisY) {
        this.axisX = axisX;
        this.axisY = axisY;
    }

    public float getAxisX() {
        return axisX;
    }

    public void setAxisX(float axisX) {
        this.axisX = axisX;
    }

    public float getAxisY() {
        return axisY;
    }

    public void setAxisY(float axisY) {
        this.axisY = axisY;
    }
}
