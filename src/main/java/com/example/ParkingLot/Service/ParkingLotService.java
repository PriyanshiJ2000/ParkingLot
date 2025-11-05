package com.example.ParkingLot.Service;

import com.example.ParkingLot.Model.EntryGate;
import com.example.ParkingLot.Model.ParkingLot;
import com.example.ParkingLot.Model.ParkingSpot;
import com.example.ParkingLot.Model.Vehicle;
import com.example.ParkingLot.Repository.ParkingLotRepository;
import com.example.ParkingLot.Service.Strategy.SpotAllocationStrategy;

public class ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;
    private final SpotAllocationStrategy spotAllocationStrategy;

    public ParkingLotService(ParkingLotRepository parkingLotRepository, SpotAllocationStrategy spotAllocationStrategy) {
        this.parkingLotRepository = parkingLotRepository;
        this.spotAllocationStrategy = spotAllocationStrategy;
    }

    /**
     * Finds a spot using the strategy and attempts to park the vehicle.
     * The ParkingSpot handles the concurrency lock and floor list update.
     * 
     * @return The ParkingSpot if successful, otherwise null.
     */
    public ParkingSpot findSpotAndParkVehicle(Vehicle vehicle, EntryGate gate) {
        ParkingLot parkingLot = parkingLotRepository.getParkingLot();

        // 1. Allocation (Strategy Pattern)
        ParkingSpot spot = spotAllocationStrategy.findSpot(parkingLot, vehicle, gate);

        if (spot != null) {
            // 2. Transactional Parking (ParkingSpot lock ensures atomicity)
            boolean parked = spot.park(vehicle);

            if (parked) {
                return spot;
            }
            // If spot.park(vehicle) returns false, another thread beat us to it.
        }
        return null;
    }

    public void unParkVehicle(ParkingSpot spot) {
        if (spot != null) {
            spot.vacate();
        }
    }
}