# Flight Management System

## Overview
An internal Tickets Flight Management System to centralize the management of Flights data.

~~## Tech Stack
- Backend: Java 17, Spring Boot
- Database: H2
- Security: JWT Authentication
- Testing: JUnit, Mockito~~

## Features
- User Role-Based Access Control (ADMINISTRATOR, USER)
- Flight CRUD Operations
- Search
- Secure Authentication

## Project Structure
```
ticket-managment-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/airxelerate/flight_managment/
│   │   │       ├── config/
│   │   │       ├── controllers/
│   │   │       ├── dtos/
│   │   │       ├── entities/
│   │   │       ├── enums/
│   │   │       ├── Exceptions/
│   │   │       ├── repositories/
│   │   │       ├── security/
│   │   │       ├── services/
│   │   │       │   ├── impl/
│   │   │       └── ui/
│   │   └── resources/
│   └── test/
├── docs/
└── README.md
```

## Prerequisites
- JDK 17
- Maven 3.8+
- H2 database

## Setup and Installation
1. Clone the repository
   ```bash
   git clone https://github.com/s1syph/flight-managment.git
   ```

3. Build the project
   ```bash
   mvn clean install
   ```

4. Run the application
   ```bash
   mvn spring-boot:run
   ```

## Default Users
- USER:
    - Username: user
    - Password: 123
- ADMINISTRATOR:
    - Username: admin
    - Password: 123

## API Documentation
The REST API documentation is available at `/swagger-ui.html` when running the application.

## Testing
Run tests using:
```bash
mvn test              # Unit tests
mvn verify            # Integration tests
mvn test jacoco:report # Coverage report
```

## Contributing
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request
