package com.example.ParkingLot.Repository;

import com.example.ParkingLot.Model.VehicleType;

public interface RateRepository {
    double getHourlyRate(VehicleType type);
}