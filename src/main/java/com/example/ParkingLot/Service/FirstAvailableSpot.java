package com.example.ParkingLot.Service;

import java.util.List;

import com.example.ParkingLot.Model.EntryGate;
import com.example.ParkingLot.Model.ParkingFloors;
import com.example.ParkingLot.Model.ParkingLot;
import com.example.ParkingLot.Model.ParkingSpot;
import com.example.ParkingLot.Model.Vehicle;

public class FirstAvailableSpot implements SpotAllocationStrategy {
    @Override
    public ParkingSpot findSpot(ParkingLot lot, Vehicle vehicle, EntryGate gate) {
        // Implementation to find the first available spot
        for (ParkingFloors floor : lot.getFloors()) {
            List<ParkingSpot> spots = floor.getAvailableSpots(vehicle.getType());
            return spots.isEmpty() ? null : spots.get(0);
        }
        return null;
    }
}
