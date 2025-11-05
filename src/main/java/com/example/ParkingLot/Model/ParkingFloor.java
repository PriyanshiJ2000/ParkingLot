package com.example.ParkingLot.Model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue; // Optimized for high-concurrency queue operations

public class ParkingFloor {
    private final int level;
    private final List<ParkingSpot> allSpots;

    // Change value type to Queue for better concurrency performance on frequent
    // adds/removals
    private final Map<VehicleType, Queue<ParkingSpot>> availableSpots = new ConcurrentHashMap<>();

    public ParkingFloor(int level, List<ParkingSpot> allSpots) {
        this.level = level;
        this.allSpots = Collections.unmodifiableList(allSpots);

        // Initialize available spots using ConcurrentLinkedQueue
        for (ParkingSpot spot : allSpots) {
            availableSpots
                    .computeIfAbsent(spot.getType(), k -> new ConcurrentLinkedQueue<>())
                    .offer(spot); // Use offer for adding to a Queue
        }
    }

    public void addAvailableSpot(VehicleType type, ParkingSpot spot) {
        availableSpots
                .computeIfAbsent(type, k -> new ConcurrentLinkedQueue<>())
                .offer(spot); // Adds the spot safely
    }

    public void removeAvailableSpot(VehicleType type, ParkingSpot spot) {
        Queue<ParkingSpot> queue = availableSpots.get(type);
        if (queue != null) {
            // Note: Queue's remove(Object) is inherently less performant than offer/poll
            // but is necessary here to remove a specific spot after it's been selected.
            // This is still better than CopyOnWriteArrayList's full array copy.
            queue.remove(spot);
        }
    }

    /**
     * Retrieves a reference to the available spot queue for a given type.
     * The SpotAllocationStrategy will decide whether to 'poll' (remove the head)
     * or iterate to find a specific spot.
     */
    public Queue<ParkingSpot> getAvailableSpotQueue(VehicleType type) {
        // Return the queue, or an empty queue for safety if the type isn't found
        return availableSpots.getOrDefault(type, new ConcurrentLinkedQueue<>());
    }

    // Getter for all spots
    public List<ParkingSpot> getAllSpots() {
        return allSpots;
    }

    public int getLevel() {
        return level;
    }
}