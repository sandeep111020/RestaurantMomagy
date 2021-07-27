package com.example.restaurant.Models;

import com.example.restaurant.DeliveryBoysHome;

public class Delieverylatlong {
    String dlat;
    String dlong;

    public Delieverylatlong(){

    }
    public Delieverylatlong(String dlat,String dlong){
        this.dlat=dlat;
        this.dlong=dlong;
    }

    public String getDlat() {
        return dlat;
    }

    public void setDlat(String dlat) {
        this.dlat = dlat;
    }

    public String getDlong() {
        return dlong;
    }

    public void setDlong(String dlong) {
        this.dlong = dlong;
    }


}
