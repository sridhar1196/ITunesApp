package com.example.sridh.class07;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FilteredDOA
{
    private SQLiteDatabase db;

    public FilteredDOA(SQLiteDatabase db)
    {
        this.db = db;
    }

    public long save(FilteredApp filteredApp)
    {
        ContentValues values = new ContentValues();
        values.put(FilteredTable.COLUMN_NAME, filteredApp.getName());
        values.put(FilteredTable.COLUMN_PRICE, filteredApp.getPrice());
        values.put(FilteredTable.COLUMN_THUMB, filteredApp.getLargeImageURL());
        return db.insert(FilteredTable.TABLENAME, null, values);
    }

    public boolean update(FilteredApp filteredApp)
    {
        ContentValues values = new ContentValues();
        values.put(FilteredTable.COLUMN_NAME, filteredApp.getName());
        values.put(FilteredTable.COLUMN_PRICE, filteredApp.getPrice());
        values.put(FilteredTable.COLUMN_THUMB, filteredApp.getLargeImageURL());
        return db.update(FilteredTable.TABLENAME, values, FilteredTable.COLUMN_NAME + "=? AND "+ FilteredTable.COLUMN_PRICE + "=? AND" + FilteredTable.COLUMN_THUMB + "=?", new String[]{filteredApp.getName()+""+filteredApp.getPrice()+""+filteredApp.getLargeImageURL()+""}) > 0;
    }

    public boolean delete(FilteredApp filteredApp)
    {
        //return db.delete(FilteredTable.TABLENAME, FilteredTable.COLUMN_NAME + "=? AND "+ FilteredTable.COLUMN_PRICE + "=? AND " + FilteredTable.COLUMN_THUMB + "=?", new String[]{filteredApp.getName()+""+String.valueOf(filteredApp.getPrice())+""+filteredApp.getLargeImageURL()+""}) > 0;
        return db.delete(FilteredTable.TABLENAME, FilteredTable.COLUMN_NAME + "=?", new String[]{filteredApp.getName()+""}) > 0;
    }

    public FilteredApp get(String name, Double price, String url)
    {
        FilteredApp note = null;

        Cursor c = db.query(true, FilteredTable.TABLENAME, new String[]{FilteredTable.COLUMN_NAME, FilteredTable.COLUMN_PRICE, FilteredTable.COLUMN_THUMB,},
                FilteredTable.COLUMN_NAME + "=? AND "+ FilteredTable.COLUMN_PRICE + "=? AND" + FilteredTable.COLUMN_THUMB + "=?", new String[]{name+""+price+""+url+""}, null, null, null, null, null);
        if(c != null && c.moveToFirst())
        {
            note = buildNoteFromCursor(c);
            if(!c.isClosed())
            {
                c.close();
            }
        }
        return note;
    }

    public ArrayList<FilteredApp> getAll()
    {
        ArrayList<FilteredApp> notes = new ArrayList<FilteredApp>();

        Cursor c = db.query(FilteredTable.TABLENAME, new String[]{FilteredTable.COLUMN_NAME,
                        FilteredTable.COLUMN_PRICE, FilteredTable.COLUMN_THUMB},
                null, null, null, null, null);

        if(c != null && c.moveToFirst())
        {
            do
            {
                FilteredApp note = buildNoteFromCursor(c);
                if(note != null)
                    notes.add(note);
            }
            while(c.moveToNext());
            if(!c.isClosed())
                c.close();
        }
        return notes;
    }

    private FilteredApp buildNoteFromCursor(Cursor c)
    {
        FilteredApp filteredApp = null;
        if(c != null)
        {
            filteredApp = new FilteredApp(c.getString(0), c.getDouble(1),c.getString(2));
        }
        return filteredApp;
    }
}
