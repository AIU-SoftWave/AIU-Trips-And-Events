# AIU-Trips-And-Events

A university system that manages events and trips through a web app that manages the whole activities process.

## Features

- **Authentication & Profiles**: Login/Create Account with JWT authentication
- **Event/Trip Management**: Add/Edit/Delete events and trips
- **Booking & Ticketing**: Reservation system with QR Code generation for entry
- **Notifications**: Real-time messages to students upon any update
- **Reports & Analytics**: Participants count, income tracking, and feedback

## Tech Stack

### Backend (Java)
- Spring Boot 3.2.0
- Spring Security with JWT
- Spring Data JPA
- H2 Database (in-memory)
- QR Code Generation (ZXing)
- Maven

### Frontend (Next.js)
- Next.js 15 with TypeScript
- Tailwind CSS
- Axios for API calls
- React Context for state management
- QR Code display

## Getting Started

### Prerequisites
- Java 17 or higher
- Node.js 18 or higher
- Maven

### Backend Setup

1. Navigate to the backend directory:
```bash
cd backend
```

2. Build and run the Spring Boot application:
```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### Frontend Setup

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Run the development server:
```bash
npm run dev
```

The frontend will start on `http://localhost:3000`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user

### Events
- `GET /api/events` - Get all events
- `GET /api/events/{id}` - Get event by ID
- `GET /api/events/type/{type}` - Get events by type (EVENT/TRIP)
- `GET /api/events/upcoming` - Get upcoming events
- `POST /api/events` - Create new event (Admin)
- `PUT /api/events/{id}` - Update event (Admin)
- `DELETE /api/events/{id}` - Delete event (Admin)

### Bookings
- `POST /api/bookings/event/{eventId}` - Create booking
- `GET /api/bookings/my-bookings` - Get user's bookings
- `PUT /api/bookings/{bookingId}/cancel` - Cancel booking
- `GET /api/bookings/code/{code}` - Get booking by code

### Notifications
- `GET /api/notifications` - Get all notifications
- `GET /api/notifications/unread` - Get unread notifications
- `PUT /api/notifications/{id}/read` - Mark as read

### Reports (Admin)
- `GET /api/admin/reports/overall` - Get overall statistics
- `GET /api/admin/reports/event/{eventId}` - Get event-specific report

## Default Users

After starting the backend, you can create users via the registration page. The first user you create will have STUDENT role. To create an admin user, you'll need to modify the user in the H2 console.

Access H2 Console at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:tripsdb`
- Username: `sa`
- Password: (leave empty)

## Architecture

### Backend (SOLID Principles)
- **Single Responsibility**: Each service handles one concern
- **Open/Closed**: Easily extensible without modifying existing code
- **Liskov Substitution**: Interface-based design
- **Interface Segregation**: Focused repository interfaces
- **Dependency Inversion**: Dependencies injected via Spring

### DRY Principle
- Reusable components and services
- No code duplication
- Shared utilities and DTOs

## Project Structure

```
AIU-Trips-And-Events/
├── backend/
│   ├── src/main/java/com/aiu/trips/
│   │   ├── controller/      # REST Controllers
│   │   ├── service/         # Business Logic
│   │   ├── repository/      # Data Access
│   │   ├── model/           # Entity Models
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── security/        # Security & JWT
│   │   └── util/            # Utilities (QR Code)
│   └── pom.xml
├── frontend/
│   ├── app/                 # Next.js Pages
│   ├── components/          # React Components
│   ├── contexts/            # Context Providers
│   ├── lib/                 # API Client
│   └── package.json
└── README.md
```

## License

This project is for educational purposes.
