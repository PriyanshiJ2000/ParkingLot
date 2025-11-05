package com.example.ParkingLot.Model;

public class Vehicle {
    private final String licensePlate;
    private final VehicleType type;

    // Removed hourlyRate: the rate is determined by the
    // FeeCalculationStrategy/Policy,
    // not by the vehicle object.
    public Vehicle(String licensePlate, VehicleType type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getType() {
        return type;
    }
}