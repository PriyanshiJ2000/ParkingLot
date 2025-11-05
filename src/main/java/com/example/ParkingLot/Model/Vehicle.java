package com.example.ParkingLot.Model;

public class Vehicle {
    private String licensePlate;
    private VehicleType type;
    private double hourlyRate;

    public Vehicle(String licensePlate, VehicleType type, double hourlyRate) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.hourlyRate = hourlyRate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getType() {
        return type;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }
}
