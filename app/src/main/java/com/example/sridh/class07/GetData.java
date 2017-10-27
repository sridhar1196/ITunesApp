package com.example.sridh.class07;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;


public class GetData extends AsyncTask<String,Void,ArrayList<FilteredApp>> {
    Activity context;
    Context con;
    MainActivity activity;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public GetData(Activity context) {
        this.context = context;
        this.con = context;
        if (context instanceof MainActivity){
            activity = (MainActivity) context;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        context.setContentView(R.layout.loading);
    }

    @Override
    protected void onPostExecute(ArrayList<FilteredApp> favorites) {
        super.onPostExecute(favorites);
        if(favorites != null){
            if (favorites.size() > 0){
                if(context instanceof MainActivity) {
                    activity.setContentView(R.layout.activity_main);
                    Collections.sort(favorites);
                    activity.listView(activity,favorites);
                    DatabaseDataManager dm = new DatabaseDataManager(context);
                    ArrayList<FilteredApp> li = dm.getAllFilteredApps();
                    for(int x =0;x<li.size();x++){
                        for(int y =0;y<favorites.size();y++){
                            if(favorites.get(y).toString().trim().equals(li.get(x).toString().trim())){
                                favorites.remove(y);
                            }
                        }
                    }
                    activity.listView2(activity,li);
                }
            } else {
                if (context instanceof MainActivity){
                    context.setContentView(R.layout.activity_main);
                    Toast.makeText(context.getBaseContext(),"Search result for the entered keyword is empty", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            if (context instanceof MainActivity){
                context.setContentView(R.layout.activity_main);
                Toast.makeText(context.getBaseContext(),"Search result for the entered keyword is empty", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected ArrayList<FilteredApp> doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            Log.d("URL","Value"+strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int status = con.getResponseCode();
            if(status == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();

                while (line != null){
                    sb.append(line);
                    line = reader.readLine();
                }
                return ResultsUtil.ResultsJSONParser.parseFav(sb.toString(),context);
            }
            else {
                Toast.makeText(context,"Error in loading list", Toast.LENGTH_LONG).show();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}