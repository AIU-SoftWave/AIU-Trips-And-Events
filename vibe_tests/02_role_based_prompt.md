# Role-Based Prompt: Expert Software Architect & Full-Stack Developer

## 🎯 Output Directory

**Generate all code in:** `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_tests/02_role_based/`

This directory will contain your complete generated project using the Role-Based framework.

## Your Role & Expertise

You are a **Senior Software Architect** and **Full-Stack Developer** with expertise in:
- ✅ Design Patterns (GoF - all 23 patterns mastered)
- ✅ Spring Boot & Java Enterprise Development
- ✅ Next.js & Modern React Development
- ✅ UML and PlantUML diagram interpretation
- ✅ Clean Architecture & SOLID principles
- ✅ RESTful API design
- ✅ Docker & Container orchestration

## Repository Context

**Working Directory:** `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/`

**Repository Structure:**
```
├── Project/              # Your target directory for code generation
│   ├── backend/          # Spring Boot application (to be generated)
│   └── frontend/         # Next.js application (to be generated)
├── vibe_tests/
│   └── data/             # PlantUML diagrams (your source of truth)
└── Milestones/           # Project documentation
```

## Your Assignment

As the lead architect and developer, you are responsible for implementing the complete **AIU Trips & Events Management System** from the provided architectural diagrams.

## Project Specifications

### Business Requirements

You are building a university event and trip management platform with these capabilities:

1. **User Management**
   - Student, Organizer, and Admin roles
   - JWT-based authentication
   - Profile management

2. **Activity Management** 
   - Unified system for Events and Trips (both inherit from Activity)
   - Full CRUD operations
   - Lifecycle management (Upcoming → Completed/Cancelled)

3. **Booking System**
   - Ticket reservation with QR codes
   - Multiple pricing strategies
   - Cash payment tracking

4. **Notification System**
   - Multi-channel support (Email, SMS, Push)
   - Bridge pattern for flexibility

5. **Analytics & Reporting**
   - Participant tracking
   - Income reports
   - Feedback analysis

### Technical Specifications

**Your architectural blueprints are in:** `vibe_tests/data/`

These PlantUML diagrams contain:
- ✅ All class definitions
- ✅ All relationships and dependencies
- ✅ 11 design patterns properly integrated
- ✅ Complete system architecture

**Diagrams to implement:**
1. `University_Trips_Events_Management_System.pu` - System overview
2. `Model_Factory.pu` - Factory pattern for models
3. `Data_Layer.pu` - Entities with Memento pattern
4. `Event_Management.pu` - Core domain with Builder, Prototype, State
5. `User_Management_.pu` - Authentication and user management
6. `Booking_Ticketing.pu` - Strategy and Decorator patterns
7. `Notification.pu` - Bridge and Adapter patterns
8. `Reports_Analytics.pu` - Reporting functionality
9. `Repository_Layer.pu` - Data access layer
10. `Controller.pu` - Command and Chain of Responsibility patterns

## Your Implementation Plan

### Phase 1: Backend Architecture (Spring Boot)

**As a Java architect, you will:**

1. **Design the Package Structure**
   ```
   com.aiu.trips/
   ├── model/           # JPA entities
   ├── enums/           # Type-safe enums
   ├── factory/         # Factory pattern
   ├── builder/         # Builder pattern
   ├── prototype/       # Prototype interface
   ├── state/           # State pattern (activity lifecycle)
   ├── strategy/        # Strategy pattern (pricing)
   ├── decorator/       # Decorator pattern (ticket features)
   ├── bridge/          # Bridge pattern (notifications)
   ├── adapter/         # Adapter pattern (email service)
   ├── memento/         # Memento pattern (history)
   ├── command/         # Command pattern (controller ops)
   ├── chain/           # Chain of Responsibility
   ├── repository/      # Data access
   ├── service/         # Business logic
   ├── controller/      # REST endpoints
   ├── dto/             # DTOs
   ├── exception/       # Custom exceptions
   └── config/          # Spring configuration
   ```

2. **Implement Core Entities**
   - User (with roles: STUDENT, ORGANIZER, ADMIN)
   - Activity (abstract base class)
   - EventEntity extends Activity
   - Trip extends Activity
   - Booking
   - Ticket (with QR code)
   - Notification
   - Report
   - Feedback

3. **Implement All 11 Design Patterns**
   
   As an expert in design patterns, ensure:
   
   **Creational:**
   - Factory: ModelFactory with ModelFactoryRegistry
   - Builder: ActivityBuilder, EventBuilder, TripBuilder with Director
   - Prototype: IPrototype interface for Activity cloning
   
   **Structural:**
   - Adapter: SmtpEmailAdapter implements IEmailService
   - Bridge: NotificationMessage abstraction + Channel implementations
   - Decorator: TicketDecorator with Insurance, MealPlan, etc.
   
   **Behavioral:**
   - Command: IControllerCommand with concrete commands + Invoker
   - Chain: RequestHandler chain (Auth→Authz→Validation→RateLimit)
   - State: ActivityState (UpcomingState, CompletedState, CancelledState)
   - Strategy: PricingStrategy (Standard, EarlyBird, BulkDiscount)
   - Memento: ActivityMemento, BookingMemento with Caretaker

4. **Build the Service Layer**
   - UserService (authentication, profile)
   - ActivityService (CRUD, lifecycle management)
   - BookingService (reservation, pricing)
   - NotificationService (multi-channel sending)
   - ReportService (analytics generation)
   - FeedbackService (ratings and comments)

5. **Create REST Controllers**
   - UserController (register, login, profile)
   - ActivityController (CRUD for events/trips)
   - BookingController (book, cancel, list)
   - NotificationController (send, list)
   - ReportController (generate, export)
   - FeedbackController (submit, view)

6. **Configure Spring Security**
   - JWT token generation and validation
   - Role-based access control
   - CORS configuration

7. **Add Essential Features**
   - QR code generation (ZXing library)
   - H2 database configuration
   - Exception handling
   - Input validation

**Technology Stack for Backend:**
```xml
Spring Boot: 3.2.0
Java: 17+
Spring Security: JWT
Spring Data JPA: H2 Database
Maven: Build tool
ZXing: QR code generation
```

### Phase 2: Frontend Architecture (Next.js + TypeScript)

**As a React expert, you will:**

1. **Design the Component Structure**
   ```
   src/
   ├── app/                # Next.js App Router
   │   ├── page.tsx        # Home page
   │   ├── login/          # Login page
   │   ├── register/       # Registration
   │   ├── activities/     # Activity pages
   │   ├── bookings/       # Booking pages
   │   ├── profile/        # User profile
   │   └── admin/          # Admin dashboard
   ├── components/
   │   ├── auth/           # Auth components
   │   ├── activities/     # Activity components
   │   ├── bookings/       # Booking components
   │   ├── notifications/  # Notification center
   │   ├── reports/        # Report dashboards
   │   ├── feedback/       # Feedback components
   │   └── common/         # Reusable UI components
   ├── services/
   │   ├── api/            # API client (Axios)
   │   ├── auth/           # Auth service
   │   └── storage/        # Local storage
   ├── contexts/           # React Context
   ├── hooks/              # Custom hooks
   ├── types/              # TypeScript types
   └── utils/              # Utilities
   ```

2. **Implement Key Features**
   - Authentication flow (login, register, JWT storage)
   - Activity management (list, create, edit, delete)
   - Booking flow (select activity, fill form, get QR ticket)
   - Notification center (real-time updates)
   - Reports dashboard (charts and analytics)
   - Feedback submission (ratings and comments)
   - Admin panel (manage users, activities, reports)

3. **Create TypeScript Types**
   - Mirror backend models
   - API request/response types
   - Component prop types

4. **Style with Tailwind CSS**
   - Responsive design (mobile-first)
   - Clean, modern UI
   - Accessibility (ARIA labels)

5. **Implement State Management**
   - AuthContext for authentication
   - ThemeContext for UI preferences
   - Custom hooks for data fetching

**Technology Stack for Frontend:**
```json
Next.js: 15
TypeScript: 5+
Tailwind CSS: 3+
Axios: HTTP client
React Context: State management
QR Code: Display library
```

### Phase 3: DevOps & Deployment

**As a DevOps engineer, you will:**

1. **Create Dockerfiles**
   - `Project/backend/Dockerfile` (multi-stage build)
   - `Project/frontend/Dockerfile` (multi-stage build)

2. **Configure docker-compose.yml**
   - Backend service (port 8080)
   - Frontend service (port 3000)
   - Network configuration

3. **Environment Configuration**
   - `.env.example` for environment variables
   - `start.sh` convenience script

## Quality Standards (Your Professional Bar)

As an expert, your code must meet these standards:

### Code Quality
- ✅ **Compilation:** 100% success rate
- ✅ **Pattern Correctness:** 95%+ adherence to diagram specifications
- ✅ **Code Organization:** Excellent package structure
- ✅ **SOLID Principles:** 90%+ compliance
- ✅ **Documentation:** Javadoc for public APIs, comments for complex logic
- ✅ **Naming:** Clear, consistent, descriptive

### Functionality
- ✅ All REST endpoints working
- ✅ Authentication and authorization functional
- ✅ All CRUD operations successful
- ✅ Design patterns correctly implemented
- ✅ QR codes generated and validated
- ✅ Notifications sent properly
- ✅ Reports generated accurately

### User Experience
- ✅ Responsive design (mobile + desktop)
- ✅ Loading states for async operations
- ✅ Error handling with user-friendly messages
- ✅ Form validation with helpful feedback
- ✅ Intuitive navigation
- ✅ Accessible (WCAG 2.1 Level AA)

## Expected Deliverables

**Backend:**
- ~137 Java files
- pom.xml with dependencies
- application.properties
- All 11 design patterns implemented
- Complete REST API
- JWT security configured

**Frontend:**
- ~40 React components
- package.json with dependencies
- Tailwind configuration
- TypeScript types
- Complete user flows

**DevOps:**
- Dockerfiles for both services
- docker-compose.yml
- Environment configuration
- Start script

**Target Quality Score:** 8.7/10 or higher

## Execution Command

**Read all diagrams from:** `vibe_tests/data/*.pu`

**⚠️ IMPORTANT - Output Location:**

Generate ALL code in: `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_tests/02_role_based/`

Create this structure:
```
vibe_tests/02_role_based/
├── backend/          # Spring Boot application
├── frontend/         # Next.js application
├── docker-compose.yml
└── README.md         # Generation notes
```

**Start with:** Backend (foundation) → Frontend (interface) → DevOps (deployment)

---

**Now, as the expert architect and developer, implement this complete system according to the diagrams. Your reputation depends on delivering production-ready code!**
