package com.example.sridh.class07;


import android.support.annotation.NonNull;

public class FilteredApp implements Comparable<FilteredApp>
{
    private String name;
    private double price;
    private String thumb_url;
    private String smallImageURL;

    public FilteredApp(String name, double price, String thumb_url) {
        this.name = name;
        this.price = price;
        this.largeImageURL = thumb_url;
    }
    public String getSmallImageURL() {
        return smallImageURL;
    }

    public void setSmallImageURL(String smallImageURL) {
        this.smallImageURL = smallImageURL;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    private String largeImageURL;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    @Override
    public String toString() {
        return "FilteredApp{" +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", largeImageURL='" + largeImageURL + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NonNull FilteredApp filteredApp) {
        if(this.getPrice()>filteredApp.getPrice())
        {
            return 1;
        }
        else
            return -1;
    }
}
