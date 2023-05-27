package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import io.swagger.models.auth.In;

import java.util.*;

public class AirportRepositry {
    private  TreeMap <String,Airport> airportTreeMap= new TreeMap<>() ;
    private Map<Integer,Flight> flightM = new HashMap<>() ;
    private Map<Integer , Passenger> passengerMap  = new HashMap<>() ;
    private Map<Integer , Set<Integer>> flightPass = new HashMap<>() ;
    private  Map<Integer , Integer> revenue = new HashMap<>() ;
    private  Map <Integer , Integer > payment = new HashMap<>() ;
    public void addAirport(Airport airport) {
        airportTreeMap.put(airport.getAirportName() , airport) ;
    }

    public String getLargestAirportName() {
        String ans = ""  ;
        int ans1 = 0 ;

        for (String name : airportTreeMap.keySet()){
            int curr  = airportTreeMap.get(name).getNoOfTerminals();
            if(curr>ans1){
                ans1 = curr ;
                ans = name ;
            }
        }
        return ans ;
    }

    public double getShortestDuratrionOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        double dura = Integer.MAX_VALUE;
        for (Flight flight : flightM.values()){
            if(fromCity.equals(flight.getFromCity()) && toCity.equals(flight.getToCity())){
                if(dura> flight.getDuration()){
                    dura = flight.getDuration() ;
                }
            }
        }
        return dura == Integer.MAX_VALUE ?-1 : dura ;
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        Airport airport = airportTreeMap.get(airportName) ;
        int count = 0 ;
        if (airport != null){
            City city = airport.getCity() ;
            for (Flight flight : flightM.values()){
                if(date.equals(flight.getFlightDate())){
                    if(city.equals(flight.getToCity()) || city.equals(flight.getFromCity())){
                        Integer flightId  = flight.getFlightId() ;
                        Set<Integer> set = flightPass.get(flightId) ;
                        if(set != null){
                            count+= set.size() ;
                        }
                    }
                }
            }
        }
        return  count ;
    }

    public int calculateFlightFare(Integer flightId) {
        int fareOfTicket = 3000 ;
        int alreadyBooked = 0 ;
        if(flightPass.containsKey(flightId)){
            alreadyBooked = flightPass.get(flightId).size()  ;
        }
        return (fareOfTicket + (alreadyBooked*50)) ;
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        Flight f = flightM.get(flightId) ;
        int maxiCap = f.getMaxCapacity() ;

        Set<Integer> set = new HashSet<>() ;
        if(flightM.containsKey(flightId)){
            set = flightPass.get(flightId) ;
        }

        int capacity = set.size() ;

        if(capacity == maxiCap){
            return "FAILURE" ;
        }
        else if (set.contains(passengerId)){
            return "FAILURE" ;
        }
        int cost = calculateFlightFare(flightId) ;
        payment.put(passengerId , cost) ;
         cost += revenue.getOrDefault(flightId , 0) ;
         revenue.put(flightId, cost) ;
         set.add(passengerId) ;
        flightPass.put(flightId , set) ;
        return "SUCCESS" ;
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        Set<Integer> set1 = flightPass.get(flightId) ;
        if(set1.contains(passengerId)){
            set1.remove(passengerId) ;
            int cost = payment.getOrDefault(passengerId , 0) ;
            payment.remove(passengerId) ;
            int r  = revenue.getOrDefault(flightId,0);
            revenue.put(flightId,r-cost) ;
            return "SUCCESS" ;
        }
        return "FAILURE" ;
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        int count = 0  ;
        for (Integer flightId : flightPass.keySet()){
            Set<Integer> set2 = flightPass.get(flightId)  ;
            if(set2.contains(passengerId)){
                count++ ;
            }
        }
        return count ;
    }

    public void addFlight(Flight flight) {
        flightM.put(flight.getFlightId() , flight) ;
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        if(!flightM.containsKey(flightId)) return null ;
        Flight f = flightM.get(flightId)  ;
        City city = f.getFromCity();

        for (String aName : airportTreeMap.keySet()){
            Airport airport = airportTreeMap.get(aName) ;
            if(city.equals(airport.getCity())){
                return aName ;
            }
        }
        return null ;
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        Integer r = revenue.getOrDefault(flightId,0);
        return r ;
    }

    public void addPassenger(Passenger passenger) {
        passengerMap.put(passenger.getPassengerId(),passenger) ;
    }
}
