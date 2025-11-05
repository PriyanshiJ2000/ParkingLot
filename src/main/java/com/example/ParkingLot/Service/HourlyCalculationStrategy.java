package com.example.ParkingLot.Service;

import com.example.ParkingLot.Model.Ticket;

public class HourlyCalculationStrategy implements FeeCalculationStrategy {
    public double calculateFee(Ticket ticket) {
        long timeElapsed = System.currentTimeMillis() - ticket.getEntryTime();
        long hoursParked = timeElapsed / (1000 * 60 * 60);
        double ratePerHour = 10.0; // Example rate
        return hoursParked * ratePerHour;
    }
}
