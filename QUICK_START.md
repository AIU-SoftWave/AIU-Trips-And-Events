# Quick Start Guide - AIU Trips and Events

Get up and running with the AIU Trips and Events system in minutes!

## 🚀 5-Minute Quick Start

### Option 1: Docker (Recommended)

**Prerequisites:** Docker and Docker Compose installed

```bash
# Clone the repository
git clone https://github.com/AIU-SoftWave/AIU-Trips-And-Events.git
cd AIU-Trips-And-Events

# Start the application
docker-compose up --build

# Access the application
# Frontend: http://localhost:3000
# Backend API: http://localhost:8080
```

That's it! 🎉

### Option 2: Local Development

**Prerequisites:** Java 17+, Node.js 18+, Maven

#### Backend (Terminal 1)
```bash
cd Project/backend
mvn spring-boot:run
```

#### Frontend (Terminal 2)
```bash
cd Project/frontend
npm install
npm run dev
```

**Access:**
- Frontend: http://localhost:3000
- Backend: http://localhost:8080

---

## 📝 First Steps

### 1. Register a New Account

**Via Frontend:**
1. Go to http://localhost:3000
2. Click "Sign Up"
3. Fill in the registration form:
   - Email: your-email@aiu.edu
   - Password: SecurePass@123 (minimum 8 chars, 1 digit, 1 uppercase, 1 special char)
   - First Name, Last Name
   - Phone Number
   - Faculty
   - Academic Year

**Via API:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "student@aiu.edu",
    "password": "SecurePass@123",
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "+1234567890",
    "faculty": "Computer Science",
    "academicYear": "2024"
  }'
```

### 2. Login and Get Token

**Via API:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "student@aiu.edu",
    "password": "SecurePass@123"
  }'
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

Save this token for authenticated requests!

### 3. Create Your First Event (Organizers/Admins)

```bash
TOKEN="your-jwt-token-here"

curl -X POST http://localhost:8080/api/events \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Tech Conference 2024",
    "description": "Annual technology conference for students",
    "type": "EVENT",
    "category": "CONFERENCE",
    "startDate": "2024-12-01T09:00:00",
    "startTime": "09:00:00",
    "location": "Main Auditorium",
    "price": 50.00,
    "capacity": 200,
    "registrationDeadline": "2024-11-25T23:59:59"
  }'
```

### 4. Browse Events

```bash
# Get all events
curl http://localhost:8080/api/events

# Get upcoming events
curl http://localhost:8080/api/events/upcoming

# Get events by category
curl http://localhost:8080/api/events/category/CONFERENCE
```

### 5. Book an Event

```bash
TOKEN="your-jwt-token-here"

curl -X POST http://localhost:8080/api/bookings/event/1 \
  -H "Authorization: Bearer $TOKEN"
```

### 6. Process Payment

```bash
curl -X POST http://localhost:8080/api/bookings/1/payment \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "paymentMethod": "CREDIT_CARD",
    "transactionId": "txn_123456789"
  }'
```

---

## 🔑 Default Credentials

After registration, you'll have a STUDENT role by default.

**To create an Organizer/Admin:**
1. Access H2 Console: http://localhost:8080/h2-console
   - JDBC URL: `jdbc:h2:mem:tripsdb`
   - Username: `sa`
   - Password: (leave empty)

2. Run SQL:
```sql
UPDATE users SET role = 'ORGANIZER' WHERE email = 'your-email@aiu.edu';
-- or
UPDATE users SET role = 'ADMINISTRATOR' WHERE email = 'admin@aiu.edu';
```

---

## 🎯 Common Use Cases

### Use Case 1: Student Books an Event

```bash
# 1. Login
LOGIN_RESPONSE=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "student@aiu.edu", "password": "SecurePass@123"}')

TOKEN=$(echo $LOGIN_RESPONSE | jq -r '.token')

# 2. View upcoming events
curl http://localhost:8080/api/events/upcoming

# 3. Book event
curl -X POST http://localhost:8080/api/bookings/event/1 \
  -H "Authorization: Bearer $TOKEN"

# 4. View my bookings
curl http://localhost:8080/api/bookings/my-bookings \
  -H "Authorization: Bearer $TOKEN"
```

### Use Case 2: Organizer Creates and Manages Event

```bash
# 1. Create event
curl -X POST http://localhost:8080/api/events \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{...event details...}'

# 2. View event bookings
curl http://localhost:8080/api/bookings/event/1 \
  -H "Authorization: Bearer $TOKEN"

# 3. Send message to participants
curl -X POST http://localhost:8080/api/events/1/send-message \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"message": "Event starts in 1 hour!"}'

# 4. Get event report
curl http://localhost:8080/api/reports/event/1 \
  -H "Authorization: Bearer $TOKEN"
```

### Use Case 3: Password Reset Flow

```bash
# 1. Request password reset
curl -X POST http://localhost:8080/api/auth/forgot-password \
  -H "Content-Type: application/json" \
  -d '{"email": "student@aiu.edu"}'

# 2. Check console logs for reset token
# (In production, this would be sent via email)

# 3. Reset password with token
curl -X POST http://localhost:8080/api/auth/reset-password \
  -H "Content-Type: application/json" \
  -d '{
    "token": "reset-token-from-email",
    "newPassword": "NewSecurePass@123"
  }'
```

---

## 📊 Sample Data

### Event Types
- `EVENT` - General events
- `TRIP` - Field trips and excursions

### Event Categories
- `FIELD_TRIP` - Educational field trips
- `SEMINAR` - Academic seminars
- `CONFERENCE` - Professional conferences
- `CONCERT` - Entertainment events

### User Roles
- `STUDENT` - Can browse and book events
- `ORGANIZER` - Can create and manage events
- `ADMINISTRATOR` - Full system access

### Booking Status
- `PENDING_PAYMENT` - Awaiting payment
- `CONFIRMED` - Payment completed
- `CANCELLED` - Booking cancelled
- `ATTENDED` - User attended event

---

## 🛠 Troubleshooting

### Port Already in Use

**Error:** "Port 8080 is already in use"

**Solution:**
```bash
# Find and kill process
lsof -i :8080
kill -9 <PID>
```

### Database Connection Error

**Error:** "Unable to connect to database"

**Solution:** Check if H2 database is configured correctly in `application.properties`

### Maven Build Fails

**Error:** "Failed to execute goal..."

**Solution:**
```bash
# Clean and rebuild
mvn clean install -U
```

### npm Install Fails

**Error:** "npm ERR! code EACCES"

**Solution:**
```bash
# Clear npm cache
npm cache clean --force
# Retry
npm install
```

### Docker Issues

**Error:** "Cannot connect to Docker daemon"

**Solution:**
```bash
# Restart Docker service
sudo systemctl restart docker

# Or rebuild without cache
docker-compose build --no-cache
docker-compose up
```

---

## 📚 Next Steps

1. **Explore the API:** Check out [API_DOCUMENTATION.md](Docs/API_DOCUMENTATION.md)
2. **Deploy to Production:** Read [DEPLOYMENT_GUIDE.md](Docs/DEPLOYMENT_GUIDE.md)
3. **Contribute:** See [CONTRIBUTING.md](CONTRIBUTING.md)
4. **Customize:** Modify configurations in `.env` file

---

## 🆘 Getting Help

- **Documentation:** Check the [README.md](README.md)
- **API Reference:** See [API_DOCUMENTATION.md](Docs/API_DOCUMENTATION.md)
- **Issues:** Report bugs on [GitHub Issues](https://github.com/AIU-SoftWave/AIU-Trips-And-Events/issues)
- **Discussions:** Ask questions in [GitHub Discussions](https://github.com/AIU-SoftWave/AIU-Trips-And-Events/discussions)

---

## 🎉 You're Ready!

Start building amazing event management features! Happy coding! 🚀
