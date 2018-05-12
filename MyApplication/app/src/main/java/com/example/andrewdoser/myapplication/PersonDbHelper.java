package com.example.andrewdoser.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersonDbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PersonDB.Person.XYGRAPH + " (" +
                    PersonDB.Person._ID + " INTEGER PRIMARY KEY," +
                    PersonDB.Person.COLUMN_X + " REAL," +
                    PersonDB.Person.COLUMN_Y + " REAL)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PersonDB.Person.XYGRAPH;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Person.db";


    public PersonDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

    }

    public boolean insertData(String name, float x, float y){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PersonDB.Person.COLUMN_X, x);
        values.put(PersonDB.Person.COLUMN_Y, y);
        long result = db.insert(PersonDB.Person.XYGRAPH, null, values);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + PersonDB.Person.XYGRAPH, null);
        return res;
    }
    public void deleteData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PersonDB.Person.XYGRAPH, null, null);
        db.close();

    }
}
