package com.example.stacjapogodowa;

public class DataPoint {
float xValue, yValue;

    public DataPoint() {
    }

    public DataPoint(float xValue, float yValue) {
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public float getxValue() {
        return xValue;
    }

    public void setxValue(float xValue) {
        this.xValue = xValue;
    }

    public float getyValue() {
        return yValue;
    }

    public void setyValue(float yValue) {
        this.yValue = yValue;
    }
}
