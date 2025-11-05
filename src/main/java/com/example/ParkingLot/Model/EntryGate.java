package com.example.ParkingLot.Model;

// Made immutable. Gates shouldn't change location or ID at runtime.
public class EntryGate {
    private final String gateId;
    private final String location; // Consider using Location class

    public EntryGate(String gateId, String location) {
        this.gateId = gateId;
        this.location = location;
    }

    public String getGateId() {
        return gateId;
    }

    public String getLocation() {
        return location;
    }
}