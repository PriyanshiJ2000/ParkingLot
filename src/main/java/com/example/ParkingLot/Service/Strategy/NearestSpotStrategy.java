package com.example.ParkingLot.Service.Strategy;

import java.util.Queue;

import com.example.ParkingLot.Model.EntryGate;
import com.example.ParkingLot.Model.Location;
import com.example.ParkingLot.Model.ParkingFloor;
import com.example.ParkingLot.Model.ParkingLot;
import com.example.ParkingLot.Model.ParkingSpot;
import com.example.ParkingLot.Model.Vehicle;

public class NearestSpotStrategy implements SpotAllocationStrategy {
    @Override
    public ParkingSpot findSpot(ParkingLot lot, Vehicle vehicle, EntryGate gate) {
        ParkingSpot bestSpot = null;
        double minDistance = Double.MAX_VALUE;

        // Assuming EntryGate is updated to use the Location model
        // For LLD purposes, we'll create a dummy Location object based on the string
        Location gateLocation = new Location(9.9, 19.9);

        for (ParkingFloor floor : lot.getFloors()) {
            Queue<ParkingSpot> spotsQueue = floor.getAvailableSpotQueue(vehicle.getType());

            // CONCURRENCY FIX: Work on a snapshot (array copy) of the queue.
            // This ensures the iteration completes without interference if another
            // thread parks/unparks mid-search.
            ParkingSpot[] spotSnapshot = spotsQueue.toArray(new ParkingSpot[0]);

            for (ParkingSpot spot : spotSnapshot) {
                if (spot == null)
                    continue; // Safety check for nulls during iteration

                double distance = spot.calculateDistance(gateLocation);

                if (distance < minDistance) {
                    minDistance = distance;
                    bestSpot = spot;
                }
            }
        }
        return bestSpot;
    }
}