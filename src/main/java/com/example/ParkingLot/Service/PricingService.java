package com.example.ParkingLot.Service;

import com.example.ParkingLot.Model.Ticket;
import com.example.ParkingLot.Repository.RateRepository;

public class PricingService {
    private final RateRepository rateRepository;

    public PricingService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public double getRate(Ticket ticket) {
        // Can be extended to check time of day, floor, etc.
        return rateRepository.getHourlyRate(ticket.getVehicle().getType());
    }
}