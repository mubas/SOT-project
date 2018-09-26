package com.arbab.sot.models;

public class Apartment {
    private int id;
    private String city;
    private int price;
    private boolean available;

    public Apartment() {}

    public Apartment(int id, int price, String city) {
        this.id = id;
        this.price = price;
        this.city = city;
        this.available = true;
    }

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
