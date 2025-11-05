package com.example.ParkingLot.Service;

import java.util.Optional;

import com.example.ParkingLot.Model.ParkingSpot;
import com.example.ParkingLot.Model.Ticket;
import com.example.ParkingLot.Model.Vehicle;
import com.example.ParkingLot.Repository.TicketRepository;

public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket generateTicket(ParkingSpot spot, long entryTime, Vehicle vehicle) {
        int ticketId = ticketRepository.getNextId();
        Ticket ticket = new Ticket(ticketId, spot, entryTime, vehicle);

        return ticketRepository.save(ticket);
    }

    public Optional<Ticket> getTicket(int ticketId) {
        return ticketRepository.findById(ticketId);
    }
}