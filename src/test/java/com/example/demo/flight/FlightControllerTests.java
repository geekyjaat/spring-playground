package com.example.demo.flight;

import com.example.demo.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@WebMvcTest({FlightController.class, FlightService.class})
@AutoConfigureMockMvc(secure=false)
public class FlightControllerTests {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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


    @Test
    public void test_flights_total_literal() throws Exception {

        String input = "{\n" +
                "  \"tickets\": [\n" +
                "    {\n" +
                "      \"passenger\": {\n" +
                "        \"firstName\": \"Some name\",\n" +
                "        \"lastName\": \"Some other name\"\n" +
                "      },\n" +
                "      \"price\": 200\n" +
                "    },\n" +
                "    {\n" +
                "      \"passenger\": {\n" +
                "        \"firstName\": \"Name B\",\n" +
                "        \"lastName\": \"Name C\"\n" +
                "      },\n" +
                "      \"price\": 150\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        this.mvc
                .perform(
                        post("/flights/tickets/total")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(input)
                )
                .andExpect(jsonPath("$.result", is(350)));
    }

    @Test
    public void test_flights_total_object() throws Exception {

        Map<String, Object> input = new HashMap<>();
        Map<String, Object> ticket1 = new HashMap<>();
        Map<String, Object> ticket2 = new HashMap<>();
        ticket1.put("price", 200);
        ticket2.put("price", 150);
        List<Map> tickets = Arrays.asList(ticket1, ticket2);
        input.put("tickets", tickets);


        this.mvc
                .perform(
                        post("/flights/tickets/total")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(OBJECT_MAPPER.writeValueAsString(input))
                )
                .andExpect(jsonPath("$.result", is(350)));
    }

    @Test
    public void test_flights_total_fixture() throws Exception {

        String input = getJSON("/price_request.json");

        this.mvc
                .perform(
                        post("/flights/tickets/total")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(input)
                )
                .andExpect(jsonPath("$.result", is(350)));
    }

    private String getJSON(String path) throws Exception {
        URL url = this.getClass().getResource(path);
        return new String(Files.readAllBytes(Paths.get(url.getFile())));
    }
}
