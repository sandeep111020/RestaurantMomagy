package com.example.restaurant.Models;

public class Mylatlong {
    String temp,lat,longt;

    public Mylatlong(){

    }
    public Mylatlong(String temp, String lat,String longt){
        this.temp=temp;
        this.lat=lat;
        this.longt=longt;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongt() {
        return longt;
    }

    public void setLongt(String longt) {
        this.longt = longt;
    }
}
