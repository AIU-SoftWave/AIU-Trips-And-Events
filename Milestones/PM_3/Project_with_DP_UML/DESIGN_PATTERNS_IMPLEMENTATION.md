# Design Patterns Implementation Documentation

This document describes all design patterns implemented in the AIU Trips and Events Management System, based on the PlantUML class diagrams from `Milestones/PM_3/Class Diagram/Before DP`.

## Overview

All design patterns have been implemented in the Java backend located at:
`Milestones/PM_3/Project_without_DP_UML/backend/src/main/java/com/aiu/trips/`

## Implemented Patterns

### 1. Factory Pattern (Creational)

**Location:** `factory/` package

**Purpose:** Centralize object creation for domain models

**Components:**
- `IModelFactory` - Interface defining factory contract
- `ModelFactory` - Concrete factory implementation

**Usage:**
```java
@Autowired
private IModelFactory modelFactory;

User user = modelFactory.createModel(User.class);
Event event = modelFactory.createModel(Event.class);
```

---

### 2. Abstract Factory Pattern (Creational)

**Location:** `factory/activity/` package

**Purpose:** Create families of related objects (Events and Trips)

**Components:**
- `IActivityFactory` - Abstract factory interface
- `EventFactory` - Concrete factory for Events
- `TripFactory` - Concrete factory for Trips

**Usage:**
```java
@Autowired
private EventFactory eventFactory;

Event event = eventFactory.createActivity();
Event clonedEvent = eventFactory.clonePrototype(existingEvent);
```

---

### 3. Builder Pattern (Creational)

**Location:** `builder/` package

**Purpose:** Construct complex objects step by step

**Components:**

#### Activity Builder
- `IActivityBuilder` - Builder interface for activities
- `EventBuilder` - Builder for Event objects
- `TripBuilder` - Builder for Trip objects
- `ActivityDirector` - Orchestrates building process

#### Report Builder
- `ReportBuilder` - Abstract builder for reports
- `PdfReportBuilder` - Builder for PDF reports
- `CsvReportBuilder` - Builder for CSV reports
- `ReportDirector` - Orchestrates report building

**Usage:**
```java
@Autowired
private ActivityDirector activityDirector;

Event event = activityDirector.constructEvent(
    "Conference", "Tech Conference", LocalDateTime.now(), 
    "Main Hall", 100.0, 200, user
);
```

---

### 4. Prototype Pattern (Creational)

**Location:** `prototype/` package and `model/Event.java`

**Purpose:** Clone existing objects without coupling to their classes

**Components:**
- `IPrototype<T>` - Generic prototype interface
- `Event.clone()` - Implementation in Event model

**Usage:**
```java
Event originalEvent = eventRepository.findById(1L);
Event clonedEvent = originalEvent.clone();
```

---

### 5. Command Pattern (Behavioral)

**Location:** `command/` package

**Purpose:** Encapsulate requests as objects

**Components:**
- `IControllerCommand` - Command interface
- `ControllerCommandInvoker` - Executes commands and maintains history
- `RegisterCommand` - User registration command
- `LoginCommand` - User login command
- `CreateEventCommand` - Event creation command
- `BookEventCommand` - Event booking command

**Usage:**
```java
@Autowired
private ControllerCommandInvoker invoker;

IControllerCommand command = new CreateEventCommand(eventService, event, userEmail);
Object result = invoker.executeCommand(command);
```

---

### 6. Strategy Pattern (Behavioral)

**Location:** `strategy/` package

**Purpose:** Define a family of algorithms and make them interchangeable

**Components:**
- `PricingStrategy` - Strategy interface
- `StandardPricingStrategy` - No discount pricing
- `EarlyBirdPricingStrategy` - Early booking discount (15% off 30+ days before)
- `BulkGroupDiscountStrategy` - Group discount (10% for 5+, 20% for 10+ tickets)

**Usage:**
```java
@Autowired
private EarlyBirdPricingStrategy earlyBirdPricing;

Double price = earlyBirdPricing.calculatePrice(event, numberOfTickets);
```

---

### 7. State Pattern (Behavioral)

**Location:** `state/` package

**Purpose:** Allow an object to alter behavior when its internal state changes

**Components:**
- `ActivityState` - State interface
- `ActivityLifecycle` - Context managing state transitions
- `UpcomingState` - State for upcoming activities
- `CompletedState` - State for completed activities
- `CancelledState` - State for cancelled activities

**Usage:**
```java
@Autowired
private ActivityLifecycle lifecycle;

lifecycle.setEvent(event);
if (lifecycle.canBook()) {
    // Allow booking
}
```

---

### 8. Chain of Responsibility Pattern (Behavioral)

**Location:** `chain/` package

**Purpose:** Pass requests along a chain of handlers

**Components:**

#### Request Handler Chain
- `RequestHandler` - Abstract handler
- `AuthenticationHandler` - Validates authentication
- `AuthorizationHandler` - Checks permissions
- `ValidationHandler` - Validates request data
- `RateLimitHandler` - Prevents abuse

#### Booking Validation Chain
- `BookingHandler` - Abstract booking handler
- `EligibilityHandler` - Checks user eligibility
- `CapacityHandler` - Verifies event capacity
- `PaymentHandler` - Validates payment

**Usage:**
```java
// Setup chain
authenticationHandler.setNext(authorizationHandler);
authorizationHandler.setNext(validationHandler);
validationHandler.setNext(rateLimitHandler);

// Process request
Object result = authenticationHandler.handle(request);
```

---

### 9. Decorator Pattern (Structural)

**Location:** `decorator/` package

**Purpose:** Add responsibilities to objects dynamically

**Components:**
- `ITicketService` - Ticket service interface
- `BaseTicketService` - Basic ticket service implementation
- `TicketServiceDecorator` - Abstract decorator
- `SignedQrDecorator` - Adds digital signature to tickets
- `AuditLogDecorator` - Adds audit logging

**Usage:**
```java
ITicketService baseService = new BaseTicketService();
ITicketService signedService = new SignedQrDecorator(baseService);
ITicketService auditedService = new AuditLogDecorator(signedService);

String ticket = auditedService.generateTicket(booking);
```

---

### 10. Bridge Pattern (Structural)

**Location:** `bridge/` package

**Purpose:** Decouple abstraction from implementation

**Components:**

#### Notification Channels (Implementation)
- `NotificationChannel` - Channel interface
- `EmailChannel` - Email delivery
- `InAppChannel` - In-app notification

#### Notification Messages (Abstraction)
- `NotificationMessage` - Abstract message
- `NewEventMessage` - New event notification
- `EventUpdateMessage` - Event update notification
- `ReminderMessage` - Event reminder

**Usage:**
```java
NotificationChannel emailChannel = new EmailChannel();
NotificationMessage message = new NewEventMessage(emailChannel, "Tech Conference");
message.sendNotification("user@example.com");
```

---

### 11. Adapter Pattern (Structural)

**Location:** `adapter/` package

**Purpose:** Convert the interface of a class into another interface

**Components:**
- `IEmailService` - Target interface
- `SmtpEmailAdapter` - Adapts JavaMailSender to IEmailService

**Usage:**
```java
@Autowired
private IEmailService emailService;

emailService.sendEmail("user@example.com", "Welcome", "Welcome to our system!");
```

---

### 12. Memento Pattern (Behavioral)

**Location:** `memento/` package

**Purpose:** Capture and restore object state without violating encapsulation

**Components:**

#### Activity Memento
- `ActivityMemento` - Stores activity state
- `ActivityMementoFactory` - Creates and restores mementos
- `ActivityHistoryCaretaker` - Manages history

#### Booking Memento
- `BookingMemento` - Stores booking state
- `BookingMementoFactory` - Creates and restores mementos
- `BookingHistoryCaretaker` - Manages history

**Usage:**
```java
@Autowired
private ActivityHistoryCaretaker caretaker;

@Autowired
private ActivityMementoFactory mementoFactory;

// Save state
ActivityMemento memento = mementoFactory.createMemento(event);
caretaker.save(memento);

// Restore state
ActivityMemento savedState = caretaker.undo();
mementoFactory.restoreFromMemento(event, savedState);
```

---

## Pattern Categories Summary

### Creational Patterns (5)
1. Factory Pattern
2. Abstract Factory Pattern
3. Builder Pattern (2 implementations: Activity & Report)
4. Prototype Pattern

### Behavioral Patterns (5)
1. Command Pattern
2. Strategy Pattern
3. State Pattern
4. Chain of Responsibility Pattern (2 implementations: Request & Booking)
5. Memento Pattern (2 implementations: Activity & Booking)

### Structural Patterns (3)
1. Decorator Pattern
2. Bridge Pattern
3. Adapter Pattern

## Total: 12 Design Patterns

All patterns have been implemented according to the specifications in `patterns_to_use.md` and are based on the class diagrams from `Milestones/PM_3/Class Diagram/Before DP/`.

## Benefits

1. **Maintainability**: Code is organized into clear, single-responsibility classes
2. **Extensibility**: New features can be added without modifying existing code
3. **Testability**: Patterns promote loose coupling and dependency injection
4. **Reusability**: Common solutions are encapsulated in reusable components
5. **SOLID Principles**: All implementations follow SOLID design principles

## Integration with Existing Code

All design patterns have been implemented as standalone components that can be integrated with the existing services:
- EventService
- BookingService
- NotificationService
- ReportService
- AuthService

The patterns are Spring-managed beans and can be autowired as needed.
