package com.example.ParkingLot.Service.Strategy;

import com.example.ParkingLot.Model.Ticket;

public interface FeeCalculationStrategy {
    public double calculateFee(Ticket ticket);
}
