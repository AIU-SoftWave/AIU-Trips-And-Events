# AIU Trips and Events - Backend API Documentation

## Overview
This is the backend API for the AIU Trips and Events management system. The system allows students to browse and book events/trips, organizers to manage events, and administrators to oversee the entire system.

## Technology Stack
- **Framework:** Spring Boot 3.2.0
- **Language:** Java 17
- **Database:** H2 (In-Memory) - Can be configured for MySQL/PostgreSQL
- **Security:** JWT Authentication
- **Build Tool:** Maven

## User Roles
1. **STUDENT** - Can browse events, make bookings, view tickets
2. **ORGANIZER** - Can create and manage events, view bookings
3. **ADMIN** - Full system access, user management, event approval

## Core Features Added

### 1. Enhanced User Management
- Email verification with tokens
- Password reset functionality
- Account locking after 5 failed login attempts
- User profile management with additional fields (faculty, academic year, etc.)

### 2. Payment & Ticketing System
- Payment processing with multiple payment methods
- QR code ticket generation
- Ticket validation at entry
- Refund processing

### 3. Admin Dashboard
- User role management
- Event approval workflow
- System statistics and analytics
- User search and filtering

### 4. Automated Tasks
- Event reminders (24 hours before event)
- Auto-update event status (DRAFT → PUBLISHED → ONGOING → COMPLETED)
- Notification system for all event changes

### 5. Validation & Security
- Password strength validation
- Email format validation
- Event data validation (dates, capacity, pricing)
- Booking validation (availability, deadlines)

## API Endpoints

### Authentication & User Endpoints

#### User Registration & Authentication
```
POST /api/auth/register
- Register new user
- Body: { email, password, fullName, firstName, lastName, phoneNumber, faculty, academicYear }

POST /api/auth/login
- Login user
- Body: { email, password }

GET /api/users/verify-email?token={token}
- Verify email address

POST /api/users/forgot-password
- Initiate password reset
- Body: { email }

POST /api/users/reset-password
- Reset password with token
- Body: { token, password }

GET /api/users/profile?email={email}
- Get user profile (authenticated)

PUT /api/users/profile/{id}
- Update user profile (authenticated)
```

### Event Management Endpoints

#### Create & Manage Events
```
POST /api/events
- Create new event (ORGANIZER/ADMIN)
- Body: { title, description, type, startDate, endDate, location, price, capacity, registrationDeadline }

PUT /api/events/{id}
- Update event (ORGANIZER/ADMIN)

DELETE /api/events/{id}
- Delete event (ORGANIZER/ADMIN)

GET /api/events
- Get all events

GET /api/events/{id}
- Get event by ID

GET /api/events/type/{type}
- Get events by type (TRIP, WORKSHOP, SEMINAR, CONFERENCE, SPORTS, CULTURAL, SOCIAL)

GET /api/events/status/{status}
- Get events by status (DRAFT, PUBLISHED, ACTIVE, ONGOING, FULL, COMPLETED, CANCELLED)
```

### Booking & Ticketing Endpoints

#### Booking Management
```
POST /api/bookings/create/{eventId}
- Create booking for event (STUDENT)

DELETE /api/bookings/{id}/cancel
- Cancel booking (STUDENT)

GET /api/bookings/user
- Get user's bookings (authenticated)

GET /api/bookings/event/{eventId}
- Get event bookings (ORGANIZER/ADMIN)

GET /api/bookings/code/{bookingCode}
- Get booking by code
```

#### Ticket Operations
```
POST /api/tickets/generate/{bookingId}
- Generate ticket for booking (auto-generated on booking)

GET /api/tickets/booking/{bookingId}
- Get ticket by booking ID

GET /api/tickets/code/{ticketCode}
- Get ticket by ticket code

POST /api/tickets/validate/{ticketCode}
- Validate ticket at entry (ORGANIZER/ADMIN)

GET /api/tickets/check/{ticketCode}
- Check ticket validity without marking as used
```

### Payment Endpoints

```
POST /api/payments/process/{bookingId}
- Process payment for booking
- Body: { paymentMethod: "CASH|CREDIT_CARD|DEBIT_CARD|BANK_TRANSFER|DIGITAL_WALLET" }

POST /api/payments/refund/{paymentId}
- Refund payment (ADMIN/ORGANIZER)

GET /api/payments/transaction/{transactionId}
- Get payment by transaction ID

GET /api/payments/booking/{bookingId}
- Get payments for booking

GET /api/payments/{paymentId}/status
- Get payment status
```

### Admin Endpoints

#### User Management
```
GET /api/admin/users
- Get all users (ADMIN)

GET /api/admin/users/role/{role}
- Get users by role (ADMIN)

GET /api/admin/users/search?query={query}
- Search users (ADMIN)

PUT /api/admin/users/{userId}/role
- Assign role to user (ADMIN)
- Body: { role: "STUDENT|ORGANIZER|ADMIN" }

PUT /api/admin/users/{userId}/status
- Toggle user active status (ADMIN)
- Body: { active: true|false }

POST /api/admin/users/{userId}/lock
- Lock user account (ADMIN)

POST /api/admin/users/{userId}/unlock
- Unlock user account (ADMIN)

DELETE /api/admin/users/{userId}
- Delete user (ADMIN)
```

#### Event Approval & Statistics
```
GET /api/admin/statistics
- Get system statistics (ADMIN)

GET /api/admin/events/pending
- Get events pending approval (ADMIN)

POST /api/admin/events/{eventId}/approve
- Approve event (ADMIN)

POST /api/admin/events/{eventId}/reject
- Reject event (ADMIN)
```

### Notification Endpoints

```
GET /api/notifications
- Get user notifications

GET /api/notifications/unread
- Get unread notifications

PUT /api/notifications/{id}/mark-read
- Mark notification as read
```

### Feedback & Reports

```
POST /api/feedback
- Submit event feedback (STUDENT)

GET /api/feedback/event/{eventId}
- Get event feedback (ORGANIZER/ADMIN)

GET /api/reports/events
- Get event reports (ADMIN)
```

## Data Models

### User
```java
{
    id: Long
    email: String (unique)
    password: String (hashed)
    fullName: String
    firstName: String
    lastName: String
    role: UserRole (STUDENT, ORGANIZER, ADMIN)
    phoneNumber: String
    faculty: String
    academicYear: Integer
    isEmailVerified: Boolean
    isActive: Boolean
    isAccountLocked: Boolean
    failedLoginAttempts: Integer
    createdAt: LocalDateTime
}
```

### Event
```java
{
    id: Long
    title: String
    description: String
    type: EventType (TRIP, WORKSHOP, SEMINAR, CONFERENCE, SPORTS, CULTURAL, SOCIAL)
    startDate: LocalDateTime
    endDate: LocalDateTime
    location: String
    price: Double
    capacity: Integer
    availableSeats: Integer
    imageUrl: String
    registrationDeadline: LocalDateTime
    status: EventStatus (DRAFT, PUBLISHED, ACTIVE, ONGOING, FULL, COMPLETED, CANCELLED)
    createdBy: User
    createdAt: LocalDateTime
}
```

### Booking
```java
{
    id: Long
    user: User
    event: Event
    bookingCode: String (UUID)
    status: BookingStatus (CONFIRMED, CANCELLED, ATTENDED)
    bookingDate: LocalDateTime
    qrCodePath: String (Base64)
    amountPaid: Double
    paymentMethod: PaymentMethod
    validatedAt: LocalDateTime
    validatedBy: String
}
```

### Ticket
```java
{
    id: Long
    booking: Booking
    ticketCode: String (UUID)
    qrCodeData: String
    isUsed: Boolean
    usedAt: LocalDateTime
    createdAt: LocalDateTime
}
```

### Payment
```java
{
    id: Long
    booking: Booking
    amount: Double
    paymentMethod: PaymentMethod (CASH, CREDIT_CARD, DEBIT_CARD, BANK_TRANSFER, DIGITAL_WALLET)
    transactionId: String
    status: PaymentStatus (PENDING, COMPLETED, FAILED, REFUNDED)
    paymentDate: LocalDateTime
    createdAt: LocalDateTime
}
```

## Scheduled Tasks

### Event Reminders
- **Schedule:** Every hour (at minute 0)
- **Function:** Sends reminders to participants 24 hours before event

### Event Status Updates
- **Schedule:** Every 30 minutes
- **Function:** Auto-updates event status based on dates
  - PUBLISHED → ONGOING (when event starts)
  - ONGOING → COMPLETED (when event ends)
  - Handles ACTIVE events similarly

## Security

### Authentication
- JWT token-based authentication
- Tokens expire after configured time
- Password hashing using BCrypt

### Authorization
- Role-based access control (RBAC)
- Method-level security with @PreAuthorize
- Protected endpoints require valid JWT

### Account Security
- Account locking after 5 failed login attempts
- Auto-unlock after 1 hour
- Password strength requirements:
  - Minimum 8 characters
  - At least one uppercase letter
  - At least one lowercase letter
  - At least one digit
  - At least one special character

## Validation Rules

### Event Validation
- Title is required
- Start date must be in the future
- End date must be after start date
- Capacity must be positive
- Price cannot be negative
- Registration deadline must be before event start

### Booking Validation
- Event must have available seats
- Registration deadline must not be passed
- Event must not have started
- User cannot duplicate bookings

## Error Handling

The API returns standard HTTP status codes:
- **200 OK** - Successful request
- **201 Created** - Resource created successfully
- **400 Bad Request** - Validation error
- **401 Unauthorized** - Authentication required
- **403 Forbidden** - Insufficient permissions
- **404 Not Found** - Resource not found
- **500 Internal Server Error** - Server error

Error response format:
```json
{
    "timestamp": "2024-01-01T10:00:00",
    "status": 400,
    "error": "Bad Request",
    "message": "Event title is required",
    "path": "/api/events"
}
```

## Configuration

### Application Properties
```properties
# Server Configuration
server.port=8080

# Database Configuration (H2)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.h2.console.enabled=true

# JWT Configuration
jwt.secret=your-secret-key
jwt.expiration=86400000

# Email Configuration (for production)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email
spring.mail.password=your-password
```

## Running the Application

### Development
```bash
mvn spring-boot:run
```

### Production Build
```bash
mvn clean package
java -jar target/trips-events-1.0.0.jar
```

### With Docker
```bash
docker build -t aiu-trips-events .
docker run -p 8080:8080 aiu-trips-events
```

## Testing

### Compile and Run Tests
```bash
mvn clean test
```

### Access H2 Console (Development)
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: (leave empty)

## Future Enhancements

1. **Email Integration**
   - SendGrid or AWS SES integration for real emails
   - HTML email templates
   - Email tracking

2. **Analytics Dashboard**
   - Real-time event statistics
   - Revenue tracking
   - User engagement metrics

3. **Mobile App Support**
   - Enhanced API for mobile clients
   - Push notification support

4. **Advanced Features**
   - Waiting list for full events
   - Group bookings
   - Recurring events
   - Multi-language support

## Support

For issues or questions, please contact:
- Email: support@aiu-events.com
- Documentation: https://docs.aiu-events.com

## License
© 2024 AIU Software Development Team. All rights reserved.
