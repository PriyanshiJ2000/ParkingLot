package com.example.ParkingLot.Service;

import java.util.logging.Level; // Import Level for standard Java logging
import java.util.logging.Logger;

import com.example.ParkingLot.Model.EntryGate;
import com.example.ParkingLot.Model.ParkingSpot;
import com.example.ParkingLot.Model.Ticket;
import com.example.ParkingLot.Model.Vehicle;

public class CheckinService {
    // Corrected Logger to use the fully qualified name in the import,
    // but the declaration itself is fine.
    private static final Logger logger = Logger.getLogger(CheckinService.class.getName());

    private final ParkingLotService parkingLotService;
    private final TicketService ticketService;

    public CheckinService(ParkingLotService parkingLotService, TicketService ticketService) {
        this.parkingLotService = parkingLotService;
        this.ticketService = ticketService;
    }

    public Ticket checkInAndGenerateTicket(Vehicle vehicle, EntryGate gate) {
        // Step 1: Find a spot and park the vehicle (Acquire resource)
        ParkingSpot spot = parkingLotService.findSpotAndParkVehicle(vehicle, gate);

        if (spot == null) {
            // Corrected logging: Use standard log method with Level.INFO.
            // Using logger.info(String, Object...) is a SLF4J/Log4j/Logback style,
            // not standard java.util.logging.
            logger.log(Level.INFO, "No Spot Available for vehicle type: {0}", vehicle.getType());
            return null;
        }

        Ticket ticket = null;
        try {
            long entryTime = System.currentTimeMillis();
            // Step 2: Generate Ticket (Critical path that can fail e.g., DB error)
            ticket = ticketService.generateTicket(spot, entryTime, vehicle);

            // Optional: Log successful ticket creation
            logger.log(Level.INFO, "Ticket generated successfully for vehicle: {0}", vehicle.getLicensePlate());

        } catch (Exception e) {
            // COMPENSATION (Rollback): If ticket creation fails, vacate the spot

            // Corrected logging: java.util.logging uses log(Level, String, Throwable) or
            // log(Level, String, Object[]) for parameterized logging.
            // We'll use log(Level.SEVERE, String, Throwable) to include the full stack
            // trace.
            logger.log(Level.SEVERE,
                    "Failed to generate ticket for vehicle " + vehicle.getLicensePlate()
                            + ". Initiating spot rollback.",
                    e);

            // Step 3: Undo the park action (compensating transaction)
            parkingLotService.unParkVehicle(spot);

            // Throw exception or return null to notify the caller (Controller)
            return null;
        }

        return ticket;
    }
}