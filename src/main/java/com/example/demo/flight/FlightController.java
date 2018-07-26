package com.example.demo.flight;

import com.example.demo.model.Flight;
import com.example.demo.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    FlightService flightService;

    @GetMapping("")
    public List<Flight> flights() {
        return flightService.getFlights();
    }

    @GetMapping("/flight")
    public Flight flight() {
        return flightService.getFlight();
    }

    @PostMapping("/tickets/total")
    public Map<String, Integer> total(@RequestBody Flight flight) {
        int total = flight.getTickets().stream().mapToInt(t -> t.getPrice()).sum();
        Map<String, Integer> map = new HashMap<>();
        map.put("result", total);
        return map;
    }

}
