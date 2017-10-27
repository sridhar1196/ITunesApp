package com.example.sridh.class07;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ResultsUtil {
    static public class ResultsJSONParser {
        static ArrayList<FilteredApp> parseFav(String is, Activity context) throws JSONException {
            String mname = "";
            String url = "";
            double mprice = 0;
            ArrayList<FilteredApp> FavoritesList = new ArrayList<FilteredApp>();
            if(context instanceof MainActivity){
                JSONObject root = new JSONObject(is);
                JSONObject results = root.getJSONObject("feed");
                JSONArray q = results.getJSONArray("entry");
                for(int i =0;i<q.length();i++){
                    JSONObject jsonObject = q.getJSONObject(i);
                    FilteredApp favorites = new FilteredApp(mname, mprice, url);
                    JSONObject name = jsonObject.getJSONObject("im:name");
                    favorites.setName(name.getString("label"));
                    JSONArray image = jsonObject.getJSONArray("im:image");
                    for(int x =0;x<image.length();x++){
                        JSONObject image_value = image.getJSONObject(x);
                        String image_url = image_value.getString("label");
                        JSONObject attributes = image_value.getJSONObject("attributes");
                        int height = Integer.parseInt(attributes.getString("height"));
                        if(height == 53){
                            favorites.setSmallImageURL(image_url);
                        } else if(height == 100){
                            favorites.setLargeImageURL(image_url);
                        }
                    }
                    JSONObject price = jsonObject.getJSONObject("im:price");
                    JSONObject att = price.getJSONObject("attributes");
                    favorites.setPrice(Double.parseDouble(att.getString("amount")));
                    FavoritesList.add(favorites);
                }
            }

            return FavoritesList;
        }
    }
}