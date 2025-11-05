package com.example.ParkingLot.Repository;

import com.example.ParkingLot.Model.ParkingLot;

// Interface to manage the single ParkingLot instance
public interface ParkingLotRepository {
    ParkingLot getParkingLot();

    // In a real system, configuration methods would be here:
    // Optional<ParkingFloor> findFloorById(int floorId);
}