package com.example.sridh.class07;

import java.util.Comparator;

/**
 * Created by sridh on 10/24/2017.
 */

public class PriceComparator implements Comparator<FilteredApp> {
    @Override
    public int compare(FilteredApp filteredApp, FilteredApp t1) {
        if(t1.getPrice()<filteredApp.getPrice())
        {
            return -1;
        }
        else
            return 1;
    }
}
