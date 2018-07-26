package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Flight {
    private Date departs;
    private List<Ticket> tickets;

    @JsonGetter("Departs")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "America/Denver")
    public Date getDeparts() {
        return departs;
    }

    @JsonSetter("departs")
    public void setDeparts(Date departs) {
        this.departs = departs;
    }

    @JsonGetter("Tickets")
    public List<Ticket> getTickets() {
        return tickets;
    }

    @JsonSetter("tickets")
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Ticket {

        private Passenger passenger;

        private int price;

        @JsonGetter("Passenger")
        public Passenger getPassenger() {
            return passenger;
        }

        @JsonSetter("passenger")
        public void setPassenger(Passenger passenger) {
            this.passenger = passenger;
        }

        @JsonGetter("Price")
        public int getPrice() {
            return price;
        }

        @JsonSetter("price")
        public void setPrice(int price) {
            this.price = price;
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Passenger {

        private String firstName;

        private String lastName;

        @JsonGetter("FirstName")
        public String getFirstName() {
            return firstName;
        }

        @JsonSetter("firstName")
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        @JsonGetter("LastName")
        public String getLastName() {
            return lastName;
        }

        @JsonSetter("lastName")
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}
