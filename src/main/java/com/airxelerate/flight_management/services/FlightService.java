package com.airxelerate.flight_management.services;

import com.airxelerate.flight_management.entities.Flight;
import java.util.List;
import java.util.Optional;

public interface FlightService {
    Flight addFlight(Flight flight);
    List<Flight> getAllFlights();
    Optional<Flight> getFlightById(Long id);
    void deleteFlight(Long id);
}

