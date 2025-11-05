package com.example.ParkingLot.Service;

import com.example.ParkingLot.Model.EntryGate;
import com.example.ParkingLot.Model.ParkingLot;
import com.example.ParkingLot.Model.ParkingSpot;
import com.example.ParkingLot.Model.Vehicle;

public class ParkingLotService {
    public final ParkingLot parkingLot;
    public final SpotAllocationStrategy spotAllocationStrategy;

    public ParkingLotService(ParkingLot parkingLot, SpotAllocationStrategy spotAllocationStrategy) {
        this.parkingLot = parkingLot;
        this.spotAllocationStrategy = spotAllocationStrategy;
    }

    public ParkingSpot findSpotAndParkVehicle(Vehicle vehicle, EntryGate gate) {
        ParkingSpot spot = spotAllocationStrategy.findSpot(parkingLot, vehicle, gate);
        if (spot != null) {
            boolean parked = spot.park(vehicle);
            if (parked) {
                return spot;
            }
        }
        return null;
    }

    public void unParkVehicle(ParkingSpot spot) {
        if (spot != null) {
            spot.vacate();
        }
        return;
    }
}
