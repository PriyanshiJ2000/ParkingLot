package com.example.ParkingLot.Repository;

import java.util.Optional;

import com.example.ParkingLot.Model.Ticket;

public interface TicketRepository {
    Ticket save(Ticket ticket);

    Optional<Ticket> findById(int id);

    int getNextId(); // Helper method moved here for ID generation responsibility
}