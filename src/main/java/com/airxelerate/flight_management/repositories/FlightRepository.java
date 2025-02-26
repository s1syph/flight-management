package com.airxelerate.flight_management.repositories;

import com.airxelerate.flight_management.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight,Long> {
}
