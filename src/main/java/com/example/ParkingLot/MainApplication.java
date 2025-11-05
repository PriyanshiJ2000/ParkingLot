package com.example.ParkingLot;

import java.util.Arrays;
import java.util.List;

import com.example.ParkingLot.Controller.ParkingLotController;
import com.example.ParkingLot.DTO.CheckinRequestDTO;
import com.example.ParkingLot.DTO.TicketResponseDTO;
import com.example.ParkingLot.Model.EntryGate;
import com.example.ParkingLot.Model.Location;
import com.example.ParkingLot.Model.ParkingFloor;
import com.example.ParkingLot.Model.ParkingLot;
import com.example.ParkingLot.Model.ParkingSpot;
import com.example.ParkingLot.Model.VehicleType;
import com.example.ParkingLot.Repository.InMemoryParkingLotRepository;
import com.example.ParkingLot.Repository.InMemoryRateRepository;
import com.example.ParkingLot.Repository.InMemoryTicketRepository;
import com.example.ParkingLot.Repository.ParkingLotRepository;
import com.example.ParkingLot.Repository.RateRepository;
import com.example.ParkingLot.Repository.TicketRepository;
import com.example.ParkingLot.Service.CheckinService;
import com.example.ParkingLot.Service.CheckoutService;
import com.example.ParkingLot.Service.ParkingLotService;
import com.example.ParkingLot.Service.PricingService;
import com.example.ParkingLot.Service.TicketService;
import com.example.ParkingLot.Service.Strategy.FeeCalculationStrategy;
import com.example.ParkingLot.Service.Strategy.HourlyCalculationStrategy;
import com.example.ParkingLot.Service.Strategy.NearestSpotStrategy;
import com.example.ParkingLot.Service.Strategy.SpotAllocationStrategy;

public class MainApplication {

    public static void main(String[] args) {
        System.out.println("--- Parking Lot System Startup and DI Setup ---");

        // --- 1. Setup Models (Structural Data) ---
        Location spotLoc1 = new Location(10.0, 20.0);
        Location spotLoc2 = new Location(10.1, 20.1);
        Location gateLoc = new Location(9.9, 19.9);
        EntryGate entryGate1 = new EntryGate("E1", gateLoc.toString());

        ParkingFloor floor1 = new ParkingFloor(1, Arrays.asList());
        ParkingSpot spot1 = new ParkingSpot(101, VehicleType.CAR, spotLoc1, floor1);
        ParkingSpot spot2 = new ParkingSpot(102, VehicleType.CAR, spotLoc2, floor1);
        ParkingSpot spot3 = new ParkingSpot(103, VehicleType.MOTORCYCLE, spotLoc1, floor1);
        ParkingSpot spot4 = new ParkingSpot(104, VehicleType.TRUCK, spotLoc1, floor1);
        List<ParkingSpot> allSpots = Arrays.asList(spot1, spot2, spot3, spot4);
        floor1 = new ParkingFloor(1, allSpots);
        ParkingLot parkingLot = new ParkingLot(Arrays.asList(floor1), Arrays.asList(entryGate1));

        // --- 2. Setup Repositories ---
        ParkingLotRepository lotRepo = new InMemoryParkingLotRepository(parkingLot);
        TicketRepository ticketRepo = new InMemoryTicketRepository();
        RateRepository rateRepo = new InMemoryRateRepository();

        // --- 3. Setup Services & Strategies (DI) ---
        PricingService pricingService = new PricingService(rateRepo);
        SpotAllocationStrategy spotStrategy = new NearestSpotStrategy();
        FeeCalculationStrategy feeStrategy = new HourlyCalculationStrategy(pricingService);

        ParkingLotService parkingLotService = new ParkingLotService(lotRepo, spotStrategy);
        TicketService ticketService = new TicketService(ticketRepo);
        CheckinService checkinService = new CheckinService(parkingLotService, ticketService);
        CheckoutService checkoutService = new CheckoutService(parkingLotService, feeStrategy, ticketRepo);

        // --- 4. Instantiate Controller (The Entry Point) ---
        // The Controller wires everything together for external access.
        ParkingLotController controller = new ParkingLotController(checkinService, checkoutService);

        // ----------------------------------------------------
        // 5. DEMONSTRATION OF BEHAVIOR (Interacting via Controller)
        // ----------------------------------------------------

        // --- SCENARIO 1: CAR CHECK-IN ---
        System.out.println("\n--- SCENARIO 1: CAR CHECK-IN via Controller ---");
        CheckinRequestDTO carRequest = new CheckinRequestDTO();
        carRequest.licensePlate = "KA-01-A-1234";
        carRequest.vehicleType = "CAR";
        carRequest.gateId = "E1";

        TicketResponseDTO ticketResponse = null;
        try {
            // Controller call
            ticketResponse = controller.checkIn(carRequest);
            System.out.println("SUCCESS: Controller returned Ticket ID " + ticketResponse.ticketId);
            System.out.println("Parked at Spot: " + ticketResponse.spotId);
        } catch (Exception e) {
            System.out.println("FAILURE: " + e.getMessage());
        }

        // --- SCENARIO 2: CHECKOUT ---
        if (ticketResponse != null) {
            System.out.println("\n--- SCENARIO 2: CAR CHECK-OUT via Controller ---");
            try {
                // Controller call
                double finalFee = controller.checkOut(ticketResponse.ticketId);
                System.out.println("Vehicle with Ticket ID " + ticketResponse.ticketId + " checked out.");
                System.out.println("Final Fee charged: $" + finalFee);

                // Verify spot status using the original model reference (spot1)
                System.out.println("Spot 101 available status after checkout: " + spot1.isAvailable());

            } catch (Exception e) {
                System.out.println("CHECKOUT FAILURE: " + e.getMessage());
            }
        }
    }
}