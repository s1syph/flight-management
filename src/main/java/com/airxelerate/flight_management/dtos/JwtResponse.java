package com.airxelerate.flight_management.dtos;


import com.airxelerate.flight_management.enums.Role;

public class JwtResponse {
    private String token;
    private String username;
    private Role role;

    // Default constructor for Jackson
    public JwtResponse() {
    }

    public JwtResponse(String token, String username, Role role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    // Getters
    public String getToken() { return token; }
    public String getUsername() { return username; }
    public Role getRole() { return role; }

    // Setters for Jackson
    public void setToken(String token) { this.token = token; }
    public void setUsername(String username) { this.username = username; }
    public void setRole(Role role) { this.role = role; }
}