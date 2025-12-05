# AIU-Trips-And-Events

A university system that manages events and trips through a web app that manages the whole activities process.

## Features

- **Authentication & Profiles**: Login/Create Account with JWT authentication
- **Event/Trip Management**: Add/Edit/Delete events and trips
- **Booking & Ticketing**: Reservation system with QR Code generation for entry
- **Ticket Validation**: Validate tickets at event entry using QR codes
- **Payment Tracking**: Track cash-only payments for bookings
- **Notifications**: Real-time messages to students upon any update
- **Feedback System**: Students can provide ratings and comments after attending events
- **Reports & Analytics**: Participants count, income tracking, and feedback analysis

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
cd Project/backend
```

2. Build and run the Spring Boot application:
```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

**Default Admin Credentials:**
- Email: `admin@aiu.edu`
- Password: `admin123`

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
- `POST /api/bookings/validate/{bookingCode}` - Validate ticket at event entry

### Feedbacks
- `POST /api/feedbacks/event/{eventId}` - Submit feedback (requires attendance)
- `GET /api/feedbacks/event/{eventId}` - Get event feedbacks
- `GET /api/feedbacks/my-feedbacks` - Get user's feedbacks
- `GET /api/feedbacks/event/{eventId}/average-rating` - Get event average rating

### Notifications
- `GET /api/notifications` - Get all notifications
- `GET /api/notifications/unread` - Get unread notifications
- `PUT /api/notifications/{id}/read` - Mark as read

### Reports (Admin)
- `GET /api/admin/reports/overall` - Get overall statistics
- `GET /api/admin/reports/event/{eventId}` - Get event-specific report

## Default Users

An admin user is automatically created on application startup:
- **Email**: `admin@aiu.edu`
- **Password**: `admin123`

Students can register via the registration page and will automatically get the STUDENT role.

Access H2 Console at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:tripsdb`
- Username: `sa`
- Password: (leave empty)

## Recent Improvements (Code Structure Enhancement)

The codebase has been significantly improved with the following structural enhancements:

### Type Safety
- ✅ **Enums instead of strings**: BookingStatus, EventStatus, EventType, UserRole, PaymentMethod
- ✅ Compile-time type checking
- ✅ Better IDE support and autocomplete

### Error Handling
- ✅ **Custom exceptions**: ResourceNotFoundException, BookingException, ValidationException
- ✅ **GlobalExceptionHandler**: Centralized error handling with proper HTTP status codes
- ✅ Consistent error response format across all endpoints

### Validation
- ✅ **Jakarta Bean Validation**: All input DTOs validated
- ✅ Automatic validation before business logic
- ✅ Detailed error messages for invalid input

### Code Quality
- ✅ **Constants class**: Eliminates magic strings
- ✅ **Clean DTOs**: Separation between entities and API responses
- ✅ **Better maintainability**: Easier to understand and modify

See [CODE_STRUCTURE_IMPROVEMENTS.md](./Docs/CODE_STRUCTURE_IMPROVEMENTS.md) for detailed documentation.

## Architecture

### Design Patterns (11 Implemented)

The system implements 11 industry-standard design patterns for improved maintainability and scalability:

**Creational Patterns:**
- **Factory Pattern**: Centralized model creation and registration
- **Builder Pattern**: Complex activity object construction (Events & Trips)
- **Prototype Pattern**: Activity cloning and template-based creation
- **Abstract Factory**: Activity type-specific factory implementations

**Structural Patterns:**
- **Adapter Pattern**: Email service integration (SMTP adapter)
- **Bridge Pattern**: Decoupled notification channels and messages
- **Decorator Pattern**: Dynamic ticket service feature composition

**Behavioral Patterns:**
- **Command Pattern**: Request handling with command queue
- **Chain of Responsibility**: Multi-stage request processing (Auth → Authz → Validation)
- **State Pattern**: Activity lifecycle management (Upcoming → Completed/Cancelled)
- **Strategy Pattern**: Dynamic pricing strategies (Standard, Early Bird, Bulk)

**Plus:** Memento Pattern for state history and undo operations

📖 **Full documentation:** See [Design Patterns Report](./report/01_design_patterns.md)

### Backend (SOLID Principles)
- **Single Responsibility**: Each service handles one concern
- **Open/Closed**: Easily extensible without modifying existing code (via patterns)
- **Liskov Substitution**: Interface-based design with polymorphic hierarchies
- **Interface Segregation**: Focused repository interfaces
- **Dependency Inversion**: Dependencies injected via Spring, patterns use abstractions

### Code Quality Metrics
- **Coupling Reduction**: 46% decrease in class dependencies (5.2 → 2.8 avg)
- **Complexity Reduction**: 60% decrease in cyclomatic complexity (15-20 → 3-8)
- **Test Coverage**: 90% average across all modules
- **Pattern Quality**: 8.1-8.7/10 average implementation quality

### DRY Principle
- Reusable components and services
- No code duplication
- Shared utilities and DTOs
- Pattern-based code reuse

## Documentation

### 📊 PM3 Milestone Report

Comprehensive documentation of Milestone 3 deliverables is available in the [`/report/`](./report/) directory:

- **[Master Report Index](./report/README.md)** - Start here for complete overview
- **[Design Patterns & Functional Requirements](./report/01_design_patterns.md)** (10 marks) - 11 design patterns mapped to 40+ FRs
- **[Class Diagrams Before/After](./report/02_class_diagrams.md)** (20 marks) - Architectural evolution with 18 diagrams
- **[Recomputed Estimates & Charts](./report/03_recomputed_estimates.md)** (10 marks) - Schedule analysis with Gantt/Burndown
- **[Finalized Project Report](./report/04_project_report.md)** (30 marks) - Complete PM metrics and team productivity
- **[Vibe Coding Analysis](./report/05_vibe_coding_analysis.md)** (30 marks) - AI code generation comparison

**Key Highlights:**
- ✅ 11 design patterns implemented (Factory, Builder, Prototype, Command, Chain, State, Strategy, Decorator, Bridge, Adapter, Memento)
- ✅ 72.1% feature completion (88/122 SP)
- ✅ Comprehensive team productivity analysis (0.40 SP/day average)
- ✅ AI code generation analysis showing 62.5% time savings with pre-designed patterns

## Project Structure

```
AIU-Trips-And-Events/
├── Project/
│   ├── backend/
│   │   ├── src/main/java/com/aiu/trips/
│   │   │   ├── adapter/          # Adapter Pattern (Email service)
│   │   │   ├── bridge/           # Bridge Pattern (Notifications)
│   │   │   ├── builder/          # Builder Pattern (Activity builders)
│   │   │   ├── chain/            # Chain of Responsibility
│   │   │   ├── command/          # Command Pattern
│   │   │   ├── config/           # Configuration classes
│   │   │   ├── constants/        # Application constants
│   │   │   ├── controller/       # REST Controllers
│   │   │   ├── decorator/        # Decorator Pattern (Ticket services)
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── enums/            # Type-safe enumerations
│   │   │   ├── exception/        # Custom exceptions & handlers
│   │   │   ├── factory/          # Factory Pattern (Model factory)
│   │   │   ├── memento/          # Memento Pattern (State history)
│   │   │   ├── model/            # JPA Entity Models
│   │   │   ├── prototype/        # Prototype Pattern (Activity cloning)
│   │   │   ├── repository/       # Data Access Layer
│   │   │   ├── security/         # Security & JWT Config
│   │   │   ├── service/          # Business Logic
│   │   │   ├── state/            # State Pattern (Activity lifecycle)
│   │   │   ├── strategy/         # Strategy Pattern (Pricing)
│   │   │   └── util/             # Utilities (QR Code)
│   │   └── pom.xml
│   ├── frontend/
│   │   ├── app/                  # Next.js Pages
│   │   ├── components/           # React Components
│   │   ├── contexts/             # Context Providers
│   │   ├── lib/                  # API Client
│   │   └── package.json
├── Milestones/                   # Project milestones
│   ├── pm/                       # Milestone 1 docs
│   ├── Pm_2/                     # Milestone 2 docs
│   └── PM_3/                     # Milestone 3 docs & diagrams
├── report/                       # PM3 Comprehensive Report (NEW!)
│   ├── README.md                 # Master report index
│   ├── 01_design_patterns.md
│   ├── 02_class_diagrams.md
│   ├── 03_recomputed_estimates.md
│   ├── 04_project_report.md
│   └── 05_vibe_coding_analysis.md
└── README.md
```

## License

This project is for educational purposes.
