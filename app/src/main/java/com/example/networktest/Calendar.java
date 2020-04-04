package com.example.networktest;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Calendar {


    private LocalDate currentDate = LocalDate.now(); // 2016-06-17
    private DayOfWeek dow = currentDate.getDayOfWeek(); // FRIDAY
    private int dom = currentDate.getDayOfMonth(); // 17
    private int doy = currentDate.getDayOfYear(); // 169
    private  Month m = currentDate.getMonth(); // JUNE
    private int y = currentDate.getYear();
    private int monthNumber;

    Date date = new Date();
    private long unixTime = date.getTime();




    public int getMonthNumber() {
       Month month = m;
        switch (month) {

            case JANUARY: monthNumber = 1;
                break;

            case FEBRUARY: monthNumber = 2;
                break;
            case MARCH: monthNumber = 3;
                break;
            case APRIL: monthNumber = 4;
                break;
            case MAY: monthNumber = 5;
                break;
            case JUNE: monthNumber = 6;
                break;
            case JULY: monthNumber = 7;
                break;
            case AUGUST: monthNumber = 8;
                break;
            case SEPTEMBER: monthNumber = 9;
                break;
            case OCTOBER: monthNumber = 10;
                break;
            case NOVEMBER: monthNumber = 11;
                break;
            case DECEMBER: monthNumber = 12;
                break;


        }


        return monthNumber;
    }

    public long getUnixTime() {
        return unixTime;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public DayOfWeek getDow() {
        return dow;
    }

    public int getDom() {
        return dom;
    }

    public int getDoy() {
        return doy;
    }

    public Month getM() {
        return m;
    }

    public int getY() {
        return y;
    }
}
