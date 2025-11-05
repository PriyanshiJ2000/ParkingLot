package com.example.ParkingLot.Service.Strategy;

import java.util.Queue;

import com.example.ParkingLot.Model.EntryGate;
import com.example.ParkingLot.Model.ParkingFloor;
import com.example.ParkingLot.Model.ParkingLot;
import com.example.ParkingLot.Model.ParkingSpot;
import com.example.ParkingLot.Model.Vehicle;

public class FirstAvailableSpot implements SpotAllocationStrategy {
    @Override
    public ParkingSpot findSpot(ParkingLot lot, Vehicle vehicle, EntryGate gate) {
        for (ParkingFloor floor : lot.getFloors()) {
            // Get the Queue of available spots for the vehicle type
            Queue<ParkingSpot> spotsQueue = floor.getAvailableSpotQueue(vehicle.getType());

            // Peek at the first element (doesn't remove it).
            // The parking attempt (and removal from the floor's list) happens
            // inside ParkingLotService/ParkingSpot.
            ParkingSpot spot = spotsQueue.peek();

            if (spot != null) {
                return spot; // Found the first available spot
            }
        }
        return null; // No spot found on any floor
    }
}