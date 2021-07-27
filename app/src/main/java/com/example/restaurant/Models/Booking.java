package com.example.restaurant.Models;

import java.util.ArrayList;

public class Booking {

    String name;
    String number;
    String address;
    String area;
    String city;
    String state;
    String pin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String price;
    String id;
    ArrayList<String> bookeddata;


    public Booking(){

    }
    public Booking(String name,String number,String address,String area,String city,String state,String pin,String price,ArrayList bookeddata,String id){
        this.name=name;
        this.number=number;
        this.address=address;
        this.area=area;
        this.city=city;
        this.state=state;
        this.id=id;

        this.pin=pin;
        this.price=price;
        this.bookeddata=bookeddata;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<String> getBookeddata() {
        return bookeddata;
    }

    public void setBookeddata(ArrayList<String> bookeddata) {
        this.bookeddata = bookeddata;
    }


}
