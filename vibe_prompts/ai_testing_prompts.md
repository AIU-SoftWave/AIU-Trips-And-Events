# AI-Assisted Code Generation Testing Prompts

## Overview

This document contains comprehensive prompts designed to evaluate and compare AI-assisted code generation across different platforms, models, and agents. These prompts are structured to test various aspects of the AIU Trips & Events Management System using diverse prompting frameworks and methodologies.

**Purpose:** Generate detailed reports on AI code generation capabilities across multiple dimensions:
- Platform comparison (GPT-4, Claude, Gemini, Copilot, etc.)
- Framework effectiveness (Chain-of-Thought, Few-Shot, Zero-Shot, etc.)
- Code quality metrics (compilation, patterns, architecture)
- Development speed and efficiency

---

## Table of Contents

1. [Testing Methodology](#testing-methodology)
2. [System Context Prompts](#system-context-prompts)
3. [Framework-Based Prompts](#framework-based-prompts)
4. [Scenario-Based Testing Prompts](#scenario-based-testing-prompts)
5. [Comparison and Benchmarking Prompts](#comparison-and-benchmarking-prompts)
6. [Evaluation Criteria](#evaluation-criteria)
7. [Reporting Template](#reporting-template)
8. [Advanced Testing Scenarios](#advanced-testing-scenarios)
9. [Usage Instructions](#usage-instructions)

---

## Testing Methodology

### Platforms to Test
- **OpenAI:** GPT-4, GPT-4 Turbo, GPT-3.5 Turbo
- **Anthropic:** Claude 3 Opus, Claude 3 Sonnet, Claude 3 Haiku
- **Google:** Gemini Pro, Gemini Ultra, PaLM 2
- **GitHub:** GitHub Copilot, Copilot Chat
- **Microsoft:** Azure OpenAI, Bing Chat
- **Open Source:** LLaMA 2, Mistral, CodeLLaMA

### Prompting Frameworks to Test
1. **Zero-Shot Prompting** - Direct instruction without examples
2. **Few-Shot Prompting** - Include 2-3 examples
3. **Chain-of-Thought (CoT)** - Step-by-step reasoning
4. **Tree-of-Thought (ToT)** - Multiple reasoning paths
5. **ReAct** - Reasoning + Acting framework
6. **Self-Consistency** - Multiple outputs with voting
7. **Constitutional AI** - With ethical constraints
8. **Prompt Chaining** - Multi-stage prompts

### Evaluation Dimensions
- Code Quality (0-10)
- Pattern Correctness (0-100%)
- Compilation Success (0-100%)
- Documentation Quality (0-10)
- Development Time (minutes)
- Integration Quality (0-10)
- SOLID Principles Adherence (0-100%)

---

## System Context Prompts

### Base Context Prompt (Use Before All Tests)

```
You are an expert software architect and developer working on the AIU Trips & Events Management System. This is a comprehensive university event and trips management platform built with:

**Backend Stack:**
- Java Spring Boot
- RESTful APIs
- JPA/Hibernate for persistence
- MySQL database
- Maven for build management

**Frontend Stack:**
- React.js
- Axios for API integration
- React Router for navigation
- Context API for state management

**Architecture:**
- 11 Design Patterns implemented (Factory, Builder, Prototype, Command, Chain of Responsibility, State, Strategy, Decorator, Bridge, Adapter, Memento)
- Multi-layered architecture (Controller, Service, Repository)
- Clean separation of concerns

**Domain Entities:**
- User (authentication and authorization)
- Activity (abstract base class)
  - Event (extends Activity)
  - Trip (extends Activity)
- Booking (user activity registrations)
- Ticket (booking confirmations)
- Notification (multi-channel system)
- Report (analytics and exports)
- Feedback (user reviews)

**Key Features:**
- User authentication and role-based access
- Event and trip management
- Booking and ticketing system
- Multi-channel notifications (Email, SMS, Push)
- Comprehensive reporting
- Feedback and rating system
- Admin dashboard

**Your Task:**
[Specific task will be provided in individual prompts]
```

---

## Framework-Based Prompts

### 1. Zero-Shot Prompting

#### Test 1.1: Pattern Implementation (Zero-Shot)

```
Using the AIU Trips & Events Management System context, implement the Observer Pattern for the notification system.

Requirements:
- Create Subject interface for notification publishers
- Create Observer interface for notification subscribers
- Implement concrete observers for Email, SMS, and Push notifications
- Ensure proper subscribe/unsubscribe mechanisms
- Implement notification broadcasting to all observers

Generate complete Java code with proper package structure.
```

#### Test 1.2: Component Creation (Zero-Shot)

```
Create a React component for displaying trip details in the AIU Trips & Events system.

Requirements:
- Display trip name, description, dates, location, price
- Show current booking count and available seats
- Include booking button with validation
- Handle loading and error states
- Use functional components with hooks

Provide complete React component code with proper styling.
```

#### Test 1.3: Database Query Generation (Zero-Shot)

```
Generate Spring Data JPA repository methods for the AIU Trips & Events booking system.

Requirements:
- Find all bookings for a specific user
- Find all bookings for a specific activity
- Find bookings by status and date range
- Count total bookings for an activity
- Get booking statistics grouped by month

Provide complete repository interface with custom query methods.
```

---

### 2. Few-Shot Prompting

#### Test 2.1: Service Layer Implementation (Few-Shot)

```
For the AIU Trips & Events system, I need you to create service classes following these examples:

**Example 1 - UserService:**
```java
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
```

**Example 2 - EventService:**
```java
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
```

Now, following these patterns, create:
1. BookingService with CRUD operations
2. NotificationService with send capabilities
3. ReportService with generation methods

Ensure proper error handling, validation, and transaction management.
```

#### Test 2.2: API Controller Creation (Few-Shot)

```
Using the AIU Trips & Events system, create REST controllers following these examples:

**Example 1 - UserController:**
```java
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
```

Now create:
1. TripController with full CRUD operations
2. BookingController with booking workflow endpoints
3. FeedbackController with review management

Include proper validation, error handling, and HTTP status codes.
```

---

### 3. Chain-of-Thought (CoT) Prompting

#### Test 3.1: Complex Pattern Integration (CoT)

```
Let's implement the Strategy Pattern for pricing in the AIU Trips & Events system. Think through this step by step:

**Step 1:** Identify the pricing variations
- What types of pricing strategies might we need?

**Step 2:** Design the strategy interface
- What methods should the pricing strategy interface have?
- What parameters are needed for price calculation?

**Step 3:** Plan concrete implementations
- How would each pricing strategy calculate differently?

**Step 4:** Design the context class
- How will Activity/Booking use the strategy?

**Step 5:** Implement integration
- How does this integrate with existing booking system?

Now, following your step-by-step reasoning, generate:
1. PricingStrategy interface
2. All concrete strategy implementations (Early bird, Student, Group, Seasonal, Default)
3. Context class integration
4. Usage examples in booking flow

Show your reasoning for each decision and provide complete code.
```

#### Test 3.2: Architecture Design (CoT)

```
Design the complete notification system for AIU Trips & Events using multiple patterns. Reason through this systematically:

**Step 1:** Analyze requirements
- What types of notifications are needed?
- Who sends and receives notifications?
- What channels should be supported?

**Step 2:** Choose appropriate patterns
- Which creational patterns fit notification creation?
- Which structural patterns help with channel abstraction?
- Which behavioral patterns handle notification distribution?

**Step 3:** Design class structure
- What are the core classes needed?
- How do they interact?

**Step 4:** Plan implementation details
- How to handle async notifications?
- How to track notification status?

**Step 5:** Integration strategy
- How does this connect to existing services?

Based on your analysis, provide:
1. UML class diagram (PlantUML format)
2. Complete Java implementation
3. Integration guide
4. Testing strategy

Document your reasoning at each step.
```

---

### 4. Tree-of-Thought (ToT) Prompting

#### Test 4.1: Multi-Path Architecture Exploration (ToT)

```
Explore different architectural approaches for the booking system in AIU Trips & Events. Consider multiple paths:

**Path 1: Event-Driven Architecture**
- Use event bus for booking operations
- Async processing with message queues
- Event sourcing for booking history
Evaluate: Scalability, complexity, consistency

**Path 2: CQRS Pattern**
- Separate read and write models
- Command handlers for bookings
- Query handlers for data retrieval
Evaluate: Performance, maintainability, complexity

**Path 3: Traditional Layered Architecture**
- Controller → Service → Repository
- Synchronous operations
- Simple transaction management
Evaluate: Simplicity, reliability, scalability

For each path:
1. Provide detailed pros and cons
2. Estimate implementation effort (1-10 scale)
3. Assess fit for university context
4. Rate: Complexity, Performance, Maintainability (1-10 each)

Then select the best path and implement:
- Core booking service
- API endpoints
- Data access layer
- Integration points

Justify your selection with reasoning from all paths.
```

---

### 5. ReAct (Reasoning + Acting) Prompting

#### Test 5.1: Debug and Fix with Reasoning (ReAct)

```
You're debugging a booking system issue in AIU Trips & Events. Follow this ReAct framework:

**Problem:** Users can book activities that are already full, causing overbooking.

**Thought 1:** I need to understand the booking flow and where validation happens.
**Action 1:** Examine the BookingController and BookingService code.
**Observation 1:** [Analyze the code structure]

**Thought 2:** There might be a race condition in the availability check.
**Action 2:** Look for synchronization mechanisms or database constraints.
**Observation 2:** [Check for transaction isolation]

**Thought 3:** The solution needs to handle concurrent bookings safely.
**Action 3:** Design a solution using optimistic or pessimistic locking.
**Observation 3:** [Evaluate trade-offs]

**Thought 4:** Implementation should be minimal and non-breaking.
**Action 4:** Add version field or use SELECT FOR UPDATE.
**Observation 4:** [Implement the fix]

**Thought 5:** Need to test the fix under concurrent load.
**Action 5:** Create test scenarios for concurrent bookings.

Now, following this ReAct pattern:
1. Show your reasoning at each step
2. Provide code analysis
3. Design the solution
4. Implement the fix
5. Create test cases
6. Document the solution

Include all reasoning and observations in your response.
```

---

### 6. Self-Consistency Prompting

#### Test 6.1: Multiple Solution Approaches (Self-Consistency)

```
Generate 3 different implementations of the Activity factory pattern for AIU Trips & Events. Then analyze consistency and choose the best approach.

**Implementation 1:** Simple Factory
- Single factory class
- Switch/case for Activity type
- Direct instantiation

**Implementation 2:** Factory Method
- Abstract factory with template method
- Subclasses for Event and Trip factories
- Extensible design

**Implementation 3:** Abstract Factory
- Factory interface
- Multiple concrete factories
- Product families support

For each implementation:
1. Provide complete Java code
2. Show usage examples
3. List pros and cons
4. Rate complexity (1-10)
5. Rate extensibility (1-10)
6. Rate maintainability (1-10)

Then:
- Compare all three approaches
- Identify common patterns across implementations
- Vote on the best approach based on:
  - Project requirements
  - Team experience
  - Future extensibility
  - Code simplicity

Select the winner and explain why consistency points to this choice.
```

---

### 7. Constitutional AI Prompting

#### Test 7.1: Secure Implementation with Constraints (Constitutional)

```
Implement user authentication for AIU Trips & Events following these constitutional principles:

**Security Principles:**
1. Never store passwords in plain text
2. Always validate and sanitize user input
3. Implement proper session management
4. Use prepared statements for SQL
5. Follow OWASP security guidelines
6. Implement rate limiting for login attempts
7. Use HTTPS for all communications
8. Log security events properly

**Privacy Principles:**
1. Minimize data collection
2. Encrypt sensitive data
3. Implement data retention policies
4. Provide user data access/deletion
5. Comply with GDPR/privacy regulations

**Ethical Principles:**
1. Provide clear user consent mechanisms
2. Transparent data usage
3. Accessible to users with disabilities
4. No discriminatory algorithms

**Task:**
Create a complete authentication system that adheres to ALL these principles:
1. User registration with validation
2. Login with secure password handling
3. JWT token generation and validation
4. Password reset functionality
5. Session management
6. Security logging

For each component:
- Explain how it follows constitutional principles
- Document security decisions
- Provide threat model analysis
- Include compliance checklist

Show that your implementation respects all constraints.
```

---

### 8. Prompt Chaining

#### Test 8.1: Multi-Stage Development (Chaining)

```
**Stage 1 - Requirements Analysis:**
Analyze the requirements for a comprehensive reporting system in AIU Trips & Events:
- Activity reports (attendance, revenue, ratings)
- User reports (booking history, preferences)
- Financial reports (revenue breakdown, forecasts)
- Export formats (PDF, Excel, CSV)

Output: Detailed requirements document

**Stage 2 - Architecture Design:**
Using the requirements from Stage 1, design the reporting architecture:
- Choose appropriate design patterns
- Define class structure
- Plan database queries
- Design API endpoints

Output: Architecture document with UML diagrams

**Stage 3 - Data Layer Implementation:**
Using the architecture from Stage 2, implement:
- Report repository interfaces
- Complex queries for analytics
- Data aggregation logic
- Caching strategy

Output: Repository and query code

**Stage 4 - Service Layer Implementation:**
Using the data layer from Stage 3, implement:
- Report generation services
- Export functionality
- Scheduling for periodic reports
- Error handling

Output: Service layer code

**Stage 5 - API Layer Implementation:**
Using the service layer from Stage 4, implement:
- REST endpoints for reports
- Request validation
- Response formatting
- API documentation

Output: Controller code and API specs

**Stage 6 - Frontend Integration:**
Using the API from Stage 5, create:
- Report dashboard components
- Data visualization charts
- Export functionality
- Filtering and search

Output: React components

**Stage 7 - Testing & Documentation:**
Using all previous stages, create:
- Unit tests for services
- Integration tests for APIs
- User documentation
- Deployment guide

Output: Complete test suite and documentation

Execute all 7 stages sequentially, using output from each stage as input to the next.
```

---

## Scenario-Based Testing Prompts

### Scenario A: Fresh Implementation from Diagrams

#### Prompt A1: Complete Backend Generation

```
You are starting a fresh implementation of the AIU Trips & Events backend system.

**Given:** PlantUML class diagrams (reference: Milestones/PM_3/Class Diagram/After DP/)

**Task:** Generate complete Spring Boot backend with:

1. **Entity Layer:**
   - User, Activity (abstract), Event, Trip, Booking, Ticket, Notification, Report, Feedback
   - All JPA annotations
   - Proper relationships

2. **Repository Layer:**
   - Spring Data JPA repositories
   - Custom queries where needed
   - Pagination support

3. **Service Layer:**
   - Business logic for all entities
   - Transaction management
   - Validation

4. **Controller Layer:**
   - RESTful APIs for all operations
   - Proper HTTP methods and status codes
   - Request/Response DTOs

5. **Design Patterns:**
   - All 11 patterns from diagram
   - Proper integration
   - Clean abstractions

6. **Configuration:**
   - application.properties
   - Bean configurations
   - Security setup

7. **Error Handling:**
   - Global exception handler
   - Custom exceptions
   - Proper error responses

**Deliverables:**
- Complete package structure
- All Java files
- Configuration files
- README with setup instructions

**Success Criteria:**
- 100% compilation success
- All patterns correctly implemented
- Clean code organization
- Proper documentation

Time yourself and report: Lines of code generated, time taken, any issues encountered.
```

#### Prompt A2: Complete Frontend Generation

```
You are creating the React frontend for AIU Trips & Events from scratch.

**Given:** Backend API structure and entity models

**Task:** Generate complete React application with:

1. **Project Setup:**
   - Create React App or Vite
   - Project structure
   - Dependencies (axios, react-router, etc.)

2. **Core Components:**
   - Authentication (Login, Register, Profile)
   - Activities (List, Detail, Create/Edit)
   - Bookings (Create, List, Detail)
   - Notifications (Center, List, Detail)
   - Reports (Dashboard, Charts)
   - Admin (Management panels)

3. **Shared Components:**
   - Navigation/Header/Footer
   - Loading states
   - Error boundaries
   - Modals and dialogs
   - Form components

4. **Services:**
   - API client configuration
   - Authentication service
   - Activity service
   - Booking service
   - Notification service

5. **State Management:**
   - Context providers
   - Custom hooks
   - Local storage utilities

6. **Routing:**
   - Protected routes
   - Public routes
   - 404 handling
   - Navigation guards

7. **Styling:**
   - Responsive design
   - Consistent theme
   - CSS modules or styled-components
   - Mobile-first approach

**Deliverables:**
- Complete component tree
- All React files
- CSS/styling files
- Package.json
- README

**Success Criteria:**
- All components render without errors
- Proper API integration
- Responsive design
- Good user experience

Report: Component count, time taken, challenges faced.
```

---

### Scenario B: Pattern Refactoring

#### Prompt B1: Add Strategy Pattern to Existing Code

```
**Context:** You have an existing booking system in AIU Trips & Events that uses fixed pricing.

**Current Code:**
```java
public class BookingService {
    public BigDecimal calculatePrice(Activity activity, User user) {
        return activity.getBasePrice();
    }
}
```

**Task:** Refactor to use Strategy Pattern for dynamic pricing:

1. **Design Strategy Interface:**
   - Define pricing strategy contract
   - Identify required parameters

2. **Implement Strategies:**
   - EarlyBirdPricingStrategy (20% off before 30 days)
   - StudentDiscountStrategy (15% off with student ID)
   - GroupDiscountStrategy (10% off for 5+ people)
   - SeasonalPricingStrategy (varies by season)
   - DefaultPricingStrategy (base price)

3. **Create Context:**
   - Modify BookingService to use strategies
   - Add strategy selection logic
   - Ensure backward compatibility

4. **Update Database:**
   - Add pricing metadata if needed
   - Migration scripts

5. **Update Tests:**
   - Test each strategy
   - Test strategy switching

**Constraints:**
- Minimal changes to existing code
- No breaking changes to API
- Maintain current functionality
- Add comprehensive tests

Provide: Complete refactored code, migration guide, test suite.
```

#### Prompt B2: Refactor to Builder Pattern

```
**Context:** Activity creation in AIU Trips & Events uses constructor with many parameters.

**Current Code:**
```java
Activity activity = new Event("Conference", "Tech Conference", 
    LocalDate.now(), LocalDate.now().plusDays(3), "Building A", 
    100, new BigDecimal("50.00"), ActivityStatus.PENDING, 
    ActivityCategory.CONFERENCE, "john@example.com");
```

**Task:** Refactor to use Builder Pattern:

1. Design ActivityBuilder interface
2. Implement EventBuilder and TripBuilder
3. Add fluent API methods
4. Include validation in build() method
5. Maintain backward compatibility
6. Update all usage instances
7. Add builder tests

Provide: Complete refactored code with examples.
```

---

### Scenario C: Feature Addition

#### Prompt C1: Add Waiting List Feature

```
**New Requirement:** When an activity is full, users should be able to join a waiting list.

**Specifications:**
- Automatically notify waiting list when spots open
- Maintain waiting list order (FIFO)
- Time-limited offers (24 hours to book)
- Automatic promotion to next person if declined

**Task:** Implement complete waiting list feature:

1. **Data Model:**
   - WaitingList entity
   - Status tracking
   - Offer expiration

2. **Business Logic:**
   - Join/leave waiting list
   - Automatic promotion logic
   - Notification triggers
   - Cancellation handling

3. **API Endpoints:**
   - POST /api/waiting-list/join
   - DELETE /api/waiting-list/leave
   - GET /api/waiting-list/status/{activityId}
   - POST /api/waiting-list/accept-offer

4. **Background Jobs:**
   - Expiration checker
   - Promotion processor
   - Notification sender

5. **Frontend Components:**
   - Waiting list join button
   - Status display
   - Offer acceptance modal
   - Position in queue

6. **Notifications:**
   - "Spot available" notification
   - "Offer expiring" reminder
   - "Promoted from waiting list"

**Design Patterns to Use:**
- Observer (for notifications)
- Command (for user actions)
- State (for offer lifecycle)

Deliver: Complete implementation with tests and documentation.
```

#### Prompt C2: Add Review and Rating System

```
**New Requirement:** Users should be able to review and rate activities they've attended.

**Specifications:**
- 5-star rating system
- Text reviews with minimum 50 characters
- Photos upload (up to 5 per review)
- Helpful votes on reviews
- Response from organizers
- Report inappropriate reviews
- Average rating calculation
- Review moderation queue

**Task:** Implement complete review system:

1. Data model (Review entity with ratings, photos, votes)
2. Service layer with validation
3. API endpoints for CRUD operations
4. Frontend components (Review form, display, list)
5. Image upload handling
6. Moderation workflow
7. Notification system integration
8. Aggregate rating calculation

Design Patterns to Use:
- Decorator (for review enhancements)
- Chain of Responsibility (for moderation)
- Observer (for notifications)

Deliver: Complete implementation with tests.
```

---

## Comparison and Benchmarking Prompts

### Benchmark 1: Speed Test

```
**Objective:** Measure code generation speed across platforms.

**Task:** Generate a complete User Management module with:
- User entity with validation
- UserRepository with custom queries
- UserService with CRUD operations
- UserController with REST endpoints
- UserDTO for requests/responses
- Unit tests for service layer

**Measure:**
1. Time to first response
2. Time to complete code
3. Number of iterations needed
4. Compilation success rate

**Record for Each Platform:**
- Total time (minutes)
- Lines of code generated
- Number of errors
- Quality score (1-10)

**Report Format:**
| Platform | Time | LOC | Errors | Quality | Winner |
|----------|------|-----|--------|---------|--------|
| GPT-4    |      |     |        |         |        |
| Claude-3 |      |     |        |         |        |
| Gemini   |      |     |        |         |        |
```

### Benchmark 2: Quality Test

```
**Objective:** Compare code quality across different models.

**Task:** Implement the Command Pattern for activity operations:
- Command interface
- Concrete commands (Create, Update, Delete, Publish)
- Command invoker
- Integration with controller

**Evaluate:**
1. **Pattern Correctness (0-100%):**
   - Proper interface design
   - Correct implementations
   - Appropriate usage

2. **Code Quality (0-10):**
   - Readability
   - Maintainability
   - Documentation
   - Error handling

3. **SOLID Principles (0-100%):**
   - Single Responsibility
   - Open/Closed
   - Liskov Substitution
   - Interface Segregation
   - Dependency Inversion

4. **Best Practices (0-10):**
   - Naming conventions
   - Package structure
   - Exception handling
   - Logging

**Scoring Matrix:**
| Platform | Pattern | Quality | SOLID | Practices | Total | Rank |
|----------|---------|---------|-------|-----------|-------|------|
| GPT-4    |         |         |       |           |       |      |
| Claude-3 |         |         |       |           |       |      |
| Gemini   |         |         |       |           |       |      |
```

### Benchmark 3: Pattern Implementation Test

```
**Objective:** Test ability to implement design patterns correctly.

**Test Set:** Implement all 11 patterns for AIU Trips & Events:

1. Factory Pattern - Model creation
2. Builder Pattern - Activity construction
3. Prototype Pattern - Activity cloning
4. Command Pattern - Controller operations
5. Chain of Responsibility - Request handling
6. State Pattern - Activity lifecycle
7. Strategy Pattern - Pricing calculation
8. Decorator Pattern - Ticket enhancements
9. Bridge Pattern - Notification channels
10. Adapter Pattern - External service integration
11. Memento Pattern - State preservation

**For Each Pattern:**
- Generate implementation
- Measure: Time, LOC, Correctness
- Rate: Complexity handling (1-10)
- Score: Pattern adherence (1-10)

**Aggregate Results:**
| Pattern | Best Platform | Avg Quality | Avg Time |
|---------|--------------|-------------|----------|
| Factory |              |             |          |
| Builder |              |             |          |
| ...     |              |             |          |

**Overall Winner:** Platform with highest average scores.
```

### Benchmark 4: Integration Test

```
**Objective:** Test end-to-end feature implementation.

**Task:** Implement complete booking workflow:
- User selects activity
- Checks availability
- Applies discount (if applicable)
- Creates booking
- Processes payment (mock)
- Generates ticket
- Sends confirmation email
- Updates activity capacity
- Records transaction

**Measure:**
1. Completeness (all steps implemented)
2. Integration quality (how well components work together)
3. Error handling (edge cases covered)
4. Code organization
5. Time taken

**Evaluate Across Platforms:**
- Which handles complexity best?
- Which has fewest integration issues?
- Which requires least manual fixes?
- Which has best code structure?

Report comprehensive comparison.
```

---

## Evaluation Criteria

### Code Quality Rubric

#### Compilation & Syntax (Weight: 20%)
- 10/10: Compiles without errors
- 8/10: Minor syntax fixes needed
- 6/10: Several compilation errors
- 4/10: Major syntax issues
- 2/10: Code doesn't compile

#### Pattern Implementation (Weight: 25%)
- 10/10: Perfect pattern implementation
- 8/10: Correct with minor issues
- 6/10: Pattern present but flawed
- 4/10: Pattern partially implemented
- 2/10: Pattern missing or wrong

#### Architecture & Design (Weight: 20%)
- 10/10: Excellent architecture, SOLID principles
- 8/10: Good design with minor issues
- 6/10: Acceptable but could improve
- 4/10: Poor structure
- 2/10: No clear architecture

#### Documentation (Weight: 15%)
- 10/10: Comprehensive docs, JavaDoc, README
- 8/10: Good documentation
- 6/10: Basic documentation
- 4/10: Minimal documentation
- 2/10: No documentation

#### Testing (Weight: 10%)
- 10/10: Complete test coverage
- 8/10: Good test coverage
- 6/10: Basic tests
- 4/10: Minimal tests
- 2/10: No tests

#### Best Practices (Weight: 10%)
- 10/10: Follows all best practices
- 8/10: Mostly follows best practices
- 6/10: Some best practices
- 4/10: Few best practices
- 2/10: Ignores best practices

### Speed Metrics

- **Immediate (<1 min):** Fast response
- **Quick (1-5 min):** Normal response
- **Moderate (5-15 min):** Slower response
- **Slow (15-30 min):** Very slow
- **Very Slow (>30 min):** Too slow

### Accuracy Metrics

- **Perfect (100%):** No changes needed
- **Excellent (90-99%):** Minor adjustments
- **Good (80-89%):** Some fixes required
- **Fair (70-79%):** Significant fixes
- **Poor (<70%):** Major rework needed

---

## Reporting Template

### Individual Test Report

```markdown
# Test Report: [Test Name]

## Test Configuration
- **Platform:** [GPT-4 / Claude-3 / Gemini / etc.]
- **Model:** [Specific model version]
- **Framework:** [Zero-Shot / Few-Shot / CoT / etc.]
- **Date:** [Test date]
- **Tester:** [Your name]

## Test Details
**Prompt Used:** [Copy exact prompt]

**Context Provided:** [Any additional context]

**Expected Output:** [What you expected]

## Results

### Generated Code
- **Lines of Code:** [Number]
- **Files Generated:** [Count]
- **Generation Time:** [Minutes]
- **Iterations Needed:** [Count]

### Quality Scores
| Metric | Score | Notes |
|--------|-------|-------|
| Compilation | X/10 | [Details] |
| Pattern Implementation | X/10 | [Details] |
| Architecture | X/10 | [Details] |
| Documentation | X/10 | [Details] |
| Testing | X/10 | [Details] |
| Best Practices | X/10 | [Details] |
| **Overall** | **X/10** | |

### Strengths
1. [Strength 1]
2. [Strength 2]
3. [Strength 3]

### Weaknesses
1. [Weakness 1]
2. [Weakness 2]
3. [Weakness 3]

### Issues Encountered
- [Issue 1]
- [Issue 2]
- [Issue 3]

### Manual Fixes Required
- [Fix 1]
- [Fix 2]
- [Fix 3]

## Analysis

### What Worked Well
[Detailed analysis]

### What Didn't Work
[Detailed analysis]

### Surprises
[Unexpected behaviors or results]

## Recommendation
**Suitable for:** [Project types / scenarios]
**Not suitable for:** [Project types / scenarios]
**Overall Rating:** [X/10]
```

### Comparative Report Template

```markdown
# Comparative Analysis: [Test Category]

## Overview
**Test Objective:** [What was tested]
**Platforms Compared:** [List platforms]
**Test Date Range:** [Dates]

## Summary Table

| Platform | Speed | Quality | Accuracy | Overall | Rank |
|----------|-------|---------|----------|---------|------|
| GPT-4    | X/10  | X/10    | X%       | X/10    | #X   |
| Claude-3 | X/10  | X/10    | X%       | X/10    | #X   |
| Gemini   | X/10  | X/10    | X%       | X/10    | #X   |

## Detailed Comparison

### Speed Analysis
**Winner:** [Platform]
- Fastest: [Platform] at [time]
- Slowest: [Platform] at [time]
- Average: [time]

### Quality Analysis
**Winner:** [Platform]
- Highest quality: [Platform] at [score]
- Lowest quality: [Platform] at [score]
- Average: [score]

### Accuracy Analysis
**Winner:** [Platform]
- Most accurate: [Platform] at [percentage]
- Least accurate: [Platform] at [percentage]
- Average: [percentage]

## Key Findings

### Pattern Recognition
[Which platforms handled patterns best]

### Code Structure
[Which produced best architecture]

### Documentation
[Which documented best]

### Error Handling
[Which handled errors best]

## Recommendations

### For Backend Development
**Best Choice:** [Platform]
**Reason:** [Explanation]

### For Frontend Development
**Best Choice:** [Platform]
**Reason:** [Explanation]

### For Pattern Implementation
**Best Choice:** [Platform]
**Reason:** [Explanation]

### For Rapid Prototyping
**Best Choice:** [Platform]
**Reason:** [Explanation]

## Conclusion

**Overall Winner:** [Platform]

**Verdict:** [Summary of findings]

**Cost-Benefit Analysis:**
| Platform | Cost | Benefit | Value |
|----------|------|---------|-------|
| GPT-4    |      |         |       |
| Claude-3 |      |         |       |
| Gemini   |      |         |       |
```

---

## Advanced Testing Scenarios

### Multi-Agent Collaboration Test

```
**Scenario:** Simulate team development with multiple AI agents.

**Team Structure:**
- Agent 1 (Architect): Design overall system
- Agent 2 (Backend Dev): Implement backend
- Agent 3 (Frontend Dev): Implement frontend
- Agent 4 (Tester): Create tests
- Agent 5 (Reviewer): Review code

**Process:**
1. Architect agent designs system
2. Backend agent implements from design
3. Frontend agent creates UI
4. Tester agent writes tests
5. Reviewer agent provides feedback

**Measure:**
- Inter-agent consistency
- Integration success
- Overall quality
- Time efficiency

Compare against single-agent development.
```

### Iterative Refinement Test

```
**Objective:** Test how well platforms handle iterative improvements.

**Round 1:** Generate initial implementation
**Round 2:** Refactor for better patterns
**Round 3:** Optimize performance
**Round 4:** Improve documentation
**Round 5:** Add comprehensive tests

**Measure:**
- Quality improvement per iteration
- Time per iteration
- Consistency across iterations
- Final quality score

**Questions:**
- Which platform improves most?
- Which reaches plateau fastest?
- Which maintains consistency?
```

### Stress Test: Complex Feature

```
**Challenge:** Implement most complex feature possible.

**Feature:** Complete booking system with:
- Real-time seat availability
- Concurrent booking handling
- Waiting list management
- Payment integration
- Email confirmations
- SMS notifications
- Calendar integration
- Refund processing
- Dynamic pricing
- Loyalty points
- Group bookings
- Special accommodations

**Evaluate:**
- How much can AI handle?
- Where does quality degrade?
- What needs human intervention?
- Time vs quality trade-off
```

---

## Usage Instructions

### How to Execute Tests

1. **Preparation:**
   ```bash
   - Set up testing environment
   - Clone AIU Trips & Events repository
   - Configure each AI platform
   - Prepare evaluation spreadsheet
   ```

2. **Execution:**
   ```bash
   - Select prompt from this document
   - Copy exact prompt to platform
   - Record start time
   - Save generated output
   - Record end time
   - Test generated code
   ```

3. **Evaluation:**
   ```bash
   - Compile and run code
   - Measure quality metrics
   - Document issues
   - Calculate scores
   - Record in comparison sheet
   ```

4. **Analysis:**
   ```bash
   - Compare across platforms
   - Identify patterns
   - Draw conclusions
   - Write report
   ```

### Data Collection Template

```csv
Test_ID,Platform,Model,Framework,Prompt_Type,Time_Minutes,LOC,Compilation_Success,Pattern_Score,Quality_Score,Notes
T001,GPT-4,gpt-4-turbo,Zero-Shot,Backend,12,450,100%,9/10,8.5/10,"Good patterns"
T002,Claude-3,opus,Few-Shot,Frontend,15,380,95%,8/10,8.0/10,"Minor issues"
T003,Gemini,pro,CoT,Pattern,18,520,98%,9/10,8.7/10,"Excellent reasoning"
...
```

### Metrics Tracking Spreadsheet Structure

**Columns:**
1. Test ID
2. Date
3. Platform
4. Model Version
5. Prompting Framework
6. Test Category
7. Time Taken (minutes)
8. Lines of Code
9. Files Generated
10. Compilation Success (%)
11. Pattern Correctness (%)
12. Code Quality Score (0-10)
13. Documentation Score (0-10)
14. Test Coverage (%)
15. SOLID Score (%)
16. Issues Found
17. Manual Fixes Needed
18. Overall Score (0-10)
19. Notes
20. Recommendation

---

## Conclusion

This comprehensive prompt collection enables systematic evaluation of AI-assisted code generation for the AIU Trips & Events Management System. Use these prompts to:

1. **Compare AI platforms** - Identify best tools for different tasks
2. **Test frameworks** - Find most effective prompting strategies
3. **Measure quality** - Establish baseline quality metrics
4. **Optimize workflow** - Improve AI-assisted development process
5. **Generate reports** - Document findings and recommendations

**Next Steps:**
1. Execute tests systematically
2. Collect and analyze data
3. Generate comparative report
4. Share findings with community
5. Iterate and improve prompts

**Expected Outcomes:**
- Comprehensive platform comparison
- Framework effectiveness analysis
- Quality benchmarks
- Best practice recommendations
- Improved AI-assisted development workflow

**Success Criteria:**
- At least 20 tests completed per platform
- Minimum 10 different prompting frameworks tested
- Complete data for all evaluation dimensions
- Detailed comparative analysis report
- Actionable recommendations for team

**Timeline:**
- Week 1: Setup and initial tests (Zero-Shot, Few-Shot)
- Week 2: Advanced frameworks (CoT, ToT, ReAct)
- Week 3: Scenario-based and benchmarking tests
- Week 4: Data analysis and report generation

---

**Document Version:** 1.0  
**Last Updated:** December 17, 2024  
**Author:** AIU Trips & Events AI Testing Framework  
**Purpose:** Systematic AI Code Generation Evaluation  
**Target:** AIU Trips & Events Management System  
**Reference:** Based on /report/05_vibe_coding_analysis.md methodology and findings
