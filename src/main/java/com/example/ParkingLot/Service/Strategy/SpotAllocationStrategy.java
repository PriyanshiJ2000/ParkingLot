package com.example.ParkingLot.Service.Strategy;

import com.example.ParkingLot.Model.EntryGate;
import com.example.ParkingLot.Model.ParkingLot;
import com.example.ParkingLot.Model.ParkingSpot;
import com.example.ParkingLot.Model.Vehicle;

public interface SpotAllocationStrategy {
    public ParkingSpot findSpot(ParkingLot lot, Vehicle vehicle, EntryGate gate);
}
