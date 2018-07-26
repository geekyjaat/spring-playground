package com.example.demo.service;

import com.example.demo.model.Flight;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class FlightService {

    public Flight getFlight() {
        return Flight.builder()
                .departs(new Date())
                .tickets(Arrays.asList(
                        Flight.Ticket.builder()
                                .price(200)
                                .passenger(Flight.Passenger.builder()
                                        .firstName("Some name")
                                        .lastName("Some other name")
                                        .build())
                                .build()
                ))
                .build();
    }

    public List<Flight> getFlights() {
        return Arrays.asList(
                Flight.builder()
                        .departs(new Date())
                        .tickets(Arrays.asList(
                                Flight.Ticket.builder()
                                        .price(200)
                                        .passenger(Flight.Passenger.builder()
                                                .firstName("Some name")
                                                .lastName("Some other name")
                                                .build())
                                        .build()
                        ))
                        .build(),
                Flight.builder()
                        .departs(new Date())
                        .tickets(Arrays.asList(
                                Flight.Ticket.builder()
                                        .price(400)
                                        .passenger(Flight.Passenger.builder()
                                                .firstName("Some other name")
                                                .lastName(null)
                                                .build())
                                        .build()
                        ))
                        .build()
        );
    }
}
