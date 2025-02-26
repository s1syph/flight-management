-- Ensure the USERS table exists
CREATE TABLE IF NOT EXISTS USERS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- Insert sample users with properly encoded passwords
-- Using BCrypt hash for the password "password"
INSERT INTO USERS (username, password, role) VALUES
    ('admin', '$2y$10$A60GiIVaxicNRTC/CoHVeeD2bLFpefzX9TmC8vL0t3uuNhGOvcttW', 'ADMINISTRATOR'),
    ('user', '$2y$10$A60GiIVaxicNRTC/CoHVeeD2bLFpefzX9TmC8vL0t3uuNhGOvcttW', 'USER'),
    -- Add a test user with a freshly encoded password
    ('test', '$2y$10$A60GiIVaxicNRTC/CoHVeeD2bLFpefzX9TmC8vL0t3uuNhGOvcttW', 'USER');

-- Create Flight table if it doesn't exist
CREATE TABLE IF NOT EXISTS FLIGHT (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    carrier_code VARCHAR(2),
    flight_number VARCHAR(4),
    flight_date DATE,
    origin VARCHAR(3),
    destination VARCHAR(3)
);

-- Insert sample flights
INSERT INTO FLIGHT (carrier_code, flight_number, flight_date, origin, destination) VALUES
    ('LH', '1234', '2024-05-01', 'FRA', 'JFK'),
    ('BA', '0987', '2024-05-02', 'LHR', 'LAX'),
    ('AF', '4567', '2024-05-03', 'CDG', 'DXB'),
    ('EK', '8901', '2024-05-04', 'DXB', 'SYD');
