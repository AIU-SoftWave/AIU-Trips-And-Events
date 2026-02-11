# Chain-of-Thought Prompt: Complete AIU Trips & Events System Generation

## 🎯 Output Directory

**Generate all code in:** `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_tests/01_chain_of_thought/`

This directory will contain your complete generated project using the Chain-of-Thought framework.

## Context & Repository Information

You are working in the repository located at: `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/`

The repository structure is:
```
AIU-Trips-And-Events/
├── Project/              # Main application (target for code generation)
├── Milestones/          # Project milestones and documentation
├── report/              # Analysis reports
├── vibe_tests/          # This folder (AI generation prompts and data)
│   └── data/            # PlantUML diagrams (After DP)
└── README.md            # Project documentation
```

## Your Mission

Generate a **complete, production-ready** AIU Trips & Events Management System by following the PlantUML diagrams in `vibe_tests/data/` directory. These diagrams represent the system **after design patterns** have been applied.

## Step 1: Understand the System Architecture

**Think through these questions:**

1. What is the core purpose of this system?
   - University event and trip management
   - Complete activity lifecycle (creation → booking → completion → analytics)

2. What are the main entities?
   - Users (students, organizers, admins)
   - Activities (abstract) → Events & Trips (concrete)
   - Bookings & Tickets
   - Notifications
   - Reports & Feedback

3. What design patterns are being used and why?
   - **Factory**: Create different types of models uniformly
   - **Builder**: Construct complex Activity objects step-by-step
   - **Prototype**: Clone activities for similar events
   - **Command**: Encapsulate controller operations
   - **Chain of Responsibility**: Handle requests through authentication → authorization → validation
   - **State**: Manage activity lifecycle (Upcoming/Completed/Cancelled)
   - **Strategy**: Apply different pricing strategies (Standard/EarlyBird/BulkDiscount)
   - **Decorator**: Add features to tickets (Insurance/MealPlan/etc.)
   - **Bridge**: Decouple notification messages from delivery channels
   - **Adapter**: Integrate external email service
   - **Memento**: Save/restore activity and booking states for history

## Step 2: Analyze the PlantUML Diagrams

**Process each diagram systematically:**

For each `.pu` file in `vibe_tests/data/`:

1. **Read the diagram** - Parse all classes, interfaces, relationships
2. **Identify patterns** - Note which design pattern(s) are used
3. **Understand relationships** - Inheritance, composition, associations
4. **List dependencies** - What does each class need?
5. **Plan implementation order** - Core entities first, then patterns

**Start with these diagrams in order:**
1. University_Trips_Events_Management_System.pu (overview)
2. Model_Factory.pu (foundation - creational)
3. Data_Layer.pu (entities and memento)
4. Event_Management.pu (core business logic with multiple patterns)
5. User_Management_.pu (authentication)
6. Booking_Ticketing.pu (booking with strategy and decorator)
7. Notification.pu (bridge and adapter)
8. Reports_Analytics.pu (reporting)
9. Repository_Layer.pu (data access)
10. Controller.pu (command and chain)

## Step 3: Generate the Backend (Spring Boot)

**Think about package organization:**

```
com.aiu.trips/
├── model/           # Core entities (User, Activity, Event, Trip, Booking, Ticket, etc.)
├── enums/           # Type-safe enumerations (ActivityType, BookingStatus, etc.)
├── factory/         # Factory pattern implementation
├── builder/         # Builder pattern for Activities
├── prototype/       # Prototype pattern interface
├── state/           # State pattern for activity lifecycle
├── strategy/        # Strategy pattern for pricing
├── decorator/       # Decorator pattern for tickets
├── bridge/          # Bridge pattern for notifications
├── adapter/         # Adapter pattern for email
├── memento/         # Memento pattern for state history
├── command/         # Command pattern for controllers
├── chain/           # Chain of Responsibility for request handling
├── repository/      # Spring Data JPA repositories
├── service/         # Business logic layer
├── controller/      # REST API endpoints
├── dto/             # Data Transfer Objects
├── exception/       # Custom exception classes
└── config/          # Spring configuration (Security, CORS, etc.)
```

**For each package, think:**
- What classes are needed based on the diagrams?
- What are their relationships?
- What methods do they need?
- What annotations are required (Spring Boot)?

**Implementation checklist:**
1. ✅ Create all entity classes with JPA annotations
2. ✅ Implement all 11 design patterns as per diagrams
3. ✅ Create repositories extending JpaRepository
4. ✅ Implement service layer with business logic
5. ✅ Build REST controllers with proper endpoints
6. ✅ Add DTOs for data transfer
7. ✅ Implement JWT authentication with Spring Security
8. ✅ Add QR code generation for tickets (ZXing)
9. ✅ Configure H2 in-memory database
10. ✅ Create application.properties with proper settings
11. ✅ Write pom.xml with all required dependencies

## Step 4: Generate the Frontend (Next.js + TypeScript)

**Think about component structure:**

```
src/
├── components/
│   ├── auth/          # Login, Register, Profile
│   ├── activities/    # Activity list, detail, create (unified for events/trips)
│   ├── bookings/      # Booking form, my bookings, booking detail
│   ├── notifications/ # Notification center
│   ├── reports/       # Analytics dashboard
│   ├── feedback/      # Feedback forms and display
│   ├── admin/         # Admin panel
│   └── common/        # Reusable components (Header, Footer, Navigation)
├── services/
│   ├── api/           # Axios API calls to backend
│   ├── auth/          # Authentication service
│   └── storage/       # Local storage utilities
├── contexts/          # React Context for global state
├── hooks/             # Custom React hooks
├── types/             # TypeScript type definitions
└── utils/             # Helper functions
```

**For each component, think:**
- What data does it need?
- What user actions does it handle?
- How does it interact with the backend API?
- What state does it manage?

**Implementation checklist:**
1. ✅ Create all page components with Next.js routing
2. ✅ Implement authentication flow with JWT storage
3. ✅ Build API service layer with Axios
4. ✅ Create reusable UI components
5. ✅ Implement form validation
6. ✅ Add loading states and error handling
7. ✅ Style with Tailwind CSS (responsive design)
8. ✅ Integrate QR code display for tickets
9. ✅ Add Context for auth and theme
10. ✅ Configure Next.js settings and environment variables
11. ✅ Create package.json with dependencies

## Step 5: Docker and Orchestration

**Think about deployment:**

Create these files in `Project/` directory:
1. **backend/Dockerfile** - Multi-stage build for Java
2. **frontend/Dockerfile** - Multi-stage build for Next.js
3. **docker-compose.yml** - Orchestrate both services
4. **.env.example** - Environment variable template
5. **start.sh** - Convenience script to start everything

## Step 6: Verify Implementation

**Final checklist - Think through:**

1. Do all classes from the diagrams exist? ✅
2. Are all design patterns correctly implemented? ✅
3. Does the code compile without errors? ✅
4. Are all REST endpoints functional? ✅
5. Does authentication work? ✅
6. Can users create and book activities? ✅
7. Do notifications send properly? ✅
8. Are reports generated correctly? ✅
9. Is the code well-organized and documented? ✅
10. Is it production-ready? ✅

## Step 7: Quality Standards

**Ensure:**
- ✅ 100% compilation success
- ✅ All 11 design patterns implemented correctly
- ✅ Proper exception handling
- ✅ Input validation on all forms and APIs
- ✅ JWT authentication and authorization
- ✅ RESTful API design
- ✅ Clean code with proper naming
- ✅ Appropriate comments for complex logic
- ✅ Type safety (TypeScript in frontend)
- ✅ Responsive UI design

## Expected Outcome

After following this chain of thought:

**Backend:**
- ~137 Java files
- All patterns from diagrams implemented
- 100% compilation success
- RESTful API with JWT security
- QR code generation
- H2 database configured

**Frontend:**
- ~40 React components
- TypeScript for type safety
- Tailwind CSS styling
- Responsive design
- Authentication flow
- QR code display

**Quality Score Target:** 8.5+/10

## Execution Instructions

1. Start by reading ALL PlantUML files in `vibe_tests/data/`
2. Create the complete backend first (01_chain_of_thought/backend/)
3. Then create the complete frontend (01_chain_of_thought/frontend/)
4. Add Docker configuration
5. Test the complete system
6. Verify against the diagrams

**⚠️ IMPORTANT - Output Location:**

Generate ALL code in: `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_tests/01_chain_of_thought/`

Create this structure:
```
vibe_tests/01_chain_of_thought/
├── backend/          # Spring Boot application
├── frontend/         # Next.js application
├── docker-compose.yml
└── README.md         # Generation notes
```

---

**Now, use this chain of thought to generate the complete project. Start with Step 1 and work through each step systematically.**
