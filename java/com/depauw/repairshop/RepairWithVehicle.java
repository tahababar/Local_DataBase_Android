package com.depauw.repairshop;

import com.depauw.repairshop.database.Repair;
import com.depauw.repairshop.database.Vehicle;

public class RepairWithVehicle {

    private Repair repair;
    private Vehicle vehicle;

    public RepairWithVehicle(Repair repair, Vehicle vehicle) {
        this.repair = repair;
        this.vehicle = vehicle;
    }

    public Repair getRepair() {
        return repair;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
