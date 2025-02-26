package com.airxelerate.flight_management.controllers;

import com.airxelerate.flight_management.dtos.LoginRequest;
import com.airxelerate.flight_management.entities.User;
import com.airxelerate.flight_management.enums.Role;
import com.airxelerate.flight_management.security.CustomUserDetails;
import com.airxelerate.flight_management.security.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for AuthController.
 * Tests authentication endpoints and JWT token generation.
 */
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthController authController;

    private LoginRequest loginRequest;
    private CustomUserDetails userDetails;
    private User user;
    private static final String TEST_JWT = "test.jwt.token";

    @BeforeEach
    void setUp() {
        // Initialize test data
        loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password123");

        user = new User();
        user.setUsername("testuser");
        user.setRole(Role.USER); // Fixed: Added ROLE_ prefix

        userDetails = new CustomUserDetails(user);
    }

    @Test
    void authenticateUser_BadCredentials() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act & Assert
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            authController.authenticateUser(loginRequest);
        });

        assertEquals("Invalid credentials", exception.getMessage());

        // Verify interactions
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils, never()).generateJwtToken(anyString());
    }

    @Test
    void authenticateUser_NullUsername() {
        // Arrange
        loginRequest.setUsername(null);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act & Assert
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            authController.authenticateUser(loginRequest);
        });

        // Verify interactions
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils, never()).generateJwtToken(any());
    }

    @Test
    void authenticateUser_EmptyPassword() {
        // Arrange
        loginRequest.setPassword("");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act & Assert
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            authController.authenticateUser(loginRequest);
        });

        // Verify interactions
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils, never()).generateJwtToken(any());
    }

//    @Test
//    void authenticateUser_UnexpectedException() {
//        // Arrange
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//                .thenThrow(new RuntimeException("Unexpected error"));
//
//        // Act & Assert
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            authController.authenticateUser(loginRequest);
//        });
//
//        assertEquals("Unexpected error", exception.getMessage());
//
//        // Verify interactions
//        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
//        verify(jwtUtils, never()).generateJwtToken(any());
//    }
}