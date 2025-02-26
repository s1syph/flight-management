package com.airxelerate.flight_management.services.impl;

import com.airxelerate.flight_management.entities.Flight;
import com.airxelerate.flight_management.repositories.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceImplTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightServiceImpl flightService;

    @Test
    void addFlight_ShouldSaveAndReturnFlight() {
        // Arrange
        Flight inputFlight = new Flight();
        inputFlight.setFlightNumber("FL123");
        Flight savedFlight = new Flight();
        savedFlight.setId(1L);
        savedFlight.setFlightNumber("FL123");

        when(flightRepository.save(any(Flight.class))).thenReturn(savedFlight);

        // Act
        Flight result = flightService.addFlight(inputFlight);

        // Assert
        assertThat(result).isEqualTo(savedFlight);
        verify(flightRepository).save(inputFlight);
    }

    @Test
    void getAllFlights_ShouldReturnAllFlights() {
        // Arrange
        Flight flight1 = new Flight();
        flight1.setId(1L);
        Flight flight2 = new Flight();
        flight2.setId(2L);
        List<Flight> flights = Arrays.asList(flight1, flight2);

        when(flightRepository.findAll()).thenReturn(flights);

        // Act
        List<Flight> result = flightService.getAllFlights();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(flight1, flight2);
        verify(flightRepository).findAll();
    }

    @Test
    void getFlightById_WhenExists_ShouldReturnFlight() {
        // Arrange
        Long flightId = 1L;
        Flight flight = new Flight();
        flight.setId(flightId);
        when(flightRepository.findById(flightId)).thenReturn(Optional.of(flight));

        // Act
        Optional<Flight> result = flightService.getFlightById(flightId);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(flight);
        verify(flightRepository).findById(flightId);
    }

    @Test
    void getFlightById_WhenNotExists_ShouldReturnEmpty() {
        // Arrange
        Long flightId = 1L;
        when(flightRepository.findById(flightId)).thenReturn(Optional.empty());

        // Act
        Optional<Flight> result = flightService.getFlightById(flightId);

        // Assert
        assertThat(result).isEmpty();
        verify(flightRepository).findById(flightId);
    }

    @Test
    void deleteFlight_ShouldCallRepositoryDelete() {
        // Arrange
        Long flightId = 1L;
        doNothing().when(flightRepository).deleteById(flightId);

        // Act
        flightService.deleteFlight(flightId);

        // Assert
        verify(flightRepository).deleteById(flightId);
    }
}