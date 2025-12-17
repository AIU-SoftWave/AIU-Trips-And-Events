# Test Data Documentation

## Overview

This document describes the test data used across all automated tests.

## Test User Data

### Admin User
```json
{
  "email": "admin@aiu.edu",
  "password": "admin123",
  "fullName": "Admin User",
  "phoneNumber": "555-0200",
  "role": "ADMIN"
}
```

### Student User
```json
{
  "email": "student@aiu.edu",
  "password": "student123",
  "fullName": "Test Student",
  "phoneNumber": "555-0300",
  "role": "STUDENT"
}
```

### Staff User
```json
{
  "email": "staff@aiu.edu",
  "password": "staff123",
  "fullName": "Staff Member",
  "phoneNumber": "555-0400",
  "role": "STAFF"
}
```

## Test Event Data

### Standard Event
```json
{
  "title": "Tech Conference",
  "description": "Annual technology conference",
  "type": "EVENT",
  "startDate": "2026-01-15T09:00:00",
  "endDate": "2026-01-15T17:00:00",
  "location": "Main Hall",
  "price": 50.0,
  "capacity": 100,
  "availableSeats": 100
}
```

### Trip Event
```json
{
  "title": "Mountain Trip",
  "description": "Weekend hiking trip",
  "type": "TRIP",
  "startDate": "2026-02-20T08:00:00",
  "endDate": "2026-02-22T18:00:00",
  "location": "Mountain Resort",
  "price": 150.0,
  "capacity": 50,
  "availableSeats": 50,
  "destination": "Mountain Peak",
  "transportMode": "BUS"
}
```

## Test Booking Data

### Standard Booking
```json
{
  "eventId": 1,
  "userId": 1,
  "bookingCode": "BOOK1734192000000_1",
  "status": "CONFIRMED",
  "bookingDate": "2025-12-14T10:00:00",
  "amountPaid": 50.0,
  "paymentMethod": "CASH"
}
```

## Data Generation Patterns

### Unique Booking Codes
```java
String bookingCode = "BOOK" + System.currentTimeMillis() + "_" + bookingCounter.incrementAndGet();
```

### Date Generation
```java
LocalDateTime futureDate = LocalDateTime.now().plusDays(30);
LocalDateTime pastDate = LocalDateTime.now().minusDays(1);
```

### JWT Token Generation
```java
String token = jwtUtil.generateToken(email, role);
```

## Test Database

### H2 Configuration
- **URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (empty)
- **Mode**: In-memory
- **Schema**: Auto-generated from JPA entities

### Data Lifecycle
1. **@BeforeEach**: Database populated with test data
2. **Test Execution**: Operations performed
3. **@AfterEach**: Automatic rollback (@Transactional)

## Sample Test Scenarios

### Scenario 1: Successful Booking
1. Create admin user
2. Create event with 100 capacity
3. Create student user
4. Student books event
5. Verify booking created
6. Verify seats decreased to 99

### Scenario 2: Duplicate Prevention
1. Create event
2. Create user
3. Create first booking
4. Attempt second booking
5. Verify rejection

### Scenario 3: Authentication Flow
1. Register new user
2. Login with credentials
3. Receive JWT token
4. Use token for authenticated request

## Data Validation Rules

### Email Format
- Must contain @
- Must have domain
- Example: `user@aiu.edu`

### Password Strength
- Minimum 6 characters
- Contains letters and numbers
- Example: `password123`

### Phone Number
- Format: `XXX-XXXX`
- Example: `555-0100`

### Event Dates
- Start date must be in future
- End date must be after start date
- Format: ISO 8601

### Capacity
- Must be positive integer
- Minimum: 1
- Maximum: No limit defined

## Test Data Management

### Isolation
- Each test uses fresh data
- No shared state between tests
- Database reset after each test

### Consistency
- Use test data builders
- Standard naming conventions
- Realistic values

### Maintenance
- Centralized test data creation
- Reusable helper methods
- Clear documentation

---

This test data design ensures consistent, realistic, and maintainable test scenarios across all automated tests.
