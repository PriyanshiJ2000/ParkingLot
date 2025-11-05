package com.example.ParkingLot.Model;

public class EntryGate {
    private String gateId;
    private String location;

    public EntryGate(String gateId, String location) {
        this.gateId = gateId;
        this.location = location;
    }

    public String getGateId() {
        return gateId;
    }

    public void setGateId(String gateId) {
        this.gateId = gateId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
