package com.example.ParkingLot.Model;

import java.util.concurrent.locks.ReentrantLock;

public class ParkingSpot {
    private final int id;
    private final VehicleType type;
    private final Location location;
    private final ParkingFloors floor;
    private volatile Vehicle vehicle;
    private final ReentrantLock lock = new ReentrantLock(true);

    public ParkingSpot(int id, VehicleType type, Location location, ParkingFloors floor, double hourlyRate) {
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

    public ParkingFloors getFloor() {
        return floor;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isAvailable() {
        lock.lock();
        try {
            if (vehicle == null)
                return true;
            return false;
        } finally {
            lock.unlock();
        }
    }

    public boolean park(Vehicle vehicle) {
        lock.lock();
        try {
            if (this.isAvailable()) {
                this.vehicle = vehicle;
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
            this.vehicle = null;
            floor.addAvailableSpot(type, this);
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
