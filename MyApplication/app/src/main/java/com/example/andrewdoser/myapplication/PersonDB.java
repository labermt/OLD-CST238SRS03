package com.example.andrewdoser.myapplication;

import android.provider.BaseColumns;

public final class PersonDB {
    private PersonDB(){}

    public static class Person implements BaseColumns{
        public static final String XYGRAPH = "XYGRAPH";
        public static final String COLUMN_X = "X";
        public static final String COLUMN_Y = "Y";


    }
}
