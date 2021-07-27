package com.example.restaurant.Models;


public class ItemModel{


    String itemname,itemdesc,itemprice,itemtype,itemcheck,itemimage;

    public ItemModel() {
    }

    public ItemModel(String itemname,String itemdesc,String itemprice,String itemtype,String itemcheck,String itemimage) {
        this.itemname=itemname;
        this.itemdesc=itemdesc;
        this.itemprice=itemprice;
        this.itemtype=itemtype;
        this.itemcheck=itemcheck;
        this.itemimage=itemimage;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemdesc() {
        return itemdesc;
    }

    public void setItemdesc(String itemdesc) {
        this.itemdesc = itemdesc;
    }

    public String getItemprice() {
        return itemprice;
    }

    public void setItemprice(String itemprice) {
        this.itemprice = itemprice;
    }

    public String getItemtype() {
        return itemtype;
    }

    public void setItemtype(String itemtype) {
        this.itemtype = itemtype;
    }

    public String getItemcheck() {
        return itemcheck;
    }

    public void setItemcheck(String itemcheck) {
        this.itemcheck = itemcheck;
    }

    public String getItemimage() {
        return itemimage;
    }

    public void setItemimage(String itemimage) {
        this.itemimage = itemimage;
    }




}