package com.airxelerate.flight_management.controllers;

import com.airxelerate.flight_management.entities.Flight;
import com.airxelerate.flight_management.services.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing employee tickets in the ticket management system.
 * Handles ticket creation, updates, comments, and retrieval operations for employees and IT support staff.
 *
 * @author Abdelilah JALAL
 * @version 1.0
 * @since 2024-02-25
 */

@RestController
@RequestMapping("/api/flights")
@Tag(name = "Flight Management", description = "Operations related to flight management")
public class FlightController {

    private final FlightService flightService;
    private static final Logger logger = LogManager.getLogger(FlightController.class);


    public FlightController(FlightService flightService) {
        this.flightService = flightService;
        logger.info("FlightController initialized with FlightService");
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Operation(
            summary = "Create a new flight",
            description = "Add a new flight record (Administrator only)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight created",
                    content = @Content(schema = @Schema(implementation = Flight.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Flight> addFlight(@Valid @RequestBody Flight flight) {
        logger.info("Attempting to add new flight: {}", flight.getFlightNumber());
        Flight savedFlight = flightService.addFlight(flight);
        logger.info("Flight {} created successfully with ID: {}",
                savedFlight.getFlightNumber(), savedFlight.getId());
        return ResponseEntity.ok(savedFlight);
    }

    @GetMapping
    @Operation(
            summary = "Get all flights",
            description = "Retrieve a list of all available flights"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(schema = @Schema(implementation = Flight.class))),
    })
    public List<Flight> getAllFlights() {
        logger.debug("Fetching all flights");
        List<Flight> flights = flightService.getAllFlights();
        logger.info("Returning {} flights", flights.size());
        return flights;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get flight by ID",
            description = "Retrieve a specific flight by its identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight found",
                    content = @Content(schema = @Schema(implementation = Flight.class))),
            @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        logger.debug("Looking for flight with ID: {}", id);
        Optional<Flight> flight = flightService.getFlightById(id);

        flight.ifPresentOrElse(
                f -> logger.info("Found flight {} with ID: {}", f.getFlightNumber(), id),
                () -> logger.warn("Flight with ID {} not found", id)
        );

        return flight.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Operation(
            summary = "Delete a flight",
            description = "Remove a flight from the system by ID (Administrator only)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    public ResponseEntity<?> deleteFlight(@PathVariable Long id) {
        logger.info("Attempting to delete flight with ID: {}", id);
        flightService.deleteFlight(id);
        logger.info("Flight with ID {} successfully deleted", id);
        return ResponseEntity.ok().build();
    }
}