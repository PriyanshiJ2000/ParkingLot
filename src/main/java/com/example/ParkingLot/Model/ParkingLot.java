package com.example.ParkingLot.Model;

import java.util.List;

public class ParkingLot {
    public final List<EntryGate> entryGates;
    private final List<ParkingFloors> floors;

    public ParkingLot(List<ParkingFloors> floors, List<EntryGate> entryGates) {
        this.floors = floors;
        this.entryGates = entryGates;
    }

    public List<ParkingFloors> getFloors() {
        return floors;
    }

    public List<EntryGate> getEntryGates() {
        return entryGates;
    }
}
