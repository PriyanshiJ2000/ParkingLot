package com.example.ParkingLot.Model;

import java.util.concurrent.locks.ReentrantLock;

public class ParkingSpot {
    private final int id;
    private final VehicleType type;
    private final Location location;
    private final ParkingFloor floor;
    private volatile Vehicle vehicle;
    private final ReentrantLock lock = new ReentrantLock(true);

    public ParkingSpot(int id, VehicleType type, Location location, ParkingFloor floor) {
        this.id = id;
        this.type = type;
        this.location = location;
        this.floor = floor;
    }

    public int getId() {
        return id;
    }

    public VehicleType getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingFloor getFloor() {
        return floor;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    // Helper method (no lock needed here as it's a read-only check)
    public boolean isAvailable() {
        return this.vehicle == null;
    }

    // Simplified: Check and park happens atomically under one lock acquisition
    public boolean park(Vehicle vehicle) {
        lock.lock();
        try {
            if (this.vehicle == null) {
                this.vehicle = vehicle;
                // Important: Update the floor's list of available spots
                floor.removeAvailableSpot(type, this);
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public void vacate() {
        lock.lock();
        try {
            // Check if a vehicle is actually present before vacating
            if (this.vehicle != null) {
                this.vehicle = null;
                // Important: Update the floor's list of available spots
                floor.addAvailableSpot(type, this);
            }
        } finally {
            lock.unlock();
        }
    }

    public double calculateDistance(Location location) {
        double dLat = this.location.getLatitude() - location.getLatitude();
        double dLon = this.location.getLongitude() - location.getLongitude();

        return Math.sqrt(Math.pow(dLat, 2) + Math.pow(dLon, 2));
    }
}
