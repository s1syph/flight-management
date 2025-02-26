package com.airxelerate.flight_management.controllers;

import com.airxelerate.flight_management.entities.Flight;
import com.airxelerate.flight_management.services.FlightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightControllerTest {

    @Mock
    private FlightService flightService;

    @InjectMocks
    private FlightController flightController;

    @Test
    void addFlight_ValidFlight_ReturnsCreatedFlight() {
        // Arrange
        Flight inputFlight = new Flight();
        inputFlight.setFlightNumber("FL123");
        Flight savedFlight = new Flight();
        savedFlight.setId(1L);
        savedFlight.setFlightNumber("FL123");

        when(flightService.addFlight(any(Flight.class))).thenReturn(savedFlight);

        // Act
        ResponseEntity<Flight> response = flightController.addFlight(inputFlight);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(savedFlight);
        verify(flightService).addFlight(inputFlight);
    }

    @Test
    void getAllFlights_ReturnsListOfFlights() {
        // Arrange
        Flight flight1 = new Flight();
        flight1.setId(1L);
        Flight flight2 = new Flight();
        flight2.setId(2L);
        List<Flight> flights = Arrays.asList(flight1, flight2);

        when(flightService.getAllFlights()).thenReturn(flights);

        // Act
        List<Flight> result = flightController.getAllFlights();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(flight1, flight2);
        verify(flightService).getAllFlights();
    }

    @Test
    void getFlightById_FlightExists_ReturnsFlight() {
        // Arrange
        Long flightId = 1L;
        Flight flight = new Flight();
        flight.setId(flightId);
        when(flightService.getFlightById(flightId)).thenReturn(Optional.of(flight));

        // Act
        ResponseEntity<Flight> response = flightController.getFlightById(flightId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(flight);
        verify(flightService).getFlightById(flightId);
    }

    @Test
    void getFlightById_FlightNotFound_ReturnsNotFound() {
        // Arrange
        Long flightId = 1L;
        when(flightService.getFlightById(flightId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Flight> response = flightController.getFlightById(flightId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(flightService).getFlightById(flightId);
    }

    @Test
    void deleteFlight_ValidId_ReturnsOk() {
        // Arrange
        Long flightId = 1L;
        doNothing().when(flightService).deleteFlight(flightId);

        // Act
        ResponseEntity<?> response = flightController.deleteFlight(flightId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(flightService).deleteFlight(flightId);
    }
}