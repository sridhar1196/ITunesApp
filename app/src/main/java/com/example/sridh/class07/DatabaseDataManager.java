package com.example.sridh.class07;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseDataManager
{
    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private FilteredDOA filteredDOA;

    public DatabaseDataManager(Context mContext)
    {
        this.mContext = mContext;
        dbOpenHelper = new DatabaseOpenHelper(mContext);
        db = dbOpenHelper.getWritableDatabase();
        filteredDOA = new FilteredDOA(db);
    }

    public void close()
    {
        if(db != null)
            db.close();
    }

    public FilteredDOA getFilteredDOA()
    {
        return this.filteredDOA;
    }

    public long saveFilteredApp(FilteredApp note)
    {
        return this.filteredDOA.save(note);
    }

    public boolean updateFilteredApp(FilteredApp note)
    {
        return this.filteredDOA.update(note);
    }

    public boolean deleteFilteredApp(FilteredApp note)
    {
        return this.filteredDOA.delete(note);
    }

    public FilteredApp getFilteredApp(String name, Double price, String url)
    {
        return this.filteredDOA.get(name,price,url);
    }

    public ArrayList<FilteredApp> getAllFilteredApps()
    {
        return this.filteredDOA.getAll();
    }
}