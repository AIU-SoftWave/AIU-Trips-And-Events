# AI Code Generation Test Prompts

> **Important:** Before using any prompt, read the system context from `vibe_prompts/data/system_context.md`

## How to Use These Prompts

1. **Read the context:** First, read `vibe_prompts/data/system_context.md` to understand the system
2. **Copy the prompt:** Copy the entire prompt for the test you want to run
3. **Paste to AI:** Paste into your AI platform (GPT-4, Claude, Gemini, etc.)
4. **Save output:** Save generated code to `vibe_prompts/outputs/PROMPT_ID/`
5. **Fill report:** Complete the report template in `vibe_prompts/reports/PROMPT_ID_report.md`

## Prompt Index

### Zero-Shot Prompts (Direct Instructions)
- **P001** - Observer Pattern for Notifications
- **P002** - React Trip Detail Component
- **P003** - JPA Repository Queries

### Few-Shot Prompts (With Examples)
- **P004** - Service Layer Implementation
- **P005** - REST Controllers

### Chain-of-Thought Prompts (Step-by-Step)
- **P006** - Strategy Pattern for Pricing
- **P007** - Notification System Design

### Scenario Prompts (Complete Features)
- **P008** - Complete Backend from Diagrams
- **P009** - Complete Frontend Application
- **P010** - Waiting List Feature
- **P011** - Review and Rating System

### Refactoring Prompts
- **P012** - Add Strategy Pattern
- **P013** - Refactor to Builder Pattern

### Benchmarking Prompts
- **P014** - Speed Test (User Management Module)
- **P015** - Quality Test (Command Pattern)

---

## Zero-Shot Prompts

### P001 - Observer Pattern for Notifications

**📋 Copy this entire prompt:**

```
I'm working on the AIU Trips & Events Management System (Java Spring Boot backend).

Task: Implement the Observer Pattern for the notification system.

Requirements:
1. Create Subject interface for notification publishers
2. Create Observer interface for notification subscribers  
3. Implement concrete observers: EmailObserver, SMSObserver, PushObserver
4. Ensure proper subscribe/unsubscribe mechanisms
5. Implement notification broadcasting to all observers

Technical Details:
- Package: com.aiu.trips.observer
- Use Spring @Component annotations
- Include proper error handling
- Add logging

Generate complete Java code with:
- All interfaces and classes
- Proper package structure
- JavaDoc comments
- Usage example

Output all files to: vibe_prompts/outputs/P001/
```

**📝 Chaining:** No  
**⏱️ Estimated Time:** 5-10 minutes  
**📊 Report Template:** `vibe_prompts/reports/P001_report.md`

---

### P002 - React Trip Detail Component

**📋 Copy this entire prompt:**

```
I'm working on the AIU Trips & Events Management System (React.js frontend).

Task: Create a React component for displaying trip details.

Requirements:
1. Display: name, description, dates, location, price
2. Show current booking count and available seats
3. Include booking button with validation
4. Handle loading and error states
5. Use functional components with hooks

Technical Details:
- Component name: TripDetail
- Use axios for API calls
- Implement responsive design
- Add PropTypes validation

Generate complete React code with:
- Component file (TripDetail.jsx)
- CSS module (TripDetail.module.css)
- API service integration
- Usage example

Output all files to: vibe_prompts/outputs/P002/
```

**📝 Chaining:** No  
**⏱️ Estimated Time:** 5-10 minutes  
**📊 Report Template:** `vibe_prompts/reports/P002_report.md`

---

### P003 - JPA Repository Queries

**📋 Copy this entire prompt:**

```
I'm working on the AIU Trips & Events Management System (Java Spring Boot backend).

Task: Generate Spring Data JPA repository methods for the booking system.

Requirements:
1. Find all bookings for a specific user
2. Find all bookings for a specific activity
3. Find bookings by status and date range
4. Count total bookings for an activity
5. Get booking statistics grouped by month

Technical Details:
- Interface: BookingRepository
- Extend JpaRepository
- Use @Query annotations where needed
- Include proper method naming

Generate complete code with:
- Repository interface
- Custom query methods
- Method documentation
- Usage examples

Output all files to: vibe_prompts/outputs/P003/
```

**📝 Chaining:** No  
**⏱️ Estimated Time:** 5-10 minutes  
**📊 Report Template:** `vibe_prompts/reports/P003_report.md`

---

## Few-Shot Prompts

### P004 - Service Layer Implementation

**📋 Copy this entire prompt:**

```
I'm working on the AIU Trips & Events Management System (Java Spring Boot backend).

Task: Create service classes following these examples.

Example 1 - UserService:
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public User createUser(UserDTO dto) {
        User user = UserMapper.toEntity(dto);
        return userRepository.save(user);
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}

Example 2 - EventService:
@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    
    public Event createEvent(EventDTO dto) {
        Event event = EventMapper.toEntity(dto);
        return eventRepository.save(event);
    }
    
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}

Now create these services following the same pattern:
1. BookingService with CRUD operations (create, read, update, delete, list)
2. NotificationService with send capabilities
3. ReportService with generation methods

Include:
- Proper error handling
- Transaction management (@Transactional)
- Input validation
- Logging

Output all files to: vibe_prompts/outputs/P004/
```

**📝 Chaining:** No  
**⏱️ Estimated Time:** 10-15 minutes  
**📊 Report Template:** `vibe_prompts/reports/P004_report.md`

---

### P005 - REST Controllers

**📋 Copy this entire prompt:**

```
I'm working on the AIU Trips & Events Management System (Java Spring Boot backend).

Task: Create REST controllers following these examples.

Example 1 - UserController:
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO dto) {
        User user = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}

Now create these controllers:
1. TripController with full CRUD operations
2. BookingController with booking workflow endpoints
3. FeedbackController with review management

Include:
- Proper HTTP methods (GET, POST, PUT, DELETE)
- Validation (@Valid)
- Error handling
- Appropriate status codes

Output all files to: vibe_prompts/outputs/P005/
```

**📝 Chaining:** No  
**⏱️ Estimated Time:** 10-15 minutes  
**📊 Report Template:** `vibe_prompts/reports/P005_report.md`

---

## Chain-of-Thought Prompts

### P006 - Strategy Pattern for Pricing

**📋 Copy this entire prompt:**

```
I'm working on the AIU Trips & Events Management System (Java Spring Boot backend).

Task: Implement the Strategy Pattern for pricing. Think through this step by step.

Step 1: Identify the pricing variations
Consider: Early bird (20% off >30 days), Student discount (15% off), Group discount (10% off for 5+), Seasonal pricing, Default pricing

Step 2: Design the strategy interface
What methods should PricingStrategy have? What parameters for price calculation?

Step 3: Plan concrete implementations
How would each strategy calculate differently? What data does each need?

Step 4: Design the context class
How will Activity/Booking use the strategy? How to switch strategies dynamically?

Step 5: Implement integration
How does this integrate with the existing booking system?

Now generate:
1. PricingStrategy interface
2. All concrete strategy implementations (5 strategies)
3. Context class integration
4. Usage examples in BookingService

Show your reasoning for each decision and provide complete code.

Technical Details:
- Package: com.aiu.trips.strategy
- Use BigDecimal for prices
- Include validation
- Add unit tests

Output all files to: vibe_prompts/outputs/P006/
```

**📝 Chaining:** No  
**⏱️ Estimated Time:** 15-20 minutes  
**📊 Report Template:** `vibe_prompts/reports/P006_report.md`

---

### P007 - Notification System Design

**📋 Copy this entire prompt:**

```
I'm working on the AIU Trips & Events Management System (Java Spring Boot backend).

Task: Design the complete notification system using multiple patterns. Reason systematically.

Step 1: Analyze requirements
What types of notifications? Who sends/receives? What channels (Email, SMS, Push)?

Step 2: Choose appropriate patterns
Which creational patterns for notification creation? Which structural patterns for channel abstraction? Which behavioral patterns for distribution?

Step 3: Design class structure
What are the core classes? How do they interact? What are the key interfaces?

Step 4: Plan implementation details
How to handle async notifications? How to track status? How to handle failures?

Step 5: Integration strategy
How does this connect to existing services? What configuration is needed?

Based on your analysis, provide:
1. PlantUML class diagram
2. Complete Java implementation
3. Integration guide
4. Testing strategy

Document your reasoning at each step.

Output all files to: vibe_prompts/outputs/P007/
```

**📝 Chaining:** No  
**⏱️ Estimated Time:** 20-30 minutes  
**📊 Report Template:** `vibe_prompts/reports/P007_report.md`

---

## Scenario Prompts

### P008 - Complete Backend from Diagrams

**📋 Copy this entire prompt:**

```
I'm working on the AIU Trips & Events Management System.

Task: Generate complete Spring Boot backend implementation.

Reference: Use class diagrams from Milestones/PM_3/Class Diagram/After DP/

Generate:

1. Entity Layer:
   - User, Activity (abstract), Event, Trip, Booking, Ticket, Notification, Report, Feedback
   - All JPA annotations
   - Proper relationships (@OneToMany, @ManyToOne, etc.)

2. Repository Layer:
   - Spring Data JPA repositories for all entities
   - Custom queries where needed
   - Pagination support

3. Service Layer:
   - Business logic for all entities
   - Transaction management
   - Input validation

4. Controller Layer:
   - RESTful APIs for all operations
   - Proper HTTP methods and status codes
   - DTOs for request/response

5. Design Patterns:
   - Implement all 11 patterns from diagrams
   - Factory, Builder, Prototype, Command, Chain, State, Strategy, Decorator, Bridge, Adapter, Memento

6. Configuration:
   - application.properties
   - Bean configurations
   - Security setup (basic)

7. Error Handling:
   - Global exception handler
   - Custom exceptions
   - Proper error responses

Success Criteria:
- 100% compilation success
- All patterns correctly implemented
- Clean code organization
- Proper documentation

Output all files to: vibe_prompts/outputs/P008/
```

**📝 Chaining:** ⚠️ **YES - Multi-stage recommended**
```
Stage 1: Entity + Repository layer
Stage 2: Service layer
Stage 3: Controller layer
Stage 4: Design patterns
Stage 5: Configuration & error handling
```

**⏱️ Estimated Time:** 60-90 minutes (if chained)  
**📊 Report Template:** `vibe_prompts/reports/P008_report.md`

---

### P009 - Complete Frontend Application

**📋 Copy this entire prompt:**

```
I'm working on the AIU Trips & Events Management System.

Task: Generate complete React application from scratch.

Generate:

1. Project Setup:
   - Create React App structure
   - Dependencies (axios, react-router, etc.)
   - ESLint configuration

2. Core Components:
   - Authentication (Login, Register, Profile)
   - Activities (List, Detail, Create/Edit for Events and Trips)
   - Bookings (Create, List, Detail)
   - Notifications (Center, List)
   - Reports (Dashboard)
   - Admin (User management, Activity management)

3. Shared Components:
   - Navigation, Header, Footer
   - Loading spinner
   - Error boundary
   - Modal dialogs
   - Form components (Input, Select, Button)

4. Services:
   - API client (axios configuration)
   - Auth service
   - Activity service
   - Booking service
   - Notification service

5. State Management:
   - Auth context
   - Theme context
   - Custom hooks (useAuth, useApi)
   - Local storage utilities

6. Routing:
   - Protected routes
   - Public routes
   - 404 page
   - Route guards

7. Styling:
   - Responsive design
   - CSS modules or styled-components
   - Mobile-first approach
   - Consistent theme

Success Criteria:
- All components render without errors
- Proper API integration
- Responsive design
- Good UX

Output all files to: vibe_prompts/outputs/P009/
```

**📝 Chaining:** ⚠️ **YES - Multi-stage recommended**
```
Stage 1: Project setup + Shared components
Stage 2: Authentication components
Stage 3: Activity components
Stage 4: Booking components
Stage 5: Notification & Report components
Stage 6: Services & State management
Stage 7: Routing & Styling
```

**⏱️ Estimated Time:** 60-90 minutes (if chained)  
**📊 Report Template:** `vibe_prompts/reports/P009_report.md`

---

### P010 - Waiting List Feature

**📋 Copy this entire prompt:**

```
I'm working on the AIU Trips & Events Management System.

Task: Implement complete waiting list feature when activities are full.

Requirements:
- Auto-notify waiting list when spots open
- Maintain FIFO order
- Time-limited offers (24 hours to book)
- Auto-promote next person if declined

Generate:

1. Data Model:
   - WaitingList entity (JPA)
   - Status tracking (WAITING, OFFERED, ACCEPTED, DECLINED, EXPIRED)
   - Offer expiration timestamp

2. Business Logic:
   - Join/leave waiting list
   - Auto-promotion logic
   - Notification triggers
   - Cancellation handling

3. API Endpoints:
   - POST /api/waiting-list/join
   - DELETE /api/waiting-list/leave
   - GET /api/waiting-list/status/{activityId}
   - POST /api/waiting-list/accept-offer

4. Background Jobs:
   - Expiration checker (scheduled task)
   - Promotion processor
   - Notification sender

5. Frontend Components:
   - Waiting list join button
   - Status display
   - Offer acceptance modal
   - Position in queue indicator

6. Notifications:
   - "Spot available" notification
   - "Offer expiring" reminder
   - "Promoted from waiting list"

Design Patterns to Use:
- Observer (for notifications)
- Command (for user actions)
- State (for offer lifecycle)

Output all files to: vibe_prompts/outputs/P010/
```

**📝 Chaining:** No  
**⏱️ Estimated Time:** 30-45 minutes  
**📊 Report Template:** `vibe_prompts/reports/P010_report.md`

---

### P011 - Review and Rating System

**📋 Copy this entire prompt:**

```
I'm working on the AIU Trips & Events Management System.

Task: Implement complete review and rating system for activities.

Requirements:
- 5-star rating system
- Text reviews (minimum 50 characters)
- Photo upload (up to 5 per review)
- Helpful votes on reviews
- Organizer responses
- Report inappropriate reviews
- Average rating calculation
- Review moderation queue

Generate:

1. Data Model (JPA):
   - Review entity (rating, text, photos, votes, status)
   - ReviewPhoto entity
   - ReviewVote entity
   - ReviewReport entity

2. Service Layer:
   - ReviewService (CRUD, moderation, statistics)
   - Photo upload handling
   - Average rating calculation
   - Moderation workflow

3. API Endpoints:
   - POST /api/reviews (create review)
   - GET /api/reviews/activity/{id} (list reviews)
   - PUT /api/reviews/{id}/vote (helpful vote)
   - POST /api/reviews/{id}/report (report review)
   - POST /api/reviews/{id}/respond (organizer response)
   - GET /api/reviews/moderation (admin queue)

4. Frontend Components:
   - ReviewForm (with photo upload)
   - ReviewList (with filtering)
   - ReviewCard (display, votes, report)
   - RatingDisplay (stars + average)
   - ModerationQueue (admin)

5. Integrations:
   - File upload service (for photos)
   - Notification system (for review alerts)
   - Activity service (for average rating)

Design Patterns:
- Decorator (for review enhancements)
- Chain of Responsibility (for moderation)
- Observer (for notifications)

Output all files to: vibe_prompts/outputs/P011/
```

**📝 Chaining:** No  
**⏱️ Estimated Time:** 30-45 minutes  
**📊 Report Template:** `vibe_prompts/reports/P011_report.md`

---

## Refactoring Prompts

### P012 - Add Strategy Pattern

**📋 Copy this entire prompt:**

```
I'm working on the AIU Trips & Events Management System.

Task: Refactor existing booking system to use Strategy Pattern for pricing.

Current Code:
public class BookingService {
    public BigDecimal calculatePrice(Activity activity, User user) {
        return activity.getBasePrice();
    }
}

Refactor to Strategy Pattern:

1. Design Strategy Interface:
   - PricingStrategy interface
   - calculatePrice(Activity, User, LocalDate) method

2. Implement Strategies:
   - EarlyBirdPricingStrategy (20% off >30 days)
   - StudentDiscountStrategy (15% off with student ID)
   - GroupDiscountStrategy (10% off for 5+ people)
   - SeasonalPricingStrategy (varies by season)
   - DefaultPricingStrategy (base price)

3. Modify Context:
   - Update BookingService to use strategies
   - Add strategy selection logic
   - Ensure backward compatibility

4. Database Updates:
   - Add discount_type field to Booking
   - Migration script

5. Update Tests:
   - Test each strategy
   - Test strategy switching

Constraints:
- Minimal changes to existing code
- No breaking API changes
- Maintain current functionality
- Add comprehensive tests

Output all files to: vibe_prompts/outputs/P012/
```

**📝 Chaining:** No  
**⏱️ Estimated Time:** 20-30 minutes  
**📊 Report Template:** `vibe_prompts/reports/P012_report.md`

---

### P013 - Refactor to Builder Pattern

**📋 Copy this entire prompt:**

```
I'm working on the AIU Trips & Events Management System.

Task: Refactor Activity creation to use Builder Pattern.

Current Code:
Activity activity = new Event("Conference", "Tech Conference", 
    LocalDate.now(), LocalDate.now().plusDays(3), "Building A", 
    100, new BigDecimal("50.00"), ActivityStatus.PENDING, 
    ActivityCategory.CONFERENCE, "john@example.com");

Refactor to Builder Pattern:

1. Design ActivityBuilder interface
2. Implement EventBuilder and TripBuilder
3. Add fluent API methods (withName, withDescription, etc.)
4. Include validation in build() method
5. Maintain backward compatibility
6. Update all usage instances
7. Add builder tests

Generate:
- ActivityBuilder interface
- EventBuilder implementation
- TripBuilder implementation
- Updated Activity constructors
- Migration guide for existing code
- Unit tests

Output all files to: vibe_prompts/outputs/P013/
```

**📝 Chaining:** No  
**⏱️ Estimated Time:** 20-30 minutes  
**📊 Report Template:** `vibe_prompts/reports/P013_report.md`

---

## Benchmarking Prompts

### P014 - Speed Test (User Management)

**📋 Copy this entire prompt:**

```
I'm working on the AIU Trips & Events Management System.

Task: Generate complete User Management module for speed benchmarking.

Generate:
1. User entity with validation annotations
2. UserRepository with custom queries
3. UserService with full CRUD operations
4. UserController with REST endpoints
5. UserDTO (request/response)
6. Unit tests for service layer

Requirements:
- Complete, compilable code
- Proper error handling
- Input validation
- Documentation

Track:
- Time to generate
- Lines of code
- Compilation success

Output all files to: vibe_prompts/outputs/P014/
```

**📝 Chaining:** No  
**⏱️ Estimated Time:** 10-15 minutes  
**📊 Report Template:** `vibe_prompts/reports/P014_report.md`

---

### P015 - Quality Test (Command Pattern)

**📋 Copy this entire prompt:**

```
I'm working on the AIU Trips & Events Management System.

Task: Implement Command Pattern for activity operations (quality evaluation).

Generate:
1. Command interface
2. Concrete commands:
   - CreateActivityCommand
   - UpdateActivityCommand
   - DeleteActivityCommand
   - PublishActivityCommand
3. CommandInvoker
4. Integration with ActivityController

Requirements:
- Perfect pattern implementation
- Excellent code quality
- Comprehensive documentation
- Proper error handling
- SOLID principles adherence
- Clean abstractions

Evaluation Focus:
- Pattern correctness
- Code readability
- Maintainability
- Documentation quality

Output all files to: vibe_prompts/outputs/P015/
```

**📝 Chaining:** No  
**⏱️ Estimated Time:** 15-20 minutes  
**📊 Report Template:** `vibe_prompts/reports/P015_report.md`

---

## Notes

### About Prompt Chaining

When you see ⚠️ **YES - Multi-stage recommended**, it means:
1. The task is complex and better done in stages
2. Each stage builds on the previous one
3. Use the suggested stages for best results
4. Complete one stage, verify it works, then proceed to the next

### Output Structure

Each prompt should generate code in:
```
vibe_prompts/outputs/PROMPT_ID/
├── src/
│   ├── main/
│   │   ├── java/       (for backend)
│   │   └── resources/
│   └── test/
└── README.md           (implementation notes)
```

Or for frontend:
```
vibe_prompts/outputs/PROMPT_ID/
├── src/
│   ├── components/
│   ├── services/
│   ├── contexts/
│   └── styles/
└── README.md           (implementation notes)
```

### Filling Reports

After generating code:
1. Copy the template from `vibe_prompts/reports/PROMPT_ID_report.md`
2. Fill in all sections
3. Compare results across different AI platforms
4. Use for analysis and decision-making
