package com.example.ParkingLot.Service;

import com.example.ParkingLot.Model.EntryGate;
import com.example.ParkingLot.Model.ParkingSpot;
import com.example.ParkingLot.Model.Ticket;
import com.example.ParkingLot.Model.Vehicle;

public class CheckinService {
    private final ParkingLotService parkingLotService;
    public TicketService ticketService;

    public CheckinService(ParkingLotService parkingLotService, TicketService ticketService) {
        this.parkingLotService = parkingLotService;
        this.ticketService = ticketService;
    }

    public Ticket checkInAndGenerateTicket(Vehicle vehicle, EntryGate gate) {
        ParkingSpot spot = parkingLotService.findSpotAndParkVehicle(vehicle, gate);
        if (spot != null) {
            long entryTime = System.currentTimeMillis();
            return ticketService.generateTicket(spot, entryTime, vehicle);
        }
        System.out.println("No Spot Available");
        return null;
    }
}
