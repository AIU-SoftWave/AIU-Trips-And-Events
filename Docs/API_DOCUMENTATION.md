# API Documentation - AIU Trips and Events

## Base URL
```
http://localhost:8080/api
```

## Authentication

All protected endpoints require a JWT token in the Authorization header:
```
Authorization: Bearer <token>
```

---

## Authentication Endpoints

### Register New User
```http
POST /auth/register
```

**Request Body:**
```json
{
  "email": "student@aiu.edu",
  "password": "SecurePass@123",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1234567890",
  "faculty": "Computer Science",
  "academicYear": "2024"
}
```

**Password Requirements:**
- Minimum 8 characters
- At least one digit
- At least one lowercase letter
- At least one uppercase letter
- At least one special character (@#$%^&+=)

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "student@aiu.edu",
  "fullName": "John Doe",
  "role": "STUDENT"
}
```

### Login
```http
POST /auth/login
```

**Request Body:**
```json
{
  "email": "student@aiu.edu",
  "password": "SecurePass@123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "student@aiu.edu",
  "fullName": "John Doe",
  "role": "STUDENT"
}
```

**Error Responses:**
- `401`: Invalid credentials
- `423`: Account locked (after 5 failed attempts)

### Verify Email
```http
GET /auth/verify-email?token={verificationToken}
```

**Response:**
```json
"Email verified successfully"
```

### Request Password Reset
```http
POST /auth/forgot-password
```

**Request Body:**
```json
{
  "email": "student@aiu.edu"
}
```

**Response:**
```json
"Password reset link sent to email"
```

### Reset Password
```http
POST /auth/reset-password
```

**Request Body:**
```json
{
  "token": "reset-token-from-email",
  "newPassword": "NewSecurePass@123"
}
```

**Response:**
```json
"Password reset successfully"
```

---

## Event Endpoints

### Get All Events
```http
GET /events
```

**Response:**
```json
[
  {
    "id": 1,
    "title": "Tech Conference 2024",
    "description": "Annual technology conference",
    "type": "EVENT",
    "category": "CONFERENCE",
    "startDate": "2024-12-01T09:00:00",
    "startTime": "09:00:00",
    "endDate": "2024-12-01T17:00:00",
    "location": "Main Auditorium",
    "price": 50.00,
    "capacity": 200,
    "availableSeats": 150,
    "registrationDeadline": "2024-11-25T23:59:59",
    "status": "ACTIVE",
    "createdBy": {
      "id": 2,
      "fullName": "Jane Smith",
      "role": "ORGANIZER"
    }
  }
]
```

### Get Event by ID
```http
GET /events/{id}
```

### Get Events by Type
```http
GET /events/type/{type}
```

**Types:** `EVENT`, `TRIP`

### Get Events by Category
```http
GET /events/category/{category}
```

**Categories:** `FIELD_TRIP`, `SEMINAR`, `CONFERENCE`, `CONCERT`

### Get Upcoming Events
```http
GET /events/upcoming
```

### Create Event (Organizer/Admin)
```http
POST /events
```

**Headers:** `Authorization: Bearer <token>`

**Request Body:**
```json
{
  "title": "Tech Conference 2024",
  "description": "Annual technology conference",
  "type": "EVENT",
  "category": "CONFERENCE",
  "startDate": "2024-12-01T09:00:00",
  "startTime": "09:00:00",
  "endDate": "2024-12-01T17:00:00",
  "location": "Main Auditorium",
  "price": 50.00,
  "capacity": 200,
  "registrationDeadline": "2024-11-25T23:59:59",
  "imageUrl": "https://example.com/image.jpg"
}
```

### Update Event (Organizer/Admin)
```http
PUT /events/{id}
```

**Headers:** `Authorization: Bearer <token>`

### Cancel Event (Admin)
```http
DELETE /events/{id}
```

**Headers:** `Authorization: Bearer <token>`

**Note:** This sets status to CANCELLED and notifies all participants

### Send Message to Participants (Organizer)
```http
POST /events/{id}/send-message
```

**Headers:** `Authorization: Bearer <token>`

**Request Body:**
```json
{
  "message": "Important update about the event..."
}
```

---

## Booking Endpoints

### Create Booking
```http
POST /bookings/event/{eventId}
```

**Headers:** `Authorization: Bearer <token>`

**Response:**
```json
{
  "id": 1,
  "user": { ... },
  "event": { ... },
  "bookingCode": "550e8400-e29b-41d4-a716-446655440000",
  "status": "PENDING_PAYMENT",
  "bookingDate": "2024-10-04T10:30:00",
  "qrCodePath": "data:image/png;base64,...",
  "amountPaid": 50.00,
  "paymentStatus": "PENDING"
}
```

### Process Payment
```http
POST /bookings/{bookingId}/payment
```

**Headers:** `Authorization: Bearer <token>`

**Request Body:**
```json
{
  "paymentMethod": "CREDIT_CARD",
  "transactionId": "txn_1234567890"
}
```

**Response:**
```json
{
  "id": 1,
  "status": "CONFIRMED",
  "paymentStatus": "COMPLETED",
  "paymentMethod": "CREDIT_CARD",
  "transactionId": "txn_1234567890",
  "ticketSent": true
}
```

### Validate QR Code and Mark Attendance
```http
POST /bookings/validate-qr/{bookingCode}
```

**Response:**
```json
{
  "id": 1,
  "status": "ATTENDED",
  "attended": true,
  "attendedAt": "2024-12-01T09:15:00"
}
```

### Get My Bookings
```http
GET /bookings/my-bookings
```

**Headers:** `Authorization: Bearer <token>`

### Cancel Booking
```http
PUT /bookings/{bookingId}/cancel
```

**Headers:** `Authorization: Bearer <token>`

### Get Booking by Code
```http
GET /bookings/code/{bookingCode}
```

---

## Feedback Endpoints

### Submit Event Feedback
```http
POST /feedback/event/{eventId}
```

**Headers:** `Authorization: Bearer <token>`

**Request Body:**
```json
{
  "rating": 5,
  "comment": "Excellent event, very informative!"
}
```

**Note:** User must have attended the event to provide feedback

**Response:**
```json
{
  "id": 1,
  "event": { ... },
  "user": { ... },
  "rating": 5,
  "comment": "Excellent event, very informative!",
  "createdAt": "2024-12-02T10:00:00"
}
```

### Get Event Feedback
```http
GET /feedback/event/{eventId}
```

---

## Notification Endpoints

### Get All Notifications
```http
GET /notifications
```

**Headers:** `Authorization: Bearer <token>`

### Get Unread Notifications
```http
GET /notifications/unread
```

**Headers:** `Authorization: Bearer <token>`

### Mark Notification as Read
```http
PUT /notifications/{id}/read
```

**Headers:** `Authorization: Bearer <token>`

---

## Reports Endpoints

### Get Overall Statistics
```http
GET /reports/overall
```

**Headers:** `Authorization: Bearer <token>` (Admin)

**Response:**
```json
{
  "totalEvents": 25,
  "totalBookings": 450,
  "totalRevenue": 22500.00,
  "activeEvents": 10,
  "completedEvents": 12,
  "cancelledEvents": 3,
  "totalUsers": 500,
  "eventsByCategory": {
    "CONFERENCE": 8,
    "SEMINAR": 10,
    "FIELD_TRIP": 5,
    "CONCERT": 2
  },
  "mostPopularCategory": "SEMINAR"
}
```

### Get Event Report
```http
GET /reports/event/{eventId}
```

**Headers:** `Authorization: Bearer <token>`

**Response:**
```json
{
  "eventId": 1,
  "eventTitle": "Tech Conference 2024",
  "eventType": "EVENT",
  "eventCategory": "CONFERENCE",
  "totalCapacity": 200,
  "availableSeats": 50,
  "bookedSeats": 150,
  "confirmedParticipants": 140,
  "attendedParticipants": 135,
  "attendanceRate": 96.4,
  "totalRevenue": 7000.00,
  "cancelledBookings": 10,
  "averageRating": 4.5,
  "totalFeedbacks": 120,
  "feedbacks": [ ... ]
}
```

### Get Organizer Performance
```http
GET /reports/organizer/{organizerId}
```

**Headers:** `Authorization: Bearer <token>` (Admin)

**Response:**
```json
{
  "totalEventsCreated": 15,
  "activeEvents": 5,
  "completedEvents": 10,
  "totalRevenue": 15000.00,
  "totalParticipants": 450,
  "averageParticipantsPerEvent": 30.0
}
```

### Get Attendance Report
```http
GET /reports/attendance/{eventId}
```

**Headers:** `Authorization: Bearer <token>` (Organizer/Admin)

**Response:**
```json
{
  "eventTitle": "Tech Conference 2024",
  "totalExpectedAttendees": 140,
  "actualAttendees": 135,
  "noShows": 5,
  "attendees": [
    {
      "name": "John Doe",
      "email": "john@aiu.edu",
      "attendedAt": "2024-12-01T09:15:00"
    }
  ]
}
```

---

## Error Responses

All endpoints may return the following error responses:

### 400 Bad Request
```json
{
  "timestamp": "2024-10-04T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/auth/register"
}
```

### 401 Unauthorized
```json
{
  "timestamp": "2024-10-04T10:30:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid credentials",
  "path": "/api/auth/login"
}
```

### 403 Forbidden
```json
{
  "timestamp": "2024-10-04T10:30:00",
  "status": 403,
  "error": "Forbidden",
  "message": "Access denied",
  "path": "/api/events"
}
```

### 404 Not Found
```json
{
  "timestamp": "2024-10-04T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Event not found",
  "path": "/api/events/999"
}
```

### 423 Locked
```json
{
  "timestamp": "2024-10-04T10:30:00",
  "status": 423,
  "error": "Locked",
  "message": "Account locked due to 5 consecutive failed login attempts. Please reset your password.",
  "path": "/api/auth/login"
}
```

### 500 Internal Server Error
```json
{
  "timestamp": "2024-10-04T10:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred",
  "path": "/api/events"
}
```

---

## Rate Limiting

To ensure fair usage and system stability:
- Rate limit: 100 requests per minute per IP
- Burst limit: 20 requests per second

Exceeding these limits will result in a 429 Too Many Requests response.

---

## Testing with Postman/cURL

### Example: Register and Login

1. **Register:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@aiu.edu",
    "password": "SecurePass@123",
    "firstName": "Test",
    "lastName": "User",
    "phoneNumber": "+1234567890",
    "faculty": "Engineering",
    "academicYear": "2024"
  }'
```

2. **Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@aiu.edu",
    "password": "SecurePass@123"
  }'
```

3. **Use Token:**
```bash
TOKEN="<your-jwt-token>"
curl -X GET http://localhost:8080/api/events \
  -H "Authorization: Bearer $TOKEN"
```

---

## WebSocket Support (Future)

Real-time notifications will be available via WebSocket:
```
ws://localhost:8080/ws/notifications
```

---

## Versioning

Current API Version: v1

Future versions will be accessible via:
```
http://localhost:8080/api/v2/...
```
