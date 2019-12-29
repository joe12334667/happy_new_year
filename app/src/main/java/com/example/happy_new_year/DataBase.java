package com.example.happy_new_year;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    private final static int _DBVersion = 1; //<-- 版本
    private final static String _DBName = "NewYear.db";  //<-- db name
    private final static String _TableName = "MoneyTable"; //<-- table name
    public DataBase(@Nullable Context context) {
        super(context, _DBName, null, _DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL = "CREATE TABLE IF NOT EXISTS " + _TableName + "( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MONEY INTEGER ," +
                "FAMILY VARCHAR(50) " +
                ");";
        db.execSQL(SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
