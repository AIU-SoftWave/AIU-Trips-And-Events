# Context-Aware Prompt: Complete System Generation with Full Repository Understanding

## Repository Location & Structure Awareness

**You are currently working in:** `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/`

**Current Repository Structure:**
```
AIU-Trips-And-Events/                           # Repository root
├── .git/                                        # Git metadata
├── .github/                                     # GitHub configuration
│   └── copilot-instructions.md                 # Coding standards
├── Project/                                     # 🎯 YOUR TARGET DIRECTORY
│   ├── backend/                                # Java Spring Boot app (GENERATE HERE)
│   ├── frontend/                               # Next.js app (GENERATE HERE)
│   ├── database/                               # Database scripts
│   ├── docs/                                   # Documentation
│   ├── docker-compose.yml                      # Container orchestration
│   └── .env.example                            # Environment template
├── Milestones/                                 # Project milestones & documentation
│   ├── PM_3/                                   # Milestone 3 (Design Patterns)
│   │   ├── Class Diagram/
│   │   │   ├── After DP/                       # ⭐ Source of truth diagrams
│   │   │   │   ├── University_Trips_Events_Management_System.pu
│   │   │   │   ├── Event_Management.pu
│   │   │   │   ├── Booking_Ticketing.pu
│   │   │   │   ├── User_Management_.pu
│   │   │   │   ├── Notification.pu
│   │   │   │   ├── Reports_Analytics.pu
│   │   │   │   ├── Controller.pu
│   │   │   │   ├── Repository_Layer.pu
│   │   │   │   ├── Data_Layer.pu
│   │   │   │   └── Model_Factory.pu
│   │   │   └── Before DP/                      # Reference only
│   │   ├── Project_without_DP_UML/            # Reference implementation (Scenario 1)
│   │   └── recomputed_estimates/              # Project estimates
│   ├── Pm_2/                                   # Milestone 2 (Requirements & diagrams)
│   └── pm/                                     # Milestone 1 (Initial planning)
├── report/                                     # Analysis & reports
│   ├── 05_vibe_coding_analysis.md             # 📊 Quality metrics reference
│   └── pdfs/                                  # PDF versions
├── vibe_tests/                                 # 📁 YOUR CURRENT CONTEXT
│   ├── data/                                   # PlantUML diagrams (copied from PM_3)
│   │   ├── README.md                          # Data documentation
│   │   └── *.pu                               # All After DP diagrams
│   ├── 01_chain_of_thought_prompt.md          # This prompt type
│   ├── 02_role_based_prompt.md                # Alternative prompt
│   ├── 03_task_decomposition_prompt.md        # Task breakdown
│   ├── 04_few_shot_examples_prompt.md         # Examples-based
│   └── 05_context_aware_prompt.md             # ⚡ YOU ARE HERE
├── README.md                                   # Project overview
└── .gitignore                                  # Git ignore rules
```

## Understanding the Project History & Context

### What Has Been Done (DO NOT REGENERATE)

The repository already contains:

1. **Documentation** (`Milestones/`, `report/`)
   - Architectural diagrams (PlantUML)
   - Project reports and analysis
   - Quality metrics (see `report/05_vibe_coding_analysis.md`)

2. **Existing Implementation** (`Milestones/PM_3/Project_without_DP_UML/`)
   - This was Scenario 1 experiment (Before DP → AI adds patterns)
   - Quality Score: 7.4/10
   - Reference only - DO NOT copy this code

3. **Main Project** (`Project/`)
   - This was Scenario 2 experiment (After DP diagrams)
   - Quality Score: 8.4/10
   - This is what you should UPDATE/REGENERATE

### What You Need to Do (YOUR TASK)

**Generate a fresh, complete implementation in `Project/` directory** based on the After DP diagrams, aiming for **8.7+/10 quality**.

## Source of Truth: After DP Diagrams

Your architectural blueprints are located in TWO places (same files):
1. **Original:** `Milestones/PM_3/Class Diagram/After DP/*.pu`
2. **Copied for convenience:** `vibe_tests/data/*.pu`

These diagrams represent the **final, pattern-integrated architecture** with:
- ✅ All 11 design patterns properly placed
- ✅ Complete class hierarchies
- ✅ All relationships defined
- ✅ Enums specified
- ✅ Package structure implied

### Diagram Overview & Dependencies

**Read in this order:**

1. **University_Trips_Events_Management_System.pu** (5 min)
   - System overview
   - High-level architecture
   - Main components

2. **Model_Factory.pu** (5 min)
   - Factory pattern for models
   - ModelFactoryRegistry
   - Foundation for other patterns

3. **Data_Layer.pu** (10 min)
   - Core entities (User, Activity, Booking, Ticket, Notification, Report, Feedback)
   - Memento pattern (ActivityMemento, BookingMemento)
   - Caretakers for history management
   - Database layer

4. **Event_Management.pu** (15 min) ⭐ COMPLEX
   - Activity hierarchy (Activity → EventEntity, Trip)
   - Builder pattern (ActivityBuilder, EventBuilder, TripBuilder, Director)
   - Prototype pattern (IPrototype for cloning)
   - State pattern (ActivityState, UpcomingState, CompletedState, CancelledState)
   - ActivityLifecycle context
   - ActivityCategory enum

5. **User_Management_.pu** (8 min)
   - User entity
   - UserRole enum
   - Authentication logic
   - Profile management

6. **Booking_Ticketing.pu** (12 min)
   - Booking and Ticket entities
   - Strategy pattern (IPricingStrategy, StandardPricing, EarlyBird, BulkDiscount)
   - Decorator pattern (ITicketService, BasicTicketService, decorators)
   - BookingStatus enum

7. **Notification.pu** (12 min)
   - Notification entity
   - Bridge pattern (NotificationMessage, channels)
   - Adapter pattern (IEmailService, SmtpEmailAdapter)
   - NotificationType enum

8. **Reports_Analytics.pu** (10 min)
   - Report entity
   - ReportType, ExportFormat enums
   - Analytics service
   - Report generation logic

9. **Repository_Layer.pu** (10 min)
   - All repository interfaces
   - Spring Data JPA
   - Custom query methods

10. **Controller.pu** (15 min) ⭐ COMPLEX
    - All REST controllers
    - Command pattern (IControllerCommand, concrete commands, Invoker)
    - Chain of Responsibility (RequestHandler chain)
    - HTTP endpoints

**Total Reading Time:** ~1.5 hours to understand fully

## Project Context & Requirements

### Business Domain Understanding

**What is this system?**
- University event and trip management platform
- Students can browse and book activities
- Organizers can create and manage events/trips
- Admins can oversee everything and generate reports

**Key User Journeys:**

1. **Student Journey:**
   - Register/Login → Browse activities → Book activity → Receive QR ticket → Attend event → Submit feedback

2. **Organizer Journey:**
   - Login → Create event/trip → Publish → Monitor bookings → Mark as completed → View feedback

3. **Admin Journey:**
   - Login → Manage users → Oversee all activities → Generate reports → Export analytics

### Technical Context

**Backend Technology Stack:**
```
Framework: Spring Boot 3.2.0
Language: Java 17+
Security: Spring Security + JWT
Database: Spring Data JPA + H2 (in-memory)
Build: Maven
Libraries:
  - ZXing (QR code generation)
  - Lombok (boilerplate reduction)
  - Validation API
  - Jackson (JSON)
```

**Frontend Technology Stack:**
```
Framework: Next.js 15 (App Router)
Language: TypeScript 5+
Styling: Tailwind CSS 3+
HTTP: Axios
State: React Context
Libraries:
  - qrcode.react (QR display)
  - @heroicons/react (icons)
  - date-fns (date utilities)
```

**Deployment:**
```
Containerization: Docker
Orchestration: docker-compose
Backend port: 8080
Frontend port: 3000
```

### Quality Expectations (Based on Historical Data)

From `report/05_vibe_coding_analysis.md`, we know:

**Scenario 2 (After DP) achieved:**
- Compilation Success: 100%
- Pattern Correctness: 95%
- Code Organization: 95%
- Documentation: 85%
- SOLID Adherence: 90%
- Overall Score: 8.4/10

**Your target: 8.7+/10**

To achieve this, ensure:
- ✅ 100% compilation (no errors)
- ✅ 97%+ pattern correctness (better than 95%)
- ✅ 98% code organization (better than 95%)
- ✅ 90% documentation (better than 85%)
- ✅ 95% SOLID adherence (better than 90%)

## Architectural Patterns to Implement

You must implement exactly **11 design patterns** as shown in the diagrams:

### Creational (3 patterns)

1. **Factory Pattern** (Model_Factory.pu)
   - Location: `backend/src/main/java/com/aiu/trips/factory/`
   - Classes: IModelFactory, UserFactory, ActivityFactory, BookingFactory, ModelFactoryRegistry
   - Purpose: Uniform model creation

2. **Builder Pattern** (Event_Management.pu)
   - Location: `backend/src/main/java/com/aiu/trips/builder/`
   - Classes: ActivityBuilder (abstract), EventBuilder, TripBuilder, ActivityDirector
   - Purpose: Step-by-step construction of complex Activity objects

3. **Prototype Pattern** (Event_Management.pu)
   - Location: `backend/src/main/java/com/aiu/trips/prototype/`
   - Classes: IPrototype interface, implemented by Activity
   - Purpose: Clone activities for similar events

### Structural (3 patterns)

4. **Adapter Pattern** (Notification.pu)
   - Location: `backend/src/main/java/com/aiu/trips/adapter/`
   - Classes: IEmailService, SmtpEmailAdapter
   - Purpose: Integrate external SMTP service

5. **Bridge Pattern** (Notification.pu)
   - Location: `backend/src/main/java/com/aiu/trips/bridge/`
   - Classes: NotificationMessage, EmailMessage, SmsMessage, PushMessage, INotificationChannel, EmailChannel, SmsChannel, PushChannel
   - Purpose: Decouple message abstraction from channel implementation

6. **Decorator Pattern** (Booking_Ticketing.pu)
   - Location: `backend/src/main/java/com/aiu/trips/decorator/`
   - Classes: ITicketService, BasicTicketService, TicketServiceDecorator, InsuranceDecorator, MealPlanDecorator
   - Purpose: Add features to tickets dynamically

### Behavioral (5 patterns)

7. **Command Pattern** (Controller.pu)
   - Location: `backend/src/main/java/com/aiu/trips/command/`
   - Classes: IControllerCommand, RegisterCommand, LoginCommand, CreateEventCommand, etc., ControllerCommandInvoker
   - Purpose: Encapsulate controller operations as objects

8. **Chain of Responsibility** (Controller.pu)
   - Location: `backend/src/main/java/com/aiu/trips/chain/`
   - Classes: IRequestHandler, AuthenticationHandler, AuthorizationHandler, ValidationHandler, RateLimitHandler
   - Purpose: Process requests through a chain of handlers

9. **State Pattern** (Event_Management.pu)
   - Location: `backend/src/main/java/com/aiu/trips/state/`
   - Classes: IActivityState, UpcomingState, CompletedState, CancelledState, ActivityLifecycle
   - Purpose: Manage activity lifecycle

10. **Strategy Pattern** (Booking_Ticketing.pu)
    - Location: `backend/src/main/java/com/aiu/trips/strategy/`
    - Classes: IPricingStrategy, StandardPricingStrategy, EarlyBirdPricingStrategy, BulkGroupDiscountStrategy
    - Purpose: Flexible pricing algorithms

11. **Memento Pattern** (Data_Layer.pu)
    - Location: `backend/src/main/java/com/aiu/trips/memento/`
    - Classes: ActivityMemento, BookingMemento, ActivityHistoryCaretaker, BookingHistoryCaretaker
    - Purpose: Save and restore object state (undo/history)

## Package Structure (Backend)

```java
com.aiu.trips/
├── TripsApplication.java                       # Main Spring Boot application
│
├── model/                                      # 📦 JPA Entities
│   ├── User.java                              # User entity
│   ├── Activity.java                          # Abstract base class
│   ├── EventEntity.java                       # Concrete event
│   ├── Trip.java                              # Concrete trip
│   ├── Booking.java                           # Booking entity
│   ├── Ticket.java                            # Ticket entity with QR
│   ├── Notification.java                      # Notification entity
│   ├── Report.java                            # Report entity
│   └── Feedback.java                          # Feedback entity
│
├── enums/                                      # 📦 Type-safe enumerations
│   ├── UserRole.java                          # STUDENT, ORGANIZER, ADMIN
│   ├── ActivityType.java                      # EVENT, TRIP
│   ├── ActivityStatus.java                    # DRAFT, UPCOMING, ONGOING, COMPLETED, CANCELLED
│   ├── ActivityCategory.java                  # SPORTS, CULTURAL, ACADEMIC, SOCIAL, ADVENTURE
│   ├── BookingStatus.java                     # PENDING, CONFIRMED, CANCELLED, REFUNDED
│   ├── NotificationType.java                  # EMAIL, SMS, PUSH
│   ├── ReportType.java                        # PARTICIPANT_COUNT, INCOME, FEEDBACK_ANALYSIS
│   ├── ExportFormat.java                      # PDF, CSV, EXCEL
│   └── TicketType.java                        # STANDARD, VIP, STUDENT_DISCOUNT
│
├── factory/                                    # 🎨 Factory Pattern
│   ├── IModelFactory.java
│   ├── UserFactory.java
│   ├── ActivityFactory.java
│   ├── BookingFactory.java
│   └── ModelFactoryRegistry.java
│
├── builder/                                    # 🏗️ Builder Pattern
│   ├── ActivityBuilder.java
│   ├── EventBuilder.java
│   ├── TripBuilder.java
│   └── ActivityDirector.java
│
├── prototype/                                  # 🧬 Prototype Pattern
│   └── IPrototype.java
│
├── adapter/                                    # 🔌 Adapter Pattern
│   ├── IEmailService.java
│   └── SmtpEmailAdapter.java
│
├── bridge/                                     # 🌉 Bridge Pattern
│   ├── NotificationMessage.java
│   ├── EmailMessage.java
│   ├── SmsMessage.java
│   ├── PushMessage.java
│   ├── INotificationChannel.java
│   ├── EmailChannel.java
│   ├── SmsChannel.java
│   └── PushChannel.java
│
├── decorator/                                  # 🎀 Decorator Pattern
│   ├── ITicketService.java
│   ├── BasicTicketService.java
│   ├── TicketServiceDecorator.java
│   ├── InsuranceDecorator.java
│   └── MealPlanDecorator.java
│
├── command/                                    # ⚡ Command Pattern
│   ├── IControllerCommand.java
│   ├── RegisterCommand.java
│   ├── LoginCommand.java
│   ├── CreateEventCommand.java
│   ├── UpdateEventCommand.java
│   ├── DeleteEventCommand.java
│   ├── BookEventCommand.java
│   ├── SendNotificationCommand.java
│   ├── GenerateReportCommand.java
│   └── ControllerCommandInvoker.java
│
├── chain/                                      # ⛓️ Chain of Responsibility
│   ├── IRequestHandler.java
│   ├── AuthenticationHandler.java
│   ├── AuthorizationHandler.java
│   ├── ValidationHandler.java
│   └── RateLimitHandler.java
│
├── state/                                      # 🔄 State Pattern
│   ├── IActivityState.java
│   ├── UpcomingState.java
│   ├── CompletedState.java
│   ├── CancelledState.java
│   └── ActivityLifecycle.java
│
├── strategy/                                   # 🎯 Strategy Pattern
│   ├── IPricingStrategy.java
│   ├── StandardPricingStrategy.java
│   ├── EarlyBirdPricingStrategy.java
│   └── BulkGroupDiscountStrategy.java
│
├── memento/                                    # 💾 Memento Pattern
│   ├── ActivityMemento.java
│   ├── BookingMemento.java
│   ├── ActivityHistoryCaretaker.java
│   └── BookingHistoryCaretaker.java
│
├── repository/                                 # 🗄️ Data Access Layer
│   ├── UserRepository.java
│   ├── ActivityRepository.java
│   ├── EventRepository.java
│   ├── TripRepository.java
│   ├── BookingRepository.java
│   ├── TicketRepository.java
│   ├── NotificationRepository.java
│   ├── ReportRepository.java
│   └── FeedbackRepository.java
│
├── service/                                    # 💼 Business Logic Layer
│   ├── UserService.java
│   ├── ActivityService.java
│   ├── BookingService.java
│   ├── TicketService.java
│   ├── NotificationService.java
│   ├── ReportService.java
│   └── FeedbackService.java
│
├── controller/                                 # 🌐 REST API Layer
│   ├── UserController.java
│   ├── ActivityController.java
│   ├── BookingController.java
│   ├── TicketController.java
│   ├── NotificationController.java
│   ├── ReportController.java
│   └── FeedbackController.java
│
├── dto/                                        # 📋 Data Transfer Objects
│   ├── UserDTO.java
│   ├── LoginRequest.java
│   ├── RegisterRequest.java
│   ├── ActivityDTO.java
│   ├── CreateActivityRequest.java
│   ├── UpdateActivityRequest.java
│   ├── BookingDTO.java
│   ├── CreateBookingRequest.java
│   ├── NotificationDTO.java
│   ├── ReportDTO.java
│   ├── GenerateReportRequest.java
│   ├── FeedbackDTO.java
│   └── SubmitFeedbackRequest.java
│
├── exception/                                  # ⚠️ Exception Handling
│   ├── GlobalExceptionHandler.java
│   ├── UserNotFoundException.java
│   ├── ActivityNotFoundException.java
│   ├── BookingException.java
│   ├── UnauthorizedException.java
│   └── ValidationException.java
│
└── config/                                     # ⚙️ Configuration
    ├── SecurityConfig.java                    # Spring Security + JWT
    ├── JwtUtil.java                           # JWT token utilities
    ├── JwtAuthenticationFilter.java           # JWT filter
    └── CorsConfig.java                        # CORS configuration
```

## Component Structure (Frontend)

```typescript
frontend/src/
├── app/                                        # Next.js App Router
│   ├── page.tsx                               # Home page
│   ├── layout.tsx                             # Root layout
│   ├── login/page.tsx                         # Login page
│   ├── register/page.tsx                      # Register page
│   ├── activities/
│   │   ├── page.tsx                           # Activity list
│   │   ├── [id]/page.tsx                      # Activity detail
│   │   └── create/page.tsx                    # Create activity
│   ├── bookings/
│   │   ├── page.tsx                           # My bookings
│   │   └── [id]/page.tsx                      # Booking detail
│   ├── profile/page.tsx                       # User profile
│   ├── admin/page.tsx                         # Admin dashboard
│   └── reports/page.tsx                       # Reports
│
├── components/
│   ├── auth/                                  # Auth components
│   │   ├── LoginForm.tsx
│   │   ├── RegisterForm.tsx
│   │   └── ProfileCard.tsx
│   ├── activities/                            # Activity components
│   │   ├── ActivityCard.tsx
│   │   ├── ActivityList.tsx
│   │   ├── ActivityDetail.tsx
│   │   ├── ActivityForm.tsx
│   │   ├── EventForm.tsx
│   │   └── TripForm.tsx
│   ├── bookings/                              # Booking components
│   │   ├── BookingForm.tsx
│   │   ├── BookingCard.tsx
│   │   ├── BookingList.tsx
│   │   └── TicketDisplay.tsx (with QR)
│   ├── notifications/                         # Notification components
│   │   ├── NotificationCenter.tsx
│   │   └── NotificationItem.tsx
│   ├── reports/                               # Report components
│   │   ├── ReportDashboard.tsx
│   │   ├── ReportChart.tsx
│   │   └── ReportExport.tsx
│   ├── feedback/                              # Feedback components
│   │   ├── FeedbackForm.tsx
│   │   └── FeedbackList.tsx
│   └── common/                                # Reusable components
│       ├── Header.tsx
│       ├── Footer.tsx
│       ├── Navigation.tsx
│       ├── Button.tsx
│       ├── Input.tsx
│       ├── Card.tsx
│       ├── Modal.tsx
│       ├── Loading.tsx
│       └── ErrorMessage.tsx
│
├── services/
│   ├── api/
│   │   ├── axiosConfig.ts                     # Axios instance with interceptors
│   │   ├── authService.ts                     # Auth API calls
│   │   ├── activityService.ts                 # Activity API calls
│   │   ├── bookingService.ts                  # Booking API calls
│   │   ├── notificationService.ts             # Notification API calls
│   │   ├── reportService.ts                   # Report API calls
│   │   └── feedbackService.ts                 # Feedback API calls
│   └── storage/
│       └── localStorage.ts                    # Local storage utilities
│
├── contexts/
│   ├── AuthContext.tsx                        # Authentication context
│   └── ThemeContext.tsx                       # Theme context
│
├── hooks/
│   ├── useAuth.ts                             # Auth hook
│   ├── useActivities.ts                       # Activities data hook
│   └── useBookings.ts                         # Bookings data hook
│
├── types/
│   ├── User.ts                                # User type
│   ├── Activity.ts                            # Activity type
│   ├── Booking.ts                             # Booking type
│   ├── Ticket.ts                              # Ticket type
│   ├── Notification.ts                        # Notification type
│   ├── Report.ts                              # Report type
│   └── Feedback.ts                            # Feedback type
│
└── utils/
    ├── formatDate.ts                          # Date formatting
    ├── formatCurrency.ts                      # Currency formatting
    └── validators.ts                          # Form validators
```

## Execution Instructions

### Step 1: Read All Diagrams (30 min)
Parse all `.pu` files in `vibe_tests/data/` in the order specified above.

### Step 2: Generate Backend (3 hours)
Create complete Spring Boot application in `Project/backend/`

### Step 3: Generate Frontend (2 hours)
Create complete Next.js application in `Project/frontend/`

### Step 4: DevOps Configuration (30 min)
Create Docker and orchestration files

### Step 5: Verification (30 min)
Compile, run, and test the complete system

**Total Time:** ~6 hours
**Target Location:** `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/Project/`
**Target Quality:** 8.7+/10

---

**Now, with full context awareness of the repository, diagrams, and quality expectations, generate the complete production-ready AIU Trips & Events Management System!**
