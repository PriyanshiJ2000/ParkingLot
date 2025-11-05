package com.example.ParkingLot.Repository;

import com.example.ParkingLot.Model.ParkingLot;

// SDE-2 Note: This simulates loading the parking lot configuration.
public class InMemoryParkingLotRepository implements ParkingLotRepository {
    private final ParkingLot parkingLot;

    // The entire lot object is injected on startup/creation
    public InMemoryParkingLotRepository(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    @Override
    public ParkingLot getParkingLot() {
        return parkingLot;
    }
}