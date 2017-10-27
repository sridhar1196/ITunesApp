package com.example.sridh.class07;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ArrayList<FilteredApp> filteredApps;
    ArrayList<FilteredApp> results;
    Switch mSwitch;
    ImageButton refresh;
    RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        if(isConnected()){
            new GetData(this).execute("http://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
        } else {
            Toast.makeText(getBaseContext(),"Network is not connected",Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_main);
        }


        //filtered = (ListView) findViewById(R.id.filteredListView);

    }

    public void delete(FilteredApp filteredApp){
        filteredApp.setSmallImageURL(filteredApp.getLargeImageURL());
        results.add(filteredApp);
        if(mSwitch.isChecked()){
            Collections.sort(results);
        } else {
            Collections.sort(results, new PriceComparator());
        }
        mAdapter.notifyDataSetChanged();
    }

    public void listView(Activity activity, ArrayList<FilteredApp> favorites) {
        //RecyclerView mRecyclerView;
        results = favorites;
        Log.d("results", favorites.toString());
        RecyclerView.LayoutManager mLayoutManager;
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.resultsListView);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout managerq
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ResultsListAdapter(this, R.layout.results, favorites);
        mRecyclerView.setAdapter(mAdapter);
        mSwitch = (Switch) findViewById(R.id.ac2dc);
        mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSwitch.isChecked()) {
                    mSwitch.setText(R.string.switch_on);
                    Collections.sort(results);
                    mAdapter.notifyDataSetChanged();
                }
                else {
                    mSwitch.setText(R.string.switch_off);
                    Collections.sort(results, new PriceComparator());
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        refresh = (ImageButton) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetData(MainActivity.this).execute("http://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
            }
        });
    }

    public void listView2(Activity activity, ArrayList<FilteredApp> favorites){
        //RecyclerView mRecyclerView;
//        Log.d("results",favorites.toString());
        filteredApps = favorites;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;
        RecyclerView mRecyclerView=(RecyclerView) findViewById(R.id.filteredListView);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout managerq
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new FilteredListAdapter(this, R.layout.results, favorites);
        mRecyclerView.setAdapter(mAdapter);
    }

    public Boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cm.    getActiveNetworkInfo();
        if(nf != null && nf.isConnected()){
            return true;
        } else {
            return false;
        }
    }
}
