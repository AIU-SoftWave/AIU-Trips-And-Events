# Pattern to PlantUML Class Diagram Mapping

This document maps each implemented design pattern to the relevant PlantUML class diagrams from `Milestones/PM_3/Class Diagram/Before DP/`.

## Source Diagrams

All patterns were derived from these PlantUML diagrams:
- `Complete_System.pu` - Full system architecture
- `Controller.pu` - Controller layer
- `User_Management_.pu` - User management component  
- `Event_Management.pu` - Event/activity management
- `Booking_Ticketing.pu` - Booking and ticketing
- `Notification.pu` - Notification component
- `Reports_Analytics.pu` - Reports and analytics
- `Data_Layer.pu` - Domain models and entities

---

## Pattern Mappings

### 1. Factory Pattern
**Source:** `Data_Layer.pu`, `Complete_System.pu`  
**Classes Referenced:**
- `User` (line 15-24 in Data_Layer.pu)
- `Event` (line 27-40 in Data_Layer.pu)
- `Booking` (line 42-49 in Data_Layer.pu)
- `Notification` (line 59-66 in Data_Layer.pu)
- `Feedback` (line 75-82 in Data_Layer.pu)

**Implementation:** Created `IModelFactory` and `ModelFactory` to centralize creation of these domain models.

---

### 2. Abstract Factory Pattern
**Source:** `Event_Management.pu`  
**Classes Referenced:**
- `EventManagementService` (line 28-36 in Event_Management.pu)
- `Event` with `EventType` enum (line 53-66, 68-74 in Event_Management.pu)

**Implementation:** Created `IActivityFactory`, `EventFactory`, and `TripFactory` based on the Event entity and its type differentiation.

---

### 3. Builder Pattern - Activity
**Source:** `Event_Management.pu`  
**Classes Referenced:**
- `Event` entity with multiple properties (line 53-66)
- Complex construction requirements for events/trips

**Implementation:** Created `IActivityBuilder`, `EventBuilder`, `TripBuilder`, and `ActivityDirector` to handle complex Event object construction.

---

### 4. Builder Pattern - Report
**Source:** `Reports_Analytics.pu`  
**Classes Referenced:**
- `ReportService` (line 27-36)
- `Report` entity (line 60-65)
- `ExportFormat` enum (line 83-86)

**Implementation:** Created `ReportBuilder`, `PdfReportBuilder`, `CsvReportBuilder`, and `ReportDirector` for flexible report generation.

---

### 5. Prototype Pattern
**Source:** `Event_Management.pu`, `patterns_to_use.md`  
**Classes Referenced:**
- `Event` entity (line 53-66)

**Implementation:** Added `IPrototype<T>` interface and implemented `clone()` method in Event model for creating event templates.

---

### 6. Command Pattern
**Source:** `Controller.pu`, `Complete_System.pu`  
**Classes Referenced:**
- `SystemController` (line 14-31 in Controller.pu)
- Controller methods: `register`, `login`, `createEvent`, `bookEvent` (line 22-30)
- Service interfaces used by controller (line 34-68)

**Implementation:** Created `IControllerCommand`, `ControllerCommandInvoker`, and concrete commands (`RegisterCommand`, `LoginCommand`, `CreateEventCommand`, `BookEventCommand`).

---

### 7. Strategy Pattern
**Source:** `Booking_Ticketing.pu`, `patterns_to_use.md`  
**Classes Referenced:**
- `BookingService` (line 27-35)
- `Booking` with price-related fields (line 57-63)

**Implementation:** Created `PricingStrategy` interface with three implementations: `StandardPricingStrategy`, `EarlyBirdPricingStrategy`, and `BulkGroupDiscountStrategy`.

---

### 8. State Pattern
**Source:** `Event_Management.pu`  
**Classes Referenced:**
- `Event` with `EventStatus` enum (line 75-79: UPCOMING, COMPLETED, CANCELLED)
- `EventManagementService` managing event lifecycle (line 28-36)

**Implementation:** Created `ActivityState`, `ActivityLifecycle`, `UpcomingState`, `CompletedState`, and `CancelledState` to manage event lifecycle transitions.

---

### 9. Chain of Responsibility - Request Handler
**Source:** `Controller.pu`, `patterns_to_use.md`  
**Classes Referenced:**
- `SystemController.handleRequest()` (line 21)
- `SystemController.authenticate()` (line 22)

**Implementation:** Created `RequestHandler` chain with `AuthenticationHandler`, `AuthorizationHandler`, `ValidationHandler`, and `RateLimitHandler`.

---

### 10. Chain of Responsibility - Booking Handler
**Source:** `Booking_Ticketing.pu`, `patterns_to_use.md`  
**Classes Referenced:**
- `BookingService.bookEvent()` (line 32)
- Validation requirements for booking creation

**Implementation:** Created `BookingHandler` chain with `EligibilityHandler`, `CapacityHandler`, and `PaymentHandler`.

---

### 11. Decorator Pattern
**Source:** `Booking_Ticketing.pu`, `Complete_System.pu`  
**Classes Referenced:**
- `ITicketService` interface (line 51-54 in Complete_System.pu)
- `BookingService` using ticket service (line 29 in Booking_Ticketing.pu)
- `Ticket` entity (line 65-71 in Booking_Ticketing.pu)

**Implementation:** Created `ITicketService`, `BaseTicketService`, `TicketServiceDecorator`, `SignedQrDecorator`, and `AuditLogDecorator` for extensible ticket features.

---

### 12. Bridge Pattern
**Source:** `Notification.pu`, `patterns_to_use.md`  
**Classes Referenced:**
- `NotificationService` (line 26-33)
- `IEmailService` interface (line 46-48)
- `NotificationType` enum (line 61-65: NEW_EVENT, EVENT_UPDATE, REMINDER)

**Implementation:** Created bridge between notification channels (`NotificationChannel`, `EmailChannel`, `InAppChannel`) and messages (`NotificationMessage`, `NewEventMessage`, `EventUpdateMessage`, `ReminderMessage`).

---

### 13. Adapter Pattern
**Source:** `Notification.pu`, `User_Management_.pu`, `patterns_to_use.md`  
**Classes Referenced:**
- `IEmailService` interface (line 50-52 in User_Management_.pu, line 46-48 in Notification.pu)
- Third-party email service integration requirement

**Implementation:** Created `IEmailService` interface and `SmtpEmailAdapter` to wrap JavaMailSender (third-party service).

---

### 14. Memento Pattern - Activity
**Source:** `Event_Management.pu`, `Data_Layer.pu`, `patterns_to_use.md`  
**Classes Referenced:**
- `Event` entity with all state fields (line 27-40 in Data_Layer.pu)
- `EventManagementService.updateEvent()` (line 32 in Event_Management.pu)

**Implementation:** Created `ActivityMemento`, `ActivityMementoFactory`, and `ActivityHistoryCaretaker` for saving/restoring event state.

---

### 15. Memento Pattern - Booking
**Source:** `Booking_Ticketing.pu`, `Data_Layer.pu`, `patterns_to_use.md`  
**Classes Referenced:**
- `Booking` entity with state fields (line 42-49 in Data_Layer.pu)
- Booking status transitions in `BookingService`

**Implementation:** Created `BookingMemento`, `BookingMementoFactory`, and `BookingHistoryCaretaker` for saving/restoring booking state.

---

## Verification Checklist

- [x] All patterns reference specific classes from PlantUML diagrams
- [x] Pattern locations match `patterns_to_use.md` specifications
- [x] Implementations follow class relationships from diagrams
- [x] Enums and types match PlantUML definitions
- [x] Service layer interactions preserved
- [x] Repository layer patterns integrated
- [x] SOLID principles maintained from original design

## Notes

1. All implementations are based **exclusively** on the "Before DP" PlantUML diagrams as specified in the requirements.
2. No changes were made to existing PlantUML diagrams - only new Java implementations were created.
3. All patterns are Spring-managed beans compatible with the existing architecture.
4. Patterns can be integrated with existing services without breaking changes.
