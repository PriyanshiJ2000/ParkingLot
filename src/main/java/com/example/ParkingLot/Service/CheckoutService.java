package com.example.ParkingLot.Service;

import com.example.ParkingLot.Model.Ticket;
import com.example.ParkingLot.Repository.TicketRepository;
import com.example.ParkingLot.Service.Strategy.FeeCalculationStrategy;

public class CheckoutService {
    private final ParkingLotService parkingLotService;
    private final FeeCalculationStrategy feeCalculationStrategy;
    private final TicketRepository ticketRepository;

    public CheckoutService(
            ParkingLotService parkingLotService,
            FeeCalculationStrategy feeCalculationStrategy,
            TicketRepository ticketRepository) {
        this.parkingLotService = parkingLotService;
        this.feeCalculationStrategy = feeCalculationStrategy;
        this.ticketRepository = ticketRepository;
    }

    public double calculateFee(int ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
        return feeCalculationStrategy.calculateFee(ticket);
    }

    public double checkoutVehicleAndProcessPayment(int ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

        double fee = calculateFee(ticketId);

        // 1. Process Payment (Placeholder: Assume success for LLD)
        // paymentGatewayService.process(fee);

        // 2. Vacate the spot
        parkingLotService.unParkVehicle(ticket.getSpot());

        // Note: In a full system, you would update the ticket status in the repository
        // here.

        return fee;
    }
}