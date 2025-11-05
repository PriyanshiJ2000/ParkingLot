package com.example.ParkingLot.Service;

import com.example.ParkingLot.Model.Ticket;

public class CheckoutService {
    private final ParkingLotService parkingLotService;
    private final FeeCalculationStrategy feeCalculationStrategy;

    public CheckoutService(ParkingLotService parkingLotService, FeeCalculationStrategy feeCalculationStrategy) {
        this.parkingLotService = parkingLotService;
        this.feeCalculationStrategy = feeCalculationStrategy;
    }

    public double calculateFee(Ticket ticket) {
        return feeCalculationStrategy.calculateFee(ticket);
    }

    public double checkoutVehicleAndProcessPayment(Ticket ticket) {
        double fee = calculateFee(ticket);
        parkingLotService.unParkVehicle(ticket.getSpot());
        return fee;
    }
}
