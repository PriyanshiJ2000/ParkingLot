package com.example.ParkingLot.Service;

import com.example.ParkingLot.Model.Ticket;

public interface FeeCalculationStrategy {
    public double calculateFee(Ticket ticket);
}
