package com.example.ParkingLot.Service.Strategy;

import com.example.ParkingLot.Model.Ticket;
import com.example.ParkingLot.Service.PricingService;

public class HourlyCalculationStrategy implements FeeCalculationStrategy {
    private final PricingService pricingService; // Dependency Injection

    // Inject the PricingService instead of a fixed rate
    public HourlyCalculationStrategy(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @Override
    public double calculateFee(Ticket ticket) {
        long timeElapsedMillis = System.currentTimeMillis() - ticket.getEntryTime();

        long totalSeconds = timeElapsedMillis / 1000;
        long hoursParked = (long) Math.ceil(totalSeconds / 3600.0);

        // Ensure minimum 1 hour charge
        if (hoursParked == 0) {
            hoursParked = 1;
        }

        // Dynamic rate lookup
        double ratePerHour = pricingService.getRate(ticket);

        return hoursParked * ratePerHour;
    }
}