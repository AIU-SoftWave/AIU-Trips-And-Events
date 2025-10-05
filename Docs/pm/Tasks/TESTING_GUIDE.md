# Testing Guide for AIU Trips and Events System

## Prerequisites

### Software Requirements
- Java 17 or higher
- Maven 3.6 or higher
- Git
- Postman (optional, for API testing)
- A web browser

### Environment Setup
1. Clone the repository
2. Navigate to the backend directory: `cd Project/backend`
3. Install dependencies: `mvn clean install -DskipTests`

---

## Running the Application

### Backend (Spring Boot)

1. Navigate to backend directory:
```bash
cd Project/backend
```

2. Run the application:
```bash
mvn spring-boot:run
```

3. The application will start on `http://localhost:8080`

4. Check health:
```bash
curl http://localhost:8080/api/events
```

### Frontend (Next.js)

1. Navigate to frontend directory:
```bash
cd Project/frontend
```

2. Install dependencies:
```bash
npm install
```

3. Run the development server:
```bash
npm run dev
```

4. Open browser at `http://localhost:3000`

---

## Test Scenarios

### 1. User Registration & Authentication

#### Test 1.1: Register New User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test.student@aiu.edu",
    "password": "Test123!@#",
    "fullName": "Test Student",
    "firstName": "Test",
    "lastName": "Student",
    "phoneNumber": "+1234567890",
    "faculty": "Computer Science",
    "academicYear": 3
  }'
```

**Expected Result:**
- Status: 200 OK
- Response contains JWT token
- User status: PENDING_VERIFICATION
- Email verification notification sent (check console)

#### Test 1.2: Email Verification
```bash
# Get the verification token from the console output
curl http://localhost:8080/api/auth/verify-email?token={verification-token}
```

**Expected Result:**
- Status: 200 OK
- User status changed to ACTIVE
- emailVerified = true

#### Test 1.3: Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test.student@aiu.edu",
    "password": "Test123!@#"
  }'
```

**Expected Result:**
- Status: 200 OK
- JWT token returned
- failedLoginAttempts reset to 0

#### Test 1.4: Failed Login Attempts
```bash
# Try with wrong password 5 times
for i in {1..5}; do
  curl -X POST http://localhost:8080/api/auth/login \
    -H "Content-Type: application/json" \
    -d '{
      "email": "test.student@aiu.edu",
      "password": "WrongPassword"
    }'
done
```

**Expected Result:**
- First 4 attempts: 401 Unauthorized with attempt count
- 5th attempt: Account locked message
- Account locked for 1 hour

#### Test 1.5: Password Reset
```bash
# Step 1: Request reset
curl -X POST http://localhost:8080/api/auth/forgot-password \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test.student@aiu.edu"
  }'

# Step 2: Reset password (use token from console)
curl -X POST http://localhost:8080/api/auth/reset-password \
  -H "Content-Type: application/json" \
  -d '{
    "token": "{reset-token}",
    "newPassword": "NewTest123!@#"
  }'
```

**Expected Result:**
- Reset link sent notification
- Password changed successfully
- Account unlocked
- Failed attempts reset

---

### 2. Event Management

#### Test 2.1: Create Event (Admin/Organizer)
```bash
# First login to get token
TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@aiu.edu","password":"admin123"}' \
  | jq -r '.token')

# Create event
curl -X POST http://localhost:8080/api/events \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "AI Workshop 2024",
    "description": "Hands-on workshop on AI and Machine Learning",
    "type": "WORKSHOP",
    "startDate": "2024-12-01T14:00:00",
    "endDate": "2024-12-01T17:00:00",
    "location": "Computer Lab 3",
    "price": 25.00,
    "capacity": 30,
    "category": "seminars",
    "registrationDeadline": "2024-11-28T23:59:59"
  }'
```

#### Test 2.2: Get All Events
```bash
curl http://localhost:8080/api/events
```

---

### 3. Booking & Payment

#### Test 3.1: Create Booking
```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "eventId": 1,
    "userId": 1
  }'
```

**Expected Result:**
- Booking created
- Booking code generated
- Available seats decremented
- QR code generated

#### Test 3.2: Process Payment
```bash
curl -X POST "http://localhost:8080/api/payments/process?bookingId=1&paymentMethod=CREDIT_CARD" \
  -H "Authorization: Bearer $TOKEN"
```

**Expected Result:**
- Payment processed
- Transaction ID generated
- Payment confirmation notification sent

#### Test 3.3: Generate Ticket
```bash
curl -X POST "http://localhost:8080/api/tickets/generate?bookingId=1" \
  -H "Authorization: Bearer $TOKEN"
```

**Expected Result:**
- Ticket generated
- Ticket code created
- QR code in base64
- Ticket email sent

---

### 4. Ticket Validation

#### Test 4.1: Validate Ticket
```bash
curl -X POST "http://localhost:8080/api/tickets/validate?ticketCode=TKT-XXX&validatedBy=organizer@aiu.edu" \
  -H "Authorization: Bearer $TOKEN"
```

**Expected Result:**
- Ticket marked as validated
- Validation timestamp recorded
- Validator email saved

#### Test 4.2: Check Ticket Validity
```bash
curl http://localhost:8080/api/tickets/TKT-XXX/validity \
  -H "Authorization: Bearer $TOKEN"
```

**Expected Result:**
- Returns {"isValid": false} if already validated
- Returns {"isValid": true} if not yet validated

---

### 5. Analytics & Reports

#### Test 5.1: System Statistics
```bash
curl http://localhost:8080/api/analytics/system \
  -H "Authorization: Bearer $TOKEN"
```

**Expected Result:**
```json
{
  "totalUsers": 10,
  "totalEvents": 5,
  "activeEvents": 3,
  "totalBookings": 15,
  "confirmedBookings": 12,
  "totalRevenue": 750.00
}
```

#### Test 5.2: Event Statistics
```bash
curl http://localhost:8080/api/analytics/event/1 \
  -H "Authorization: Bearer $TOKEN"
```

#### Test 5.3: Export Report as CSV
```bash
curl http://localhost:8080/api/admin/reports/event/1/export/csv \
  -H "Authorization: Bearer $TOKEN" \
  -o event-report.csv
```

**Expected Result:**
- CSV file downloaded
- Contains event details and bookings

---

### 6. Admin Functions

#### Test 6.1: Get All Users
```bash
curl http://localhost:8080/api/admin/users \
  -H "Authorization: Bearer $TOKEN"
```

#### Test 6.2: Update User Role
```bash
curl -X PUT "http://localhost:8080/api/admin/users/2/role?role=ORGANIZER" \
  -H "Authorization: Bearer $TOKEN"
```

**Expected Result:**
- User role updated to ORGANIZER
- User can now create events

#### Test 6.3: Unlock User Account
```bash
curl -X PUT http://localhost:8080/api/admin/users/3/unlock \
  -H "Authorization: Bearer $TOKEN"
```

---

## Database Verification

### H2 Console Access
1. Navigate to: `http://localhost:8080/h2-console`
2. JDBC URL: `jdbc:h2:mem:testdb`
3. Username: `sa`
4. Password: (leave empty)

### Verify Tables
```sql
-- Check users table
SELECT * FROM users;

-- Check events table
SELECT * FROM events;

-- Check bookings table
SELECT * FROM bookings;

-- Check payments table
SELECT * FROM payments;

-- Check tickets table
SELECT * FROM tickets;

-- Check notifications table
SELECT * FROM notifications;

-- Check trips table
SELECT * FROM trips;
```

---

## Automated Testing Script

Save as `test-system.sh`:

```bash
#!/bin/bash

BASE_URL="http://localhost:8080/api"

echo "=== Testing AIU Trips and Events System ==="

# 1. Register User
echo -e "\n1. Registering user..."
REGISTER_RESPONSE=$(curl -s -X POST $BASE_URL/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@aiu.edu",
    "password": "Test123!@#",
    "fullName": "Test User",
    "phoneNumber": "+1234567890"
  }')
echo "Response: $REGISTER_RESPONSE"

# 2. Login
echo -e "\n2. Logging in..."
LOGIN_RESPONSE=$(curl -s -X POST $BASE_URL/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@aiu.edu",
    "password": "admin123"
  }')
TOKEN=$(echo $LOGIN_RESPONSE | jq -r '.token')
echo "Token: $TOKEN"

# 3. Get Events
echo -e "\n3. Getting events..."
curl -s $BASE_URL/events \
  -H "Authorization: Bearer $TOKEN" | jq

# 4. Get System Stats
echo -e "\n4. Getting system statistics..."
curl -s $BASE_URL/analytics/system \
  -H "Authorization: Bearer $TOKEN" | jq

echo -e "\n=== Tests Completed ==="
```

Run with:
```bash
chmod +x test-system.sh
./test-system.sh
```

---

## Common Issues & Solutions

### Issue 1: Compilation Errors
**Solution:** Ensure Java 17 is being used
```bash
java -version
mvn -version
```

### Issue 2: Port Already in Use
**Solution:** Kill process on port 8080
```bash
lsof -ti:8080 | xargs kill -9
```

### Issue 3: Database Connection Error
**Solution:** H2 runs in memory, no external database needed

### Issue 4: JWT Token Expired
**Solution:** Login again to get new token

### Issue 5: 403 Forbidden
**Solution:** Check user role permissions for the endpoint

---

## Performance Testing

### Using Apache Bench
```bash
# Test login endpoint
ab -n 100 -c 10 -p login.json -T application/json \
  http://localhost:8080/api/auth/login

# Test events endpoint
ab -n 100 -c 10 -H "Authorization: Bearer $TOKEN" \
  http://localhost:8080/api/events
```

---

## Security Testing

### Test 1: SQL Injection Prevention
```bash
curl -X POST $BASE_URL/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@aiu.edu OR 1=1--",
    "password": "anything"
  }'
```
**Expected:** Should not bypass authentication

### Test 2: XSS Prevention
```bash
curl -X POST $BASE_URL/events \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "<script>alert(\"XSS\")</script>",
    "description": "Test event"
  }'
```
**Expected:** Script tags should be sanitized

---

## Monitoring

### Application Logs
```bash
# View logs in real-time
tail -f logs/application.log

# Search for errors
grep ERROR logs/application.log
```

### Health Check Endpoint
```bash
curl http://localhost:8080/actuator/health
```

---

## Test Data Setup

### Create Admin User
```sql
INSERT INTO users (email, password, full_name, role, status, email_verified, created_at)
VALUES ('admin@aiu.edu', '$2a$10$encodedpassword', 'System Admin', 'ADMIN', 'ACTIVE', true, NOW());
```

### Create Sample Event
```sql
INSERT INTO events (title, description, type, start_date, end_date, location, price, capacity, available_seats, status, category, created_at)
VALUES ('Tech Seminar', 'Introduction to AI', 'SEMINAR', '2024-12-01 14:00:00', '2024-12-01 17:00:00', 'Hall A', 30.00, 100, 100, 'ACTIVE', 'seminars', NOW());
```

---

## Cleanup

### Reset Database
```bash
# Delete H2 database file
rm -rf ~/testdb*

# Restart application to recreate tables
```

### Clear Test Data
```sql
DELETE FROM tickets;
DELETE FROM payments;
DELETE FROM bookings;
DELETE FROM events;
DELETE FROM users WHERE email LIKE 'test%';
```

---

## Next Steps

1. ✅ All backend endpoints tested
2. ✅ Authentication flow verified
3. ✅ Payment processing validated
4. ✅ Analytics working correctly
5. [ ] Frontend integration testing
6. [ ] End-to-end UI testing
7. [ ] Load testing
8. [ ] Security audit
9. [ ] User acceptance testing
10. [ ] Production deployment

---

## Support

For issues or questions:
- Check logs: `Project/backend/logs/`
- Review API docs: `Docs/pm/Tasks/API_DOCUMENTATION.md`
- System overview: `Docs/pm/Tasks/SYSTEM_ENHANCEMENT_SUMMARY.md`
