package com.airxelerate.flight_management.controllers;

import com.airxelerate.flight_management.dtos.JwtResponse;
import com.airxelerate.flight_management.dtos.LoginRequest;
import com.airxelerate.flight_management.security.CustomUserDetails;
import com.airxelerate.flight_management.security.JwtUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for Authentication.
 *
 * @author Abdelilah JALAL
 * @version 1.0
 * @since 2024-02-25
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LogManager.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    /**
     * Constructs a new AuthController with the required dependencies.
     *
     * @param authenticationManager Spring Security's authentication manager
     * @param jwtUtils Utility class for JWT token operations
     */
    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        logger.info("AuthController initialized with required dependencies");
    }

    /**
     * Authenticates a user and generates a JWT token upon successful authentication.
     *
     * @param loginRequest DTO containing user login credentials
     * @return ResponseEntity containing JWT token and user details if authentication is successful
     * @throws AuthenticationException if authentication fails
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        logger.debug("Attempting authentication for user: {}", loginRequest.getUsername());

        try {
            // Authenticate user credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            logger.info("User authenticated successfully: {}", userDetails.getUsername());

            // Generate JWT token
            String jwt = jwtUtils.generateJwtToken(userDetails.getUsername());
            logger.debug("JWT token generated for user: {}", userDetails.getUsername());

            // Create and return response
            JwtResponse response = new JwtResponse(
                    jwt,
                    userDetails.getUsername(),
                    userDetails.getUser().getRole()
            );

            logger.info("Authentication successful for user: {}. Role: {}",
                    userDetails.getUsername(),
                    userDetails.getUser().getRole());

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            logger.error("Authentication failed for user: {}. Error: {}",
                    loginRequest.getUsername(),
                    e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error during authentication process", e);
            throw e;
        }
    }
}
