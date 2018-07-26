package com.example.demo.flight;

import com.example.demo.service.FlightService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@WebMvcTest({FlightController.class, FlightService.class})
public class FlightControllerTests {

    @Autowired
    MockMvc mvc;

    @Test
    public void test_flight_get() throws Exception {

        this.mvc
                .perform(
                        get("/flights/flight")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(jsonPath("$.Tickets[0].Passenger.FirstName", is("Some name")))
                .andExpect(jsonPath("$.Tickets[0].Passenger.LastName", is("Some other name")))
                .andExpect(jsonPath("$.Tickets[0].Price", is(200)));
    }

    @Test
    public void test_flights_get() throws Exception {

        this.mvc
                .perform(
                        get("/flights")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(jsonPath("$.[0].Tickets[0].Passenger.FirstName", is("Some name")))
                .andExpect(jsonPath("$.[0].Tickets[0].Price", is(200)))
                .andExpect(jsonPath("$.[1].Tickets[0].Price", is(400)));
    }
}
