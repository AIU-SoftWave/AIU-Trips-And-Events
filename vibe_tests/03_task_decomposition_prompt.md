# Task Decomposition Prompt: Step-by-Step Project Generation

## Repository Context
**Location:** `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/`
**Data Source:** `vibe_tests/data/` (PlantUML diagrams - After DP)
**Target:** `Project/` directory

---

## Main Task: Generate Complete AIU Trips & Events Management System

Break down into these major tasks:

---

## TASK 1: Preparation & Analysis (15 minutes)

### Subtask 1.1: Read All PlantUML Diagrams
**Location:** `vibe_tests/data/`

**Action:** Parse each `.pu` file and extract:
- Class names and types (class, interface, abstract, enum)
- Attributes with types
- Methods with parameters and return types
- Relationships (inheritance, implementation, association, composition)
- Design patterns used

**Files to read:**
1. University_Trips_Events_Management_System.pu
2. Model_Factory.pu
3. Data_Layer.pu
4. Event_Management.pu
5. User_Management_.pu
6. Booking_Ticketing.pu
7. Notification.pu
8. Reports_Analytics.pu
9. Repository_Layer.pu
10. Controller.pu

**Output:** Mental map of complete system architecture

### Subtask 1.2: Create Implementation Order
**Priority:**
1. Core entities (model layer)
2. Enums
3. Creational patterns (Factory, Builder, Prototype)
4. Repository layer
5. Service layer
6. Structural patterns (Adapter, Bridge, Decorator)
7. Behavioral patterns (Command, Chain, State, Strategy, Memento)
8. Controllers
9. Security configuration
10. Frontend components

---

## TASK 2: Backend Setup (10 minutes)

### Subtask 2.1: Create Project Structure
**Target:** `Project/backend/`

**Action:** Create directory structure:
```
backend/
├── src/
│   └── main/
│       ├── java/com/aiu/trips/
│       │   ├── model/
│       │   ├── enums/
│       │   ├── factory/
│       │   ├── builder/
│       │   ├── prototype/
│       │   ├── state/
│       │   ├── strategy/
│       │   ├── decorator/
│       │   ├── bridge/
│       │   ├── adapter/
│       │   ├── memento/
│       │   ├── command/
│       │   ├── chain/
│       │   ├── repository/
│       │   ├── service/
│       │   ├── controller/
│       │   ├── dto/
│       │   ├── exception/
│       │   └── config/
│       └── resources/
│           └── application.properties
├── pom.xml
└── Dockerfile
```

### Subtask 2.2: Create pom.xml
**Dependencies needed:**
- Spring Boot Starter Web (3.2.0)
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- H2 Database
- JWT (io.jsonwebtoken:jjwt)
- Lombok
- ZXing (QR codes)
- Spring Boot Starter Validation

---

## TASK 3: Implement Core Entities (30 minutes)

### Subtask 3.1: Create Enums
**Source:** All diagrams
**Location:** `backend/src/main/java/com/aiu/trips/enums/`

**Create these enums:**
1. UserRole.java (STUDENT, ORGANIZER, ADMIN)
2. ActivityType.java (EVENT, TRIP)
3. ActivityStatus.java (DRAFT, UPCOMING, ONGOING, COMPLETED, CANCELLED)
4. BookingStatus.java (PENDING, CONFIRMED, CANCELLED, REFUNDED)
5. NotificationType.java (EMAIL, SMS, PUSH)
6. ReportType.java (PARTICIPANT_COUNT, INCOME, FEEDBACK_ANALYSIS)
7. ExportFormat.java (PDF, CSV, EXCEL)
8. ActivityCategory.java (SPORTS, CULTURAL, ACADEMIC, SOCIAL, ADVENTURE)
9. TicketType.java (STANDARD, VIP, STUDENT_DISCOUNT)

### Subtask 3.2: Create Core Entity Classes
**Source:** Data_Layer.pu, Event_Management.pu
**Location:** `backend/src/main/java/com/aiu/trips/model/`

**Create these entities (with JPA annotations):**

1. **User.java**
   - Fields: id, username, email, password, role, createdAt, updatedAt
   - Annotations: @Entity, @Table, @Id, @GeneratedValue, etc.

2. **Activity.java** (abstract)
   - Fields: id, title, description, category, status, startDate, endDate, location, capacity, price
   - Annotations: @Entity, @Inheritance(strategy = InheritanceType.JOINED)
   - Methods: abstract validate(), abstract calculateDuration()

3. **EventEntity.java** extends Activity
   - Additional fields: speaker, agenda, eventType
   - Override methods

4. **Trip.java** extends Activity
   - Additional fields: destination, transportation, accommodation
   - Override methods

5. **Booking.java**
   - Fields: id, user, activity, bookingDate, status, totalPrice, paymentStatus
   - Relationships: @ManyToOne with User and Activity

6. **Ticket.java**
   - Fields: id, booking, qrCode, issueDate, validationDate, isValidated
   - Relationships: @ManyToOne with Booking

7. **Notification.java**
   - Fields: id, user, type, message, sentAt, isRead
   - Relationships: @ManyToOne with User

8. **Report.java**
   - Fields: id, type, title, content, generatedBy, generatedAt
   - Relationships: @ManyToOne with User

9. **Feedback.java**
   - Fields: id, user, activity, rating, comment, submittedAt
   - Relationships: @ManyToOne with User and Activity

---

## TASK 4: Implement Creational Patterns (25 minutes)

### Subtask 4.1: Factory Pattern
**Source:** Model_Factory.pu
**Location:** `backend/src/main/java/com/aiu/trips/factory/`

**Create:**
1. IModelFactory.java (interface)
2. UserFactory.java
3. ActivityFactory.java
4. BookingFactory.java
5. ModelFactoryRegistry.java (registers all factories)

### Subtask 4.2: Builder Pattern
**Source:** Event_Management.pu
**Location:** `backend/src/main/java/com/aiu/trips/builder/`

**Create:**
1. ActivityBuilder.java (abstract builder)
2. EventBuilder.java (concrete builder for events)
3. TripBuilder.java (concrete builder for trips)
4. ActivityDirector.java (orchestrates building)

### Subtask 4.3: Prototype Pattern
**Source:** Event_Management.pu
**Location:** `backend/src/main/java/com/aiu/trips/prototype/`

**Create:**
1. IPrototype.java (interface with clone method)
2. Implement IPrototype in Activity class

---

## TASK 5: Implement Repository Layer (15 minutes)

### Subtask 5.1: Create Repositories
**Source:** Repository_Layer.pu
**Location:** `backend/src/main/java/com/aiu/trips/repository/`

**Create these interfaces (extend JpaRepository):**
1. UserRepository.java
   - Custom method: findByUsername(String username)
   - Custom method: findByEmail(String email)

2. ActivityRepository.java
   - Custom method: findByStatus(ActivityStatus status)
   - Custom method: findByCategory(ActivityCategory category)

3. EventRepository.java (extends ActivityRepository)

4. TripRepository.java (extends ActivityRepository)

5. BookingRepository.java
   - Custom method: findByUserId(Long userId)
   - Custom method: findByActivityId(Long activityId)

6. TicketRepository.java
   - Custom method: findByQrCode(String qrCode)

7. NotificationRepository.java
   - Custom method: findByUserIdAndIsRead(Long userId, Boolean isRead)

8. ReportRepository.java

9. FeedbackRepository.java
   - Custom method: findByActivityId(Long activityId)

---

## TASK 6: Implement Service Layer (40 minutes)

### Subtask 6.1: Create DTOs
**Location:** `backend/src/main/java/com/aiu/trips/dto/`

**Create DTOs for:**
1. UserDTO, LoginRequest, RegisterRequest
2. ActivityDTO, CreateActivityRequest, UpdateActivityRequest
3. BookingDTO, CreateBookingRequest
4. NotificationDTO
5. ReportDTO, GenerateReportRequest
6. FeedbackDTO, SubmitFeedbackRequest

### Subtask 6.2: Create Service Interfaces and Implementations
**Location:** `backend/src/main/java/com/aiu/trips/service/`

**Create:**
1. **UserService.java**
   - Methods: register, login, getProfile, updateProfile

2. **ActivityService.java**
   - Methods: create, update, delete, getById, getAll, changeStatus

3. **BookingService.java**
   - Methods: createBooking, cancelBooking, getMyBookings, getBookingById

4. **TicketService.java**
   - Methods: generateTicket, validateTicket, getTicketByQrCode

5. **NotificationService.java**
   - Methods: sendNotification, getMyNotifications, markAsRead

6. **ReportService.java**
   - Methods: generateReport, exportReport, getAllReports

7. **FeedbackService.java**
   - Methods: submitFeedback, getFeedbackForActivity, getAverageRating

---

## TASK 7: Implement Structural Patterns (25 minutes)

### Subtask 7.1: Adapter Pattern
**Source:** Notification.pu
**Location:** `backend/src/main/java/com/aiu/trips/adapter/`

**Create:**
1. IEmailService.java (interface)
2. SmtpEmailAdapter.java (adapts external SMTP to our interface)

### Subtask 7.2: Bridge Pattern
**Source:** Notification.pu
**Location:** `backend/src/main/java/com/aiu/trips/bridge/`

**Create:**
1. NotificationMessage.java (abstraction)
2. EmailMessage.java, SmsMessage.java, PushMessage.java (refined abstractions)
3. INotificationChannel.java (implementor interface)
4. EmailChannel.java, SmsChannel.java, PushChannel.java (concrete implementors)

### Subtask 7.3: Decorator Pattern
**Source:** Booking_Ticketing.pu
**Location:** `backend/src/main/java/com/aiu/trips/decorator/`

**Create:**
1. ITicketService.java (component interface)
2. BasicTicketService.java (concrete component)
3. TicketServiceDecorator.java (abstract decorator)
4. InsuranceDecorator.java (adds insurance)
5. MealPlanDecorator.java (adds meal plan)

---

## TASK 8: Implement Behavioral Patterns (35 minutes)

### Subtask 8.1: Strategy Pattern
**Source:** Booking_Ticketing.pu
**Location:** `backend/src/main/java/com/aiu/trips/strategy/`

**Create:**
1. IPricingStrategy.java (interface)
2. StandardPricingStrategy.java
3. EarlyBirdPricingStrategy.java
4. BulkGroupDiscountStrategy.java

### Subtask 8.2: State Pattern
**Source:** Event_Management.pu
**Location:** `backend/src/main/java/com/aiu/trips/state/`

**Create:**
1. IActivityState.java (interface)
2. UpcomingState.java
3. CompletedState.java
4. CancelledState.java
5. ActivityLifecycle.java (context)

### Subtask 8.3: Command Pattern
**Source:** Controller.pu
**Location:** `backend/src/main/java/com/aiu/trips/command/`

**Create:**
1. IControllerCommand.java (interface)
2. Concrete commands: RegisterCommand, LoginCommand, CreateEventCommand, UpdateEventCommand, DeleteEventCommand, BookEventCommand, SendNotificationCommand, GenerateReportCommand, etc.
3. ControllerCommandInvoker.java

### Subtask 8.4: Chain of Responsibility Pattern
**Source:** Controller.pu
**Location:** `backend/src/main/java/com/aiu/trips/chain/`

**Create:**
1. IRequestHandler.java (interface)
2. AuthenticationHandler.java
3. AuthorizationHandler.java
4. ValidationHandler.java
5. RateLimitHandler.java

### Subtask 8.5: Memento Pattern
**Source:** Data_Layer.pu
**Location:** `backend/src/main/java/com/aiu/trips/memento/`

**Create:**
1. ActivityMemento.java
2. BookingMemento.java
3. ActivityHistoryCaretaker.java
4. BookingHistoryCaretaker.java

---

## TASK 9: Implement Controllers (30 minutes)

### Subtask 9.1: Create REST Controllers
**Source:** Controller.pu
**Location:** `backend/src/main/java/com/aiu/trips/controller/`

**Create:**
1. **UserController.java** (@RestController, @RequestMapping("/api/users"))
   - POST /register
   - POST /login
   - GET /profile
   - PUT /profile

2. **ActivityController.java** (@RequestMapping("/api/activities"))
   - GET /activities
   - GET /activities/{id}
   - POST /activities
   - PUT /activities/{id}
   - DELETE /activities/{id}
   - PATCH /activities/{id}/status

3. **BookingController.java** (@RequestMapping("/api/bookings"))
   - POST /bookings
   - GET /bookings (my bookings)
   - GET /bookings/{id}
   - DELETE /bookings/{id}

4. **TicketController.java** (@RequestMapping("/api/tickets"))
   - GET /tickets/{bookingId}
   - POST /tickets/{qrCode}/validate

5. **NotificationController.java** (@RequestMapping("/api/notifications"))
   - GET /notifications
   - PATCH /notifications/{id}/read

6. **ReportController.java** (@RequestMapping("/api/reports"))
   - POST /reports/generate
   - GET /reports
   - GET /reports/{id}/export

7. **FeedbackController.java** (@RequestMapping("/api/feedback"))
   - POST /feedback
   - GET /activities/{id}/feedback

---

## TASK 10: Configure Security (20 minutes)

### Subtask 10.1: JWT Configuration
**Location:** `backend/src/main/java/com/aiu/trips/config/`

**Create:**
1. JwtUtil.java (token generation and validation)
2. JwtAuthenticationFilter.java (filter for JWT validation)
3. SecurityConfig.java (Spring Security configuration)

### Subtask 10.2: Exception Handling
**Location:** `backend/src/main/java/com/aiu/trips/exception/`

**Create:**
1. GlobalExceptionHandler.java (@ControllerAdvice)
2. Custom exceptions: UserNotFoundException, ActivityNotFoundException, BookingException, etc.

### Subtask 10.3: Application Configuration
**Location:** `backend/src/main/resources/application.properties`

**Configure:**
- Server port (8080)
- H2 database settings
- JWT secret and expiration
- CORS settings

---

## TASK 11: Frontend Setup (10 minutes)

### Subtask 11.1: Create Project Structure
**Target:** `Project/frontend/`

**Action:** Initialize Next.js project with TypeScript and Tailwind CSS:
```
frontend/
├── src/
│   ├── app/
│   ├── components/
│   ├── services/
│   ├── contexts/
│   ├── hooks/
│   ├── types/
│   └── utils/
├── public/
├── package.json
├── tsconfig.json
├── tailwind.config.js
└── Dockerfile
```

### Subtask 11.2: Create package.json
**Dependencies:**
- next (15.x)
- react, react-dom (18.x)
- typescript
- tailwindcss
- axios
- qrcode.react
- @heroicons/react

---

## TASK 12: Implement Frontend Components (60 minutes)

### Subtask 12.1: Create Type Definitions
**Location:** `frontend/src/types/`

**Create TypeScript types:**
1. User.ts
2. Activity.ts
3. Booking.ts
4. Ticket.ts
5. Notification.ts
6. Report.ts
7. Feedback.ts

### Subtask 12.2: Create API Service
**Location:** `frontend/src/services/api/`

**Create:**
1. axiosConfig.ts (Axios instance with interceptors)
2. authService.ts (register, login, logout)
3. activityService.ts (CRUD operations)
4. bookingService.ts (booking operations)
5. notificationService.ts (get notifications)
6. reportService.ts (generate and view reports)
7. feedbackService.ts (submit and view feedback)

### Subtask 12.3: Create Context Providers
**Location:** `frontend/src/contexts/`

**Create:**
1. AuthContext.tsx (authentication state)
2. ThemeContext.tsx (theme preferences)

### Subtask 12.4: Create Pages (App Router)
**Location:** `frontend/src/app/`

**Create:**
1. page.tsx (Home/Landing)
2. login/page.tsx
3. register/page.tsx
4. activities/page.tsx (list)
5. activities/[id]/page.tsx (detail)
6. activities/create/page.tsx
7. bookings/page.tsx (my bookings)
8. bookings/[id]/page.tsx (booking detail)
9. profile/page.tsx
10. admin/page.tsx (admin dashboard)
11. reports/page.tsx

### Subtask 12.5: Create Reusable Components
**Location:** `frontend/src/components/common/`

**Create:**
1. Header.tsx
2. Footer.tsx
3. Navigation.tsx
4. Button.tsx
5. Input.tsx
6. Card.tsx
7. Modal.tsx
8. Loading.tsx
9. ErrorMessage.tsx

### Subtask 12.6: Create Feature Components
**Location:** `frontend/src/components/`

**Create:**
- activities/: ActivityCard, ActivityList, ActivityForm, ActivityDetail
- bookings/: BookingForm, BookingCard, TicketDisplay (with QR)
- notifications/: NotificationCenter, NotificationItem
- reports/: ReportDashboard, ReportChart
- feedback/: FeedbackForm, FeedbackList

---

## TASK 13: Docker Configuration (15 minutes)

### Subtask 13.1: Backend Dockerfile
**Location:** `Project/backend/Dockerfile`

**Create multi-stage build:**
- Stage 1: Maven build
- Stage 2: Run with JRE

### Subtask 13.2: Frontend Dockerfile
**Location:** `Project/frontend/Dockerfile`

**Create multi-stage build:**
- Stage 1: npm build
- Stage 2: Serve with Node

### Subtask 13.3: docker-compose.yml
**Location:** `Project/docker-compose.yml`

**Configure:**
- backend service (port 8080)
- frontend service (port 3000)
- networks and volumes

---

## TASK 14: Testing & Verification (20 minutes)

### Subtask 14.1: Backend Verification
- ✅ Compile backend (mvn clean install)
- ✅ Run backend (mvn spring-boot:run)
- ✅ Test API endpoints (Postman/curl)

### Subtask 14.2: Frontend Verification
- ✅ Compile frontend (npm run build)
- ✅ Run frontend (npm run dev)
- ✅ Test user flows

### Subtask 14.3: Integration Testing
- ✅ Test authentication flow
- ✅ Test activity creation and booking
- ✅ Test QR code generation
- ✅ Test notifications
- ✅ Test reports

---

## Execution Checklist

Use this checklist to track progress:

- [ ] Task 1: Preparation & Analysis
- [ ] Task 2: Backend Setup
- [ ] Task 3: Core Entities
- [ ] Task 4: Creational Patterns
- [ ] Task 5: Repository Layer
- [ ] Task 6: Service Layer
- [ ] Task 7: Structural Patterns
- [ ] Task 8: Behavioral Patterns
- [ ] Task 9: Controllers
- [ ] Task 10: Security Configuration
- [ ] Task 11: Frontend Setup
- [ ] Task 12: Frontend Components
- [ ] Task 13: Docker Configuration
- [ ] Task 14: Testing & Verification

---

**Total Estimated Time:** ~6 hours
**Expected Quality:** 8.5+/10
**Target Location:** `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/Project/`

**Start with Task 1 and proceed sequentially through Task 14.**
