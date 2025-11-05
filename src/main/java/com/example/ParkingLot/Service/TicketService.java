package com.example.ParkingLot.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.ParkingLot.Model.ParkingSpot;
import com.example.ParkingLot.Model.Ticket;
import com.example.ParkingLot.Model.Vehicle;

public class TicketService {
    private final List<Ticket> ticketHistory;
    private final AtomicInteger ticketIdCounter = new AtomicInteger(0);

    public TicketService() {
        this.ticketHistory = new java.util.ArrayList<>();
    }

    public Ticket generateTicket(ParkingSpot spot, long entryTime, Vehicle vehicle) {
        int ticketId = ticketIdCounter.incrementAndGet();
        Ticket ticket = new Ticket(ticketId, spot, entryTime, vehicle);
        synchronized (ticketHistory) {
            ticketHistory.add(ticket);
        }
        return ticket;
    }
}
