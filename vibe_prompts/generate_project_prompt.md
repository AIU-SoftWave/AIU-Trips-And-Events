# Generate Complete AIU Trips & Events Project

> **Single Prompt to Generate Entire Project from Scratch**

This prompt will generate the complete AIU Trips & Events Management System including all backend, frontend, and configuration files.

---

## 🤖 Complete Project Generation Prompt

**Copy this entire prompt to agentic AI:**

```
You are tasked with generating the complete AIU Trips & Events Management System from scratch.

CONTEXT:
- Repository root: /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events
- Read system specifications from: /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/data/system_context.md
- Output directory: /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/outputs/FULL_PROJECT/

PROJECT REQUIREMENTS:

================================
BACKEND (Java Spring Boot)
================================

1. ENTITY LAYER (JPA Entities with all relationships):
   - User (id, username, email, password, role, createdAt, updatedAt)
   - Activity (abstract base class: id, name, description, startDate, endDate, location, capacity, price, status, category, organizerId)
   - Event (extends Activity: eventType, speaker, agenda)
   - Trip (extends Activity: destination, itinerary, transportation)
   - Booking (id, userId, activityId, bookingDate, status, totalPrice, paymentStatus)
   - Ticket (id, bookingId, ticketCode, qrCode, isUsed, usedAt)
   - Notification (id, userId, type, channel, subject, message, status, sentAt)
   - Report (id, reportType, generatedBy, generatedAt, data, format)
   - Feedback (id, userId, activityId, rating, comment, createdAt)

2. REPOSITORY LAYER (Spring Data JPA):
   - All repositories extending JpaRepository
   - Custom query methods for:
     * Find bookings by user/activity/status
     * Find activities by date range/category/status
     * Find notifications by user/status
     * Get booking statistics
     * Get activity analytics
     * Find feedback by activity with average rating

3. SERVICE LAYER (Business Logic):
   - UserService: register, login, updateProfile, deleteUser, findAll
   - ActivityService: create, update, delete, findAll, findByCategory, findByDateRange, checkAvailability
   - EventService: specific event operations
   - TripService: specific trip operations  
   - BookingService: createBooking, cancelBooking, getBookingsByUser, calculatePrice
   - TicketService: generateTicket, validateTicket, useTicket
   - NotificationService: sendNotification (Email/SMS/Push), getNotificationsByUser
   - ReportService: generateActivityReport, generateUserReport, generateFinancialReport, exportReport
   - FeedbackService: submitFeedback, getFeedbackByActivity, getAverageRating
   
   All services must include:
   - @Transactional annotations where needed
   - Proper error handling
   - Input validation
   - Logging

4. CONTROLLER LAYER (REST APIs):
   - UserController: /api/users (POST, GET, PUT, DELETE)
   - ActivityController: /api/activities (CRUD operations)
   - EventController: /api/events (CRUD operations)
   - TripController: /api/trips (CRUD operations)
   - BookingController: /api/bookings (CRUD, cancel, user bookings)
   - TicketController: /api/tickets (generate, validate, use)
   - NotificationController: /api/notifications (send, get user notifications)
   - ReportController: /api/reports (generate, export)
   - FeedbackController: /api/feedback (submit, get by activity)
   
   All controllers must include:
   - @RestController and @RequestMapping annotations
   - Proper HTTP methods and status codes
   - @Valid for validation
   - DTOs for request/response
   - Error handling

5. DESIGN PATTERNS (All 11 patterns):
   
   a. Factory Pattern (com.aiu.trips.factory):
      - ActivityFactory interface
      - EventFactory and TripFactory implementations
      - Usage in activity creation
   
   b. Builder Pattern (com.aiu.trips.builder):
      - ActivityBuilder interface
      - EventBuilder and TripBuilder implementations
      - Fluent API for complex object creation
   
   c. Prototype Pattern (com.aiu.trips.prototype):
      - Cloneable interface for Activity
      - Deep copy implementations
   
   d. Command Pattern (com.aiu.trips.command):
      - Command interface
      - CreateActivityCommand, UpdateActivityCommand, DeleteActivityCommand
      - CommandInvoker
      - Integration with controllers
   
   e. Chain of Responsibility (com.aiu.trips.chain):
      - Handler interface
      - ValidationHandler, AuthorizationHandler, LoggingHandler
      - Request processing chain
   
   f. State Pattern (com.aiu.trips.state):
      - ActivityState interface
      - PendingState, ActiveState, CompletedState, CancelledState
      - Context class for state management
   
   g. Strategy Pattern (com.aiu.trips.strategy):
      - PricingStrategy interface
      - EarlyBirdStrategy, StudentDiscountStrategy, GroupDiscountStrategy, SeasonalStrategy
      - Context class for price calculation
   
   h. Decorator Pattern (com.aiu.trips.decorator):
      - Ticket decorator for enhancements
      - VIPTicket, GroupTicket, InsuranceTicket decorators
   
   i. Bridge Pattern (com.aiu.trips.bridge):
      - NotificationSender interface
      - EmailSender, SMSSender, PushSender implementations
      - Abstraction for notification channels
   
   j. Adapter Pattern (com.aiu.trips.adapter):
      - EmailServiceAdapter for external email service
      - PaymentGatewayAdapter for payment integration
   
   k. Memento Pattern (com.aiu.trips.memento):
      - ActivityMemento for state preservation
      - Caretaker for managing mementos
      - Undo/Redo functionality

6. CONFIGURATION:
   - application.properties:
     * Database configuration (MySQL)
     * Server port (8080)
     * JPA settings
     * Logging configuration
   - SecurityConfig (basic authentication)
   - CorsConfig (for frontend integration)
   - SwaggerConfig (API documentation)

7. EXCEPTION HANDLING:
   - GlobalExceptionHandler with @ControllerAdvice
   - Custom exceptions:
     * ResourceNotFoundException
     * InvalidInputException
     * BookingException
     * NotificationException
   - Proper error response format

8. DTOs (Data Transfer Objects):
   - UserDTO, ActivityDTO, EventDTO, TripDTO
   - BookingDTO, TicketDTO, NotificationDTO
   - ReportDTO, FeedbackDTO
   - Proper validation annotations

9. MAPPERS:
   - UserMapper, ActivityMapper, BookingMapper, etc.
   - Entity to DTO conversions
   - DTO to Entity conversions

10. TESTS:
    - Unit tests for all service methods
    - Integration tests for controllers
    - Test configuration
    - Mock data setup

================================
FRONTEND (React.js)
================================

1. PROJECT SETUP:
   - package.json with all dependencies:
     * react, react-dom, react-router-dom
     * axios for API calls
     * Context API for state management
     * CSS modules or styled-components
   - .env for environment variables
   - ESLint and Prettier configuration

2. FOLDER STRUCTURE:
   ```
   src/
   ├── components/
   │   ├── auth/
   │   │   ├── Login.jsx
   │   │   ├── Register.jsx
   │   │   └── Profile.jsx
   │   ├── activities/
   │   │   ├── ActivityList.jsx
   │   │   ├── ActivityDetail.jsx
   │   │   ├── ActivityForm.jsx (create/edit)
   │   │   ├── EventList.jsx
   │   │   ├── EventDetail.jsx
   │   │   ├── TripList.jsx
   │   │   └── TripDetail.jsx
   │   ├── bookings/
   │   │   ├── BookingForm.jsx
   │   │   ├── BookingList.jsx
   │   │   ├── BookingDetail.jsx
   │   │   └── BookingConfirmation.jsx
   │   ├── tickets/
   │   │   ├── TicketView.jsx
   │   │   └── QRCodeDisplay.jsx
   │   ├── notifications/
   │   │   ├── NotificationCenter.jsx
   │   │   └── NotificationList.jsx
   │   ├── reports/
   │   │   ├── ReportDashboard.jsx
   │   │   ├── ActivityReport.jsx
   │   │   ├── UserReport.jsx
   │   │   └── FinancialReport.jsx
   │   ├── feedback/
   │   │   ├── FeedbackForm.jsx
   │   │   └── FeedbackList.jsx
   │   ├── admin/
   │   │   ├── Dashboard.jsx
   │   │   ├── UserManagement.jsx
   │   │   ├── ActivityManagement.jsx
   │   │   └── ReportGeneration.jsx
   │   └── common/
   │       ├── Navbar.jsx
   │       ├── Footer.jsx
   │       ├── Loading.jsx
   │       ├── ErrorBoundary.jsx
   │       ├── Modal.jsx
   │       └── FormInput.jsx
   ├── services/
   │   ├── api.js (axios instance)
   │   ├── authService.js
   │   ├── activityService.js
   │   ├── bookingService.js
   │   ├── ticketService.js
   │   ├── notificationService.js
   │   ├── reportService.js
   │   └── feedbackService.js
   ├── contexts/
   │   ├── AuthContext.jsx
   │   ├── ThemeContext.jsx
   │   └── NotificationContext.jsx
   ├── hooks/
   │   ├── useAuth.js
   │   ├── useApi.js
   │   └── useForm.js
   ├── utils/
   │   ├── validation.js
   │   ├── formatting.js
   │   └── storage.js
   ├── routes/
   │   ├── AppRoutes.jsx
   │   ├── ProtectedRoute.jsx
   │   └── PublicRoute.jsx
   ├── styles/
   │   ├── global.css
   │   ├── variables.css
   │   └── components/ (CSS modules)
   ├── App.jsx
   └── index.jsx
   ```

3. CORE FEATURES:
   - Authentication (login, register, logout, token management)
   - Activity browsing (list, filter, search, pagination)
   - Booking workflow (select activity, book, pay, confirm)
   - Ticket display (QR code, details, status)
   - Notification center (real-time notifications, mark as read)
   - User profile (view, edit, booking history)
   - Admin dashboard (users, activities, reports)
   - Feedback system (submit review, view ratings)

4. STATE MANAGEMENT:
   - AuthContext for authentication state
   - Global state for user data
   - Local state for component-specific data
   - Context providers in App.jsx

5. ROUTING:
   - Public routes: /, /login, /register
   - Protected routes: /profile, /bookings, /admin/*
   - 404 page
   - Route guards for authentication

6. STYLING:
   - Responsive design (mobile-first)
   - Consistent theme
   - Reusable CSS classes
   - Component-specific styles

================================
DATABASE
================================

1. SCHEMA SQL:
   - All table creation statements
   - Foreign key constraints
   - Indexes for performance
   - Initial data/seed data

================================
DOCUMENTATION
================================

1. ROOT README.md:
   - Project overview
   - Technology stack
   - Architecture diagram
   - Setup instructions
   - API documentation
   - Design patterns explanation

2. BACKEND README:
   - Setup instructions
   - Build commands (mvn clean install)
   - Run instructions (mvn spring-boot:run)
   - API endpoints documentation
   - Environment variables

3. FRONTEND README:
   - Setup instructions
   - Install dependencies (npm install)
   - Run development server (npm start)
   - Build production (npm run build)
   - Environment variables

4. API DOCUMENTATION:
   - Swagger/OpenAPI specification
   - Endpoint descriptions
   - Request/response examples
   - Error codes

================================
BUILD & DEPLOYMENT
================================

1. Backend (Maven):
   - pom.xml with all dependencies
   - Build configuration
   - Profiles (dev, prod)

2. Frontend (npm):
   - package.json
   - Build scripts
   - Environment configuration

3. Docker (optional):
   - Dockerfile for backend
   - Dockerfile for frontend
   - docker-compose.yml

================================
ACTIONS TO PERFORM
================================

1. Create output directory:
   mkdir -p /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/outputs/FULL_PROJECT

2. Generate complete project structure with ALL files:
   /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/outputs/FULL_PROJECT/
   ├── backend/ (Spring Boot project)
   │   ├── src/
   │   │   ├── main/
   │   │   │   ├── java/com/aiu/trips/
   │   │   │   │   ├── model/
   │   │   │   │   ├── repository/
   │   │   │   │   ├── service/
   │   │   │   │   ├── controller/
   │   │   │   │   ├── dto/
   │   │   │   │   ├── mapper/
   │   │   │   │   ├── exception/
   │   │   │   │   ├── config/
   │   │   │   │   ├── factory/
   │   │   │   │   ├── builder/
   │   │   │   │   ├── prototype/
   │   │   │   │   ├── command/
   │   │   │   │   ├── chain/
   │   │   │   │   ├── state/
   │   │   │   │   ├── strategy/
   │   │   │   │   ├── decorator/
   │   │   │   │   ├── bridge/
   │   │   │   │   ├── adapter/
   │   │   │   │   ├── memento/
   │   │   │   │   └── TripsApplication.java
   │   │   │   └── resources/
   │   │   │       ├── application.properties
   │   │   │       └── schema.sql
   │   │   └── test/
   │   ├── pom.xml
   │   └── README.md
   ├── frontend/ (React project)
   │   ├── public/
   │   ├── src/ (all components, services, contexts, hooks)
   │   ├── package.json
   │   └── README.md
   ├── database/
   │   └── schema.sql
   ├── docs/
   │   ├── API.md
   │   ├── ARCHITECTURE.md
   │   └── DESIGN_PATTERNS.md
   └── README.md

3. Ensure all files are complete and compilable:
   - All imports are correct
   - All dependencies are specified
   - All relationships are properly mapped
   - All patterns are correctly implemented

4. Create comprehensive documentation:
   - Setup instructions for both backend and frontend
   - API documentation
   - Architecture explanation
   - Design patterns documentation

5. Generate test report:
   - Copy /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/report_template.md to /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/reports/FULL_PROJECT_report.md
   - Fill with:
     * Total files generated
     * Lines of code
     * Compilation status
     * Pattern implementation verification
     * Complete feature checklist

================================
SUCCESS CRITERIA
================================

✅ Backend compiles successfully (mvn clean install)
✅ Frontend builds successfully (npm run build)
✅ All 11 design patterns implemented correctly
✅ All REST APIs properly defined
✅ All JPA relationships correctly mapped
✅ All React components functional
✅ Complete documentation provided
✅ No compilation errors
✅ Clean code organization
✅ Proper error handling throughout
✅ Ready for immediate deployment

================================
ESTIMATED TIME
================================

This is a comprehensive task estimated at 90-120 minutes to generate the complete project with all components, patterns, and documentation.

================================
IMPORTANT NOTES
================================

1. Generate COMPLETE files, not placeholders
2. All code must be production-ready
3. Include proper error handling everywhere
4. Add JavaDoc/JSDoc comments
5. Ensure all patterns are correctly implemented
6. Make sure backend and frontend integrate properly
7. Provide clear setup instructions
8. Include sample data/seeds if helpful

================================
END OF PROMPT
================================
```

---

## Usage

1. Copy the entire prompt above (from "You are tasked with..." to "END OF PROMPT")
2. Paste to an agentic AI with repository access
3. Wait for complete project generation (90-120 minutes)
4. Review generated files at: `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/outputs/FULL_PROJECT/`
5. Check the generated report at: `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/reports/FULL_PROJECT_report.md`

---

## What Gets Generated

### Backend (~150 files)
- 9 Entity classes
- 9 Repository interfaces
- 9 Service classes
- 9 Controller classes
- 9 DTOs
- 9 Mappers
- 4 Exception classes
- 3 Configuration classes
- All 11 design pattern implementations (~40 classes)
- Test classes
- Configuration files
- Documentation

### Frontend (~80 files)
- All React components
- All services
- All contexts
- All hooks
- All utilities
- All routes
- Styling files
- Configuration files

### Total: ~230+ files representing a complete, production-ready application

---

## After Generation

1. **Test Backend:**
   ```bash
   cd /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/outputs/FULL_PROJECT/backend
   mvn clean install
   mvn spring-boot:run
   ```

2. **Test Frontend:**
   ```bash
   cd /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/outputs/FULL_PROJECT/frontend
   npm install
   npm start
   ```

3. **Review Report:**
   Check quality metrics and compilation results in the generated report

4. **Deploy:**
   Follow deployment instructions in the generated README.md

---

**This single prompt replaces all 15 individual prompts and generates the entire project at once!**
