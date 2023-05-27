package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;

import java.util.Date;

public class AirportService {
    AirportRepositry airportRepositry = new AirportRepositry()  ;
    public void addAirport(Airport airport) {
        airportRepositry.addAirport(airport);
    }

    public String getLargestAirportName() {
        return airportRepositry.getLargestAirportName();
    }

    public double getShortestDuratrionOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        return airportRepositry.getShortestDuratrionOfPossibleBetweenTwoCities(fromCity , toCity);
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        return airportRepositry.getNumberOfPeopleOn(date,airportName) ;
    }

    public int calculateFlightFare(Integer flightId) {
        return airportRepositry.calculateFlightFare(flightId) ;
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        return airportRepositry.bookATicket(flightId , passengerId) ;
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        return airportRepositry.cancelATicket(flightId,passengerId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        return airportRepositry.countOfBookingsDoneByPassengerAllCombined(passengerId) ;
    }

    public void addFlight(Flight flight) {
         airportRepositry.addFlight(flight) ;
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        return airportRepositry.getAirportNameFromFlightId(flightId) ;
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        return airportRepositry.calculateRevenueOfAFlight(flightId) ;
    }

    public void addPassenger(Passenger passenger) {
        airportRepositry.addPassenger(passenger) ;
    }
}
