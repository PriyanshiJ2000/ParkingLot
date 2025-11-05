package com.example.ParkingLot.Model;

import java.util.Collections;
import java.util.List;

public class ParkingLot {
    private final List<EntryGate> entryGates;
    private final List<ParkingFloor> floors; // Changed to use the consolidated ParkingFloor class

    public ParkingLot(List<ParkingFloor> floors, List<EntryGate> entryGates) {
        // Use unmodifiable lists to ensure the parking lot structure is immutable after
        // creation.
        this.floors = Collections.unmodifiableList(floors);
        this.entryGates = Collections.unmodifiableList(entryGates);
    }

    public List<ParkingFloor> getFloors() {
        return floors;
    }

    public List<EntryGate> getEntryGates() {
        return entryGates;
    }
}