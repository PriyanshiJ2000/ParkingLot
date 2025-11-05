package com.example.ParkingLot.Model;

public class Ticket {
    private final int id;
    private final ParkingSpot spot;
    private final long entryTime;
    private final Vehicle vehicle;

    public Ticket(int id, ParkingSpot spot, long entryTime, Vehicle vehicle) {
        this.id = id;
        this.spot = spot;
        this.entryTime = entryTime;
        this.vehicle = vehicle;
    }

    public int getId() {
        return id;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
