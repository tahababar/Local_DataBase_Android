package com.depauw.repairshop.database;

public class Vehicle {

    private int vid;
    private String year;
    private String makeAndModel;
    private float purchasePrice;
    private int isNew;

    public Vehicle(int vid, String year, String makeAndModel, float purchasePrice, int isNew) {
        this.vid = vid;
        this.year = year;
        this.makeAndModel = makeAndModel;
        this.purchasePrice = purchasePrice;
        this.isNew = isNew;
    }

    public Vehicle(String year, String makeAndModel, float purchasePrice, int isNew) {
        this.year = year;
        this.makeAndModel = makeAndModel;
        this.purchasePrice = purchasePrice;
        this.isNew = isNew;
    }

    public int getVid() {
        return vid;
    }

    public String getYear() {
        return year;
    }

    public String getMakeAndModel() {
        return makeAndModel;
    }

    public float getPurchasePrice() {
        return purchasePrice;
    }

    public int getIsNew() {
        return isNew;
    }

    public String toString(){
        return this.year + " " + this.makeAndModel;
    }
}

