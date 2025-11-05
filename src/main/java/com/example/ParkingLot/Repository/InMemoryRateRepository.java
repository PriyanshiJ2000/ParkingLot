package com.example.ParkingLot.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.example.ParkingLot.Model.VehicleType;

public class InMemoryRateRepository implements RateRepository {
    // Example fixed rates based on type
    private final Map<VehicleType, Double> rates = new ConcurrentHashMap<>();

    public InMemoryRateRepository() {
        // Rates would typically be loaded from a configuration file/DB
        rates.put(VehicleType.CAR, 20.0);
        rates.put(VehicleType.MOTORCYCLE, 10.0);
        rates.put(VehicleType.TRUCK, 50.0);
        rates.put(VehicleType.BUS, 60.0);
        rates.put(VehicleType.SCOOTER, 8.0);
    }

    @Override
    public double getHourlyRate(VehicleType type) {
        // Default to a high rate or throw an exception if type is unknown
        return rates.getOrDefault(type, 30.0);
    }
}