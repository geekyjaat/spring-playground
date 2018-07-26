package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Flight {
    @JsonProperty("Departs")
    private Date departs;
    @JsonProperty("Tickets")
    private List<Ticket> tickets = Collections.emptyList();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "America/Denver")
    public Date getDeparts() {
        return departs;
    }

    public void setDeparts(Date departs) {
        this.departs = departs;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Ticket {
        @JsonProperty("Passenger")
        private Passenger passenger;
        @JsonProperty("Price")
        private int price;

        public Passenger getPassenger() {
            return passenger;
        }

        public void setPassenger(Passenger passenger) {
            this.passenger = passenger;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }

    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Passenger {
        @JsonProperty("FirstName")
        private String firstName;
        @JsonProperty("LastName")
        private String lastName;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}
