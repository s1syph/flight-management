-- Create USERS table
CREATE TABLE IF NOT EXISTS USERS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- Create Flight table
CREATE TABLE IF NOT EXISTS FLIGHT (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    carrier_code VARCHAR(2),
    flight_number VARCHAR(4),
    flight_date DATE,
    origin VARCHAR(3),
    destination VARCHAR(3)
);
