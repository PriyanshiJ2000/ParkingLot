package com.example.ParkingLot.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.ParkingLot.Model.Ticket;

public class InMemoryTicketRepository implements TicketRepository {
    private final Map<Integer, Ticket> ticketStore = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    @Override
    public Ticket save(Ticket ticket) {
        // Tickets are assumed to be immutable once created
        ticketStore.put(ticket.getId(), ticket);
        return ticket;
    }

    @Override
    public Optional<Ticket> findById(int id) {
        return Optional.ofNullable(ticketStore.get(id));
    }

    @Override
    public int getNextId() {
        return idCounter.incrementAndGet();
    }
}