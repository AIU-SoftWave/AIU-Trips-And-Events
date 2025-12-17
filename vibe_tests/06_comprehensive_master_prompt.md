# 🎯 Master Prompt: Complete AIU Trips & Events System Generation

**Mission:** Generate a production-ready university event and trip management system from PlantUML architectural diagrams.

---

## 🎯 Output Directory

**Generate all code in:** `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_tests/06_comprehensive/`

This directory will contain your complete generated project using the Comprehensive Master framework.

---

## 📍 Repository Context

**Working Directory:** `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/`

**Source Diagrams:** `vibe_tests/data/*.pu` (PlantUML - After Design Patterns)

**Target Directory:** `vibe_tests/06_comprehensive/` (backend + frontend)

---

## 🎓 What You're Building

### System Overview
A comprehensive university platform where:
- **Students** can browse, book, and attend events/trips, then provide feedback
- **Organizers** can create and manage activities, monitor bookings
- **Admins** can oversee everything and generate analytics reports

### Core Features
1. 🔐 Authentication (JWT-based)
2. 📅 Activity Management (Events & Trips)
3. 🎫 Booking System (with QR codes)
4. 💰 Multi-strategy Pricing
5. 🔔 Multi-channel Notifications
6. 📊 Analytics & Reports
7. ⭐ Feedback System

---

## 📐 Architectural Foundation

### Design Patterns (11 Total)

You must implement exactly these patterns as shown in the diagrams:

**Creational (3):**
1. ✅ **Factory** - Uniform model creation
2. ✅ **Builder** - Step-by-step Activity construction
3. ✅ **Prototype** - Clone activities

**Structural (3):**
4. ✅ **Adapter** - Integrate external email service
5. ✅ **Bridge** - Decouple notifications from channels
6. ✅ **Decorator** - Add features to tickets

**Behavioral (5):**
7. ✅ **Command** - Encapsulate controller operations
8. ✅ **Chain of Responsibility** - Request processing pipeline
9. ✅ **State** - Activity lifecycle management
10. ✅ **Strategy** - Flexible pricing algorithms
11. ✅ **Memento** - State history and undo

---

## 📚 Your Source Material

### Diagram Reading Order

Read these PlantUML files from `vibe_tests/data/`:

| # | File | Time | Focus |
|---|------|------|-------|
| 1 | University_Trips_Events_Management_System.pu | 5m | System overview |
| 2 | Model_Factory.pu | 5m | Factory pattern |
| 3 | Data_Layer.pu | 10m | Core entities + Memento |
| 4 | Event_Management.pu | 15m | ⭐ Builder, Prototype, State |
| 5 | User_Management_.pu | 8m | Authentication |
| 6 | Booking_Ticketing.pu | 12m | Strategy, Decorator |
| 7 | Notification.pu | 12m | Bridge, Adapter |
| 8 | Reports_Analytics.pu | 10m | Reporting |
| 9 | Repository_Layer.pu | 10m | Data access |
| 10 | Controller.pu | 15m | ⭐ Command, Chain |

**Total:** ~100 minutes to understand fully

---

## 🔧 Technology Stack

### Backend (Spring Boot)
```yaml
Framework: Spring Boot 3.2.0
Language: Java 17+
Security: Spring Security + JWT
Database: Spring Data JPA + H2 (in-memory)
Build: Maven
Libraries:
  - ZXing: QR code generation
  - Lombok: Boilerplate reduction
  - Validation API
  - Jackson: JSON serialization
```

### Frontend (Next.js)
```yaml
Framework: Next.js 15 (App Router)
Language: TypeScript 5+
Styling: Tailwind CSS 3+
HTTP: Axios
State: React Context
Libraries:
  - qrcode.react: QR code display
  - @heroicons/react: Icons
  - date-fns: Date utilities
```

### DevOps
```yaml
Container: Docker (multi-stage builds)
Orchestration: docker-compose
Backend Port: 8080
Frontend Port: 3000
```

---

## 🏗️ Backend Architecture

### Package Structure

```
com.aiu.trips/
├── TripsApplication.java        # Main app

├── model/                        # 9 JPA entities
│   ├── User.java
│   ├── Activity.java (abstract)
│   ├── EventEntity.java
│   ├── Trip.java
│   ├── Booking.java
│   ├── Ticket.java
│   ├── Notification.java
│   ├── Report.java
│   └── Feedback.java

├── enums/                        # 9 enumerations
│   ├── UserRole
│   ├── ActivityType
│   ├── ActivityStatus
│   ├── ActivityCategory
│   ├── BookingStatus
│   ├── NotificationType
│   ├── ReportType
│   ├── ExportFormat
│   └── TicketType

├── factory/                      # Factory pattern (5 files)
├── builder/                      # Builder pattern (4 files)
├── prototype/                    # Prototype pattern (1 file)
├── adapter/                      # Adapter pattern (2 files)
├── bridge/                       # Bridge pattern (7 files)
├── decorator/                    # Decorator pattern (5 files)
├── command/                      # Command pattern (10+ files)
├── chain/                        # Chain pattern (5 files)
├── state/                        # State pattern (5 files)
├── strategy/                     # Strategy pattern (4 files)
├── memento/                      # Memento pattern (4 files)

├── repository/                   # 9 repositories (Spring Data JPA)
├── service/                      # 7 services (business logic)
├── controller/                   # 7 controllers (REST API)
├── dto/                          # ~15 DTOs
├── exception/                    # Exception handling
└── config/                       # Security, JWT, CORS
```

### Key Components

**Entities (9 classes):**
```java
User → id, username, email, password, role, createdAt, updatedAt
Activity (abstract) → id, title, description, category, status, dates, location, capacity, price
├── EventEntity → speaker, agenda, eventType
└── Trip → destination, transportation, accommodation
Booking → id, user, activity, bookingDate, status, totalPrice
Ticket → id, booking, qrCode, validationStatus
Notification → id, user, type, message, sentAt, isRead
Report → id, type, title, content, generatedBy, generatedAt
Feedback → id, user, activity, rating, comment, submittedAt
```

**REST Endpoints:**
```
User: POST /api/users/register, POST /api/users/login, GET/PUT /api/users/profile
Activity: GET/POST /api/activities, GET/PUT/DELETE /api/activities/{id}
Booking: GET/POST /api/bookings, GET/DELETE /api/bookings/{id}
Ticket: GET /api/tickets/{bookingId}, POST /api/tickets/{qrCode}/validate
Notification: GET /api/notifications, PATCH /api/notifications/{id}/read
Report: POST /api/reports/generate, GET /api/reports, GET /api/reports/{id}/export
Feedback: POST /api/feedback, GET /api/activities/{id}/feedback
```

---

## 💻 Frontend Architecture

### Component Hierarchy

```
src/
├── app/                          # Next.js App Router (11 pages)
│   ├── page.tsx                 # Home
│   ├── login/page.tsx
│   ├── register/page.tsx
│   ├── activities/              # List, detail, create
│   ├── bookings/                # My bookings, detail
│   ├── profile/page.tsx
│   ├── admin/page.tsx
│   └── reports/page.tsx

├── components/                   # ~40 components
│   ├── auth/                    # Login, Register, Profile
│   ├── activities/              # Card, List, Detail, Form
│   ├── bookings/                # Form, Card, Ticket (QR)
│   ├── notifications/           # Center, Item
│   ├── reports/                 # Dashboard, Chart, Export
│   ├── feedback/                # Form, List
│   └── common/                  # Reusable UI (Header, Button, etc.)

├── services/api/                 # 7 API service files
│   ├── axiosConfig.ts           # Axios with JWT interceptor
│   ├── authService.ts
│   ├── activityService.ts
│   ├── bookingService.ts
│   ├── notificationService.ts
│   ├── reportService.ts
│   └── feedbackService.ts

├── contexts/                     # 2 context providers
│   ├── AuthContext.tsx          # Auth state
│   └── ThemeContext.tsx         # Theme preferences

├── hooks/                        # 3 custom hooks
│   ├── useAuth.ts
│   ├── useActivities.ts
│   └── useBookings.ts

├── types/                        # 7 TypeScript type files
└── utils/                        # Helper functions
```

---

## 📋 Implementation Checklist

### Phase 1: Backend Core (90 min)
- [ ] Create Maven project structure
- [ ] Configure pom.xml with all dependencies
- [ ] Create 9 JPA entities with proper annotations
- [ ] Create 9 enums
- [ ] Configure application.properties (H2, JWT, server)

### Phase 2: Design Patterns (60 min)
- [ ] Factory pattern (5 files)
- [ ] Builder pattern (4 files)
- [ ] Prototype pattern (1 file)
- [ ] Adapter pattern (2 files)
- [ ] Bridge pattern (7 files)
- [ ] Decorator pattern (5 files)
- [ ] Command pattern (10+ files)
- [ ] Chain of Responsibility (5 files)
- [ ] State pattern (5 files)
- [ ] Strategy pattern (4 files)
- [ ] Memento pattern (4 files)

### Phase 3: Backend Logic (60 min)
- [ ] Create 9 repositories (Spring Data JPA)
- [ ] Create 7 service interfaces and implementations
- [ ] Create 7 REST controllers with endpoints
- [ ] Create ~15 DTOs
- [ ] Implement exception handling

### Phase 4: Security (30 min)
- [ ] JWT utility class (generate, validate, extract claims)
- [ ] JWT authentication filter
- [ ] Spring Security configuration
- [ ] CORS configuration
- [ ] Password encoding

### Phase 5: Frontend Setup (20 min)
- [ ] Initialize Next.js with TypeScript
- [ ] Configure Tailwind CSS
- [ ] Setup package.json with dependencies
- [ ] Create type definitions (7 files)

### Phase 6: Frontend Core (60 min)
- [ ] Create Axios configuration with JWT interceptor
- [ ] Create 7 API service files
- [ ] Create AuthContext and ThemeContext
- [ ] Create 3 custom hooks

### Phase 7: Frontend UI (60 min)
- [ ] Create 11 pages (App Router)
- [ ] Create ~40 components (auth, activities, bookings, etc.)
- [ ] Create common reusable components
- [ ] Implement responsive Tailwind styling

### Phase 8: DevOps (20 min)
- [ ] Backend Dockerfile (multi-stage)
- [ ] Frontend Dockerfile (multi-stage)
- [ ] docker-compose.yml
- [ ] .env.example
- [ ] start.sh script

### Phase 9: Testing (20 min)
- [ ] Backend compilation (mvn clean install)
- [ ] Backend run (mvn spring-boot:run)
- [ ] Frontend build (npm run build)
- [ ] Frontend run (npm run dev)
- [ ] Integration testing (auth, CRUD, QR codes)

**Total Time:** ~7 hours
**Expected Files:** ~137 backend files + ~40 frontend components

---

## 🎯 Quality Standards

### Target Metrics (Aim for 8.7+/10)

| Metric | Target | How to Achieve |
|--------|--------|----------------|
| **Compilation Success** | 100% | Test compile after each major section |
| **Pattern Correctness** | 97%+ | Follow diagrams exactly, verify each pattern |
| **Code Organization** | 98% | Use proper package structure, clear naming |
| **Documentation** | 90% | Javadoc for public APIs, comments for complex logic |
| **SOLID Principles** | 95% | Single responsibility, dependency injection |
| **Functionality** | 95% | Test all endpoints, user flows |
| **UI/UX** | 85% | Responsive, loading states, error handling |

### Code Quality Checklist

**Backend:**
- ✅ All classes from diagrams exist
- ✅ All 11 patterns correctly implemented
- ✅ JPA annotations on entities
- ✅ @Service, @Repository, @RestController annotations
- ✅ JWT authentication functional
- ✅ QR code generation works
- ✅ Exception handling comprehensive
- ✅ Input validation on all endpoints

**Frontend:**
- ✅ TypeScript types match backend DTOs
- ✅ All API calls use Axios service layer
- ✅ Loading and error states on all components
- ✅ Form validation with user feedback
- ✅ Responsive design (mobile + desktop)
- ✅ JWT stored securely (httpOnly cookies or secure storage)
- ✅ QR code display functional
- ✅ Navigation intuitive

---

## 🚀 Execution Steps

### Step 1: Preparation (20 min)
```bash
# Read all diagram files
cd /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_tests/data
ls *.pu
# Read each file, extract classes, relationships, patterns
```

### Step 2: Backend Generation (3.5 hours)
```bash
# Create project structure
cd /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/Project/backend

# Generate code following package structure
# Test compilation frequently
mvn clean compile
```

### Step 3: Frontend Generation (2 hours)
```bash
# Create Next.js project
cd /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/Project/frontend

# Generate components and pages
# Test build
npm run build
```

### Step 4: Docker Setup (20 min)
```bash
# Create Dockerfiles and docker-compose.yml
cd /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/Project

# Test containers
docker-compose up --build
```

### Step 5: Verification (30 min)
```bash
# Start backend
cd backend && mvn spring-boot:run

# Start frontend (separate terminal)
cd frontend && npm run dev

# Test user flows:
# 1. Register → Login → Browse → Book → View Ticket
# 2. Organizer creates event
# 3. Admin generates report
```

---

## 📊 Expected Outcomes

### Backend Statistics
- **Total Files:** ~137
- **Lines of Code:** ~10,000
- **Patterns:** 11/11 implemented
- **Endpoints:** ~25 REST endpoints
- **Compilation:** 100% success

### Frontend Statistics
- **Total Components:** ~40
- **Pages:** 11 (App Router)
- **Lines of Code:** ~6,000
- **API Services:** 7 service files
- **Build:** Success

### Quality Score
- **Target:** 8.7/10
- **Backend:** 9.0/10
- **Frontend:** 8.4/10
- **Overall:** 8.7/10

---

## 💡 Pro Tips

1. **Read Diagrams First** - Spend 1.5 hours understanding architecture before coding
2. **Start with Entities** - Foundation is critical
3. **Test Frequently** - Compile after each package
4. **Follow Patterns Exactly** - Don't improvise pattern implementations
5. **Use Lombok** - Reduce boilerplate (but ensure proper annotations)
6. **Type Safety** - TypeScript catches errors early
7. **Error Handling** - User-friendly messages, not stack traces
8. **Security First** - Validate inputs, protect endpoints
9. **Document as You Go** - Javadoc and comments
10. **Verify Incrementally** - Test each feature as completed

---

## 🎬 Final Command

**⚠️ IMPORTANT - Output Location:**

Generate ALL code in: `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_tests/06_comprehensive/`

Create this structure:
```
vibe_tests/06_comprehensive/
├── backend/          # Spring Boot application
├── frontend/         # Next.js application
├── docker-compose.yml
└── README.md         # Comprehensive generation notes
```

**Using diagrams from:**

`/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_tests/data/*.pu`

**Target quality: 8.7+/10**

**Expected time: 6-7 hours** *(Note: This is estimated AI processing time and may vary significantly based on model capability, complexity, and iteration needs)*

---

## ✅ Success Criteria

You have succeeded when:
- [x] All 137 backend files generated and compile successfully
- [x] All 40 frontend components created and build successfully
- [x] All 11 design patterns correctly implemented
- [x] JWT authentication works (register, login, protected routes)
- [x] CRUD operations functional (create event, book, view ticket)
- [x] QR codes generate and display
- [x] Responsive UI works on mobile and desktop
- [x] Docker containers build and run
- [x] Quality score ≥ 8.7/10

---

**BEGIN IMPLEMENTATION NOW! 🚀**
