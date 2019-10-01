package com.example.stacjapogodowa;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {
    private static DateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.getDefault());

    String date = "";

    public DateFormatter(String date){

        this.date = date;
    }

    public static String toString(Date date){

        return formatter.format(date);
    }

    public Date format() throws ParseException {
        return formatter.parse(date);
    }

}