package com.example.ParkingLot.Controller;

import com.example.ParkingLot.DTO.CheckinRequestDTO;
import com.example.ParkingLot.DTO.TicketResponseDTO;
import com.example.ParkingLot.Model.EntryGate;
import com.example.ParkingLot.Model.Ticket;
import com.example.ParkingLot.Model.Vehicle;
import com.example.ParkingLot.Model.VehicleType;
import com.example.ParkingLot.Service.CheckinService;
import com.example.ParkingLot.Service.CheckoutService;

// SDE-2 Note: This Controller converts DTOs to Models and handles service calls.
public class ParkingLotController {
    private final CheckinService checkinService;
    private final CheckoutService checkoutService;

    public ParkingLotController(CheckinService checkinService, CheckoutService checkoutService) {
        this.checkinService = checkinService;
        this.checkoutService = checkoutService;
    }

    /**
     * Handles the vehicle check-in process.
     * 
     * @param request The incoming request DTO.
     * @return A response DTO containing the new ticket details.
     */
    public TicketResponseDTO checkIn(CheckinRequestDTO request) {
        // 1. Data Conversion (DTO to Model)
        // Note: In a real system, EntryGate would be looked up via a GateRepository
        EntryGate gate = new EntryGate(request.gateId, "Dummy Location");

        Vehicle vehicle = new Vehicle(
                request.licensePlate,
                VehicleType.valueOf(request.vehicleType.toUpperCase()));

        // 2. Business Logic Call
        Ticket ticket = checkinService.checkInAndGenerateTicket(vehicle, gate);

        if (ticket == null) {
            // Throw appropriate application exception (e.g., ParkingFullException)
            throw new RuntimeException("404: No space available for " + request.vehicleType);
        }

        // 3. Response Conversion (Model to DTO)
        TicketResponseDTO response = new TicketResponseDTO();
        response.ticketId = ticket.getId();
        response.entryTime = ticket.getEntryTime();
        response.spotId = String.valueOf(ticket.getSpot().getId());

        return response;
    }

    /**
     * Handles the vehicle checkout and payment process.
     * 
     * @param ticketId The ID of the ticket to checkout.
     * @return The final fee charged.
     */
    public double checkOut(int ticketId) {
        // Business logic call handles fee calculation, payment, and unparking.
        return checkoutService.checkoutVehicleAndProcessPayment(ticketId);
    }
}