package com.depauw.repairshop.database;

public class Repair {

    private int rid;
    private String date;
    private float cost;
    private String description;
    private int vehicleId;

    public Repair(int rid, String date, float cost, String description, int vehicleId) {
        this.rid = rid;
        this.date = date;
        this.cost = cost;
        this.description = description;
        this.vehicleId = vehicleId;
    }

    public Repair(String date, float cost, String description, int vehicleId) {
        this.date = date;
        this.cost = cost;
        this.description = description;
        this.vehicleId = vehicleId;
    }

    public int getRid() {
        return rid;
    }

    public String getDate() {
        return date;
    }

    public float getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public int getVehicleId() {
        return vehicleId;
    }
}
