package com.example.sridh.class07;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FilteredTable
{
    static final String TABLENAME = "filtered";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_PRICE = "price";
    static final String COLUMN_THUMB = "thumb_url";

    static public void onCreate(SQLiteDatabase db)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLENAME + " (");
        sb.append(COLUMN_NAME + " TEXT," + COLUMN_PRICE + " TEXT," + COLUMN_THUMB + " TEXT, PRIMARY KEY (" );
        sb.append(COLUMN_NAME + "," + COLUMN_PRICE + "," + COLUMN_THUMB + "));");
        try
        {
            db.execSQL(sb.toString());
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }


    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        FilteredTable.onCreate(db);
    }
}
