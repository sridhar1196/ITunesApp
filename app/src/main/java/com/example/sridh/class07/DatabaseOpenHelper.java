package com.example.sridh.class07;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper
{
    static final String DB_NAME = "filterTable.db";
    static final int DB_VERSION = 1;

    public DatabaseOpenHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        FilteredTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        FilteredTable.onUpgrade(sqLiteDatabase, i , i1);
    }
}
