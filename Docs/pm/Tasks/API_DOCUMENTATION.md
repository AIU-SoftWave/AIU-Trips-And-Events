# AIU Trips and Events - API Documentation

## Base URL
```
http://localhost:8080/api
```

---

## Authentication Endpoints

### Register User
```http
POST /auth/register
```

**Request Body:**
```json
{
  "email": "student@aiu.edu",
  "password": "SecurePass123!",
  "fullName": "John Doe",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1234567890",
  "faculty": "Computer Science",
  "academicYear": 3
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "email": "student@aiu.edu",
  "name": "John Doe",
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
  "password": "SecurePass123!"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "email": "student@aiu.edu",
  "name": "John Doe",
  "role": "STUDENT"
}
```

### Forgot Password
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
{
  "message": "Password reset link sent to your email"
}
```

### Reset Password
```http
POST /auth/reset-password
```

**Request Body:**
```json
{
  "token": "reset-token-here",
  "newPassword": "NewSecurePass123!"
}
```

**Response:**
```json
{
  "message": "Password reset successfully"
}
```

### Verify Email
```http
GET /auth/verify-email?token={verification-token}
```

**Response:**
```json
{
  "message": "Email verified successfully"
}
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
    "type": "CONFERENCE",
    "startDate": "2024-11-15T09:00:00",
    "endDate": "2024-11-15T17:00:00",
    "location": "Main Hall",
    "price": 50.00,
    "capacity": 200,
    "availableSeats": 150,
    "status": "ACTIVE",
    "category": "conferences",
    "registrationDeadline": "2024-11-10T23:59:59"
  }
]
```

### Create Event (ORGANIZER/ADMIN only)
```http
POST /events
```

**Request Body:**
```json
{
  "title": "Workshop on AI",
  "description": "Hands-on AI workshop",
  "type": "WORKSHOP",
  "startDate": "2024-11-20T14:00:00",
  "endDate": "2024-11-20T17:00:00",
  "location": "Lab 3",
  "price": 30.00,
  "capacity": 50,
  "category": "seminars",
  "registrationDeadline": "2024-11-18T23:59:59"
}
```

---

## Booking Endpoints

### Create Booking
```http
POST /bookings
```

**Request Body:**
```json
{
  "eventId": 1,
  "userId": 1
}
```

**Response:**
```json
{
  "id": 1,
  "bookingCode": "BK-ABC123",
  "status": "CONFIRMED",
  "bookingDate": "2024-10-05T10:30:00",
  "amountPaid": 50.00,
  "qrCodePath": "base64-encoded-qr-code..."
}
```

### Get User Bookings
```http
GET /bookings/user/{userId}
```

### Cancel Booking
```http
DELETE /bookings/{bookingId}
```

---

## Payment Endpoints

### Process Payment
```http
POST /payments/process?bookingId=1&paymentMethod=CREDIT_CARD
```

**Response:**
```json
{
  "id": 1,
  "transactionId": "TXN-ABC12345",
  "amount": 50.00,
  "status": "COMPLETED",
  "paymentDate": "2024-10-05T10:35:00"
}
```

### Refund Payment (ORGANIZER/ADMIN only)
```http
POST /payments/{paymentId}/refund?reason=Event cancelled
```

### Get Payment by Booking
```http
GET /payments/booking/{bookingId}
```

---

## Ticket Endpoints

### Generate Ticket
```http
POST /tickets/generate?bookingId=1
```

**Response:**
```json
{
  "id": 1,
  "ticketCode": "TKT-XYZ789",
  "qrCode": "base64-encoded-qr-code...",
  "isValidated": false,
  "issuedAt": "2024-10-05T10:36:00"
}
```

### Validate Ticket (ORGANIZER/ADMIN only)
```http
POST /tickets/validate?ticketCode=TKT-XYZ789&validatedBy=organizer@aiu.edu
```

**Response:**
```json
{
  "id": 1,
  "ticketCode": "TKT-XYZ789",
  "isValidated": true,
  "validatedAt": "2024-11-15T09:15:00",
  "validatedBy": "organizer@aiu.edu"
}
```

### Check Ticket Validity
```http
GET /tickets/{ticketCode}/validity
```

**Response:**
```json
{
  "isValid": true
}
```

---

## Admin Endpoints (ADMIN only)

### Get All Users
```http
GET /admin/users
```

### Update User Role
```http
PUT /admin/users/{userId}/role?role=ORGANIZER
```

### Update User Status
```http
PUT /admin/users/{userId}/status?status=ACTIVE
```

### Unlock User Account
```http
PUT /admin/users/{userId}/unlock
```

### Delete User
```http
DELETE /admin/users/{userId}
```

---

## Analytics Endpoints (ORGANIZER/ADMIN only)

### Get System Statistics
```http
GET /analytics/system
```

**Response:**
```json
{
  "totalUsers": 150,
  "totalEvents": 25,
  "activeEvents": 10,
  "totalBookings": 300,
  "confirmedBookings": 280,
  "totalRevenue": 15000.00
}
```

### Get Event Statistics
```http
GET /analytics/event/{eventId}
```

**Response:**
```json
{
  "eventId": 1,
  "eventTitle": "Tech Conference 2024",
  "capacity": 200,
  "availableSeats": 150,
  "totalBookings": 50,
  "confirmedBookings": 48,
  "revenue": 2400.00,
  "capacityUtilization": 25.00
}
```

### Get User Engagement
```http
GET /analytics/engagement
```

### Get Revenue Analytics
```http
GET /analytics/revenue?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59
```

### Get Event Category Distribution
```http
GET /analytics/categories
```

**Response:**
```json
{
  "conferences": 5,
  "seminars": 8,
  "field trips": 7,
  "concerts": 5
}
```

---

## Report Endpoints (ORGANIZER/ADMIN only)

### Get Event Report
```http
GET /admin/reports/event/{eventId}
```

### Get Overall Report
```http
GET /admin/reports/overall
```

### Export Event Report as CSV
```http
GET /admin/reports/event/{eventId}/export/csv
```

**Response:** Downloads CSV file

### Export Overall Report as CSV
```http
GET /admin/reports/overall/export/csv
```

**Response:** Downloads CSV file

### Export Event Report as PDF
```http
GET /admin/reports/event/{eventId}/export/pdf
```

### Export Overall Report as PDF
```http
GET /admin/reports/overall/export/pdf
```

---

## Notification Endpoints

### Get User Notifications
```http
GET /notifications/user/{userEmail}
```

### Get Unread Notifications
```http
GET /notifications/unread/{userEmail}
```

### Mark Notification as Read
```http
PUT /notifications/{notificationId}/read
```

---

## Authentication

All protected endpoints require a JWT token in the Authorization header:

```
Authorization: Bearer {your-jwt-token}
```

### Role-Based Access:
- **Public**: Login, Register, Get Events
- **STUDENT**: Bookings, Payments, Tickets, Notifications
- **ORGANIZER**: Create/Edit Events, Analytics, Reports, Validate Tickets
- **ADMIN**: All endpoints, User Management

---

## Error Responses

### 400 Bad Request
```json
{
  "timestamp": "2024-10-05T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Email already exists"
}
```

### 401 Unauthorized
```json
{
  "timestamp": "2024-10-05T10:30:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid credentials"
}
```

### 403 Forbidden
```json
{
  "timestamp": "2024-10-05T10:30:00",
  "status": 403,
  "error": "Forbidden",
  "message": "Access denied"
}
```

### 404 Not Found
```json
{
  "timestamp": "2024-10-05T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Resource not found"
}
```

### 500 Internal Server Error
```json
{
  "timestamp": "2024-10-05T10:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred"
}
```

---

## Testing with cURL

### Register
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "student@aiu.edu",
    "password": "SecurePass123!",
    "fullName": "John Doe",
    "phoneNumber": "+1234567890"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "student@aiu.edu",
    "password": "SecurePass123!"
  }'
```

### Get Events (with token)
```bash
curl -X GET http://localhost:8080/api/events \
  -H "Authorization: Bearer {your-token}"
```

---

## Postman Collection

A Postman collection is available at:
`/docs/postman/AIU-Trips-Events-API.json`

Import this into Postman for easy API testing.

---

## Rate Limiting

Currently not implemented. Consider adding in production:
- 100 requests per minute for authenticated users
- 20 requests per minute for public endpoints

---

## Changelog

### Version 1.0 (October 2024)
- Initial API release
- Authentication with JWT
- Event management
- Booking system
- Payment processing
- Ticket generation
- Analytics and reporting
- Admin user management
