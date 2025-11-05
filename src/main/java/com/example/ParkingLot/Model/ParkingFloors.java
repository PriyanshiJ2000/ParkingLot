package com.example.ParkingLot.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingFloors {
    private final int level;
    private final List<ParkingSpot> spots;
    private final Map<VehicleType, List<ParkingSpot>> availableSpots = new ConcurrentHashMap<>();

    public ParkingFloors(int level, List<ParkingSpot> spots) {
        this.level = level;
        this.spots = spots;
    }

    public int getLevel() {
        return level;
    }

    public List<ParkingSpot> getSpots() {
        return spots;
    }

    public List<ParkingSpot> getAvailableSpots(VehicleType type) {
        return availableSpots.getOrDefault(type, Collections.emptyList());
    }

    public void addAvailableSpot(VehicleType type, ParkingSpot spot) {
        availableSpots.computeIfAbsent(type, k -> Collections.synchronizedList(new ArrayList<>())).add(spot);
    }

    public void removeAvailableSpot(VehicleType type, ParkingSpot spot) {
        List<ParkingSpot> spotsOfType = availableSpots.get(type);
        if (spotsOfType != null) {
            if (spot == null) {
                spotsOfType.removeLast();
            } else {
                spotsOfType.remove(spot);
            }
        }
    }
}