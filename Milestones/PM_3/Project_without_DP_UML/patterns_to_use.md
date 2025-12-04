# Design Patterns Quick Reference

## 1. Factory Pattern

**Location:** Repository Layer  
**Apply to:**

- Create `IModelFactory` interface → `ModelFactory` implementation
- Register: `UserModel`, `ActivityModel`, `BookingModel`, `NotificationModel`, `ReportModel`, `FeedbackModel`

---

## 2. Abstract Factory Pattern

**Location:** Activity Management  
**Apply to:**

- Create `IActivityFactory` interface
- Implement `EventFactory` and `TripFactory`
- Used by `ActivityManagementService` to create Events/Trips

---

## 3. Builder Pattern

### Activity Builder

**Location:** Activity Management  
**Apply to:**

- Create `IActivityBuilder` interface → `ActivityDirector` class
- Implement `EventBuilder` and `TripBuilder`
- Used by factories to construct complex Activity objects

### Report Builder

**Location:** Reports & Analytics  
**Apply to:**

- Create `ReportBuilder` abstract class → `ReportDirector` class
- Implement `PdfReportBuilder` and `CsvReportBuilder`
- Used by `ReportService` to build reports

---

## 4. Prototype Pattern

**Location:** Activity Management  
**Apply to:**

- Create `IPrototype<T>` interface
- Implement in `Event` and `Trip` classes with `clone()` method
- Used in `IActivityFactory.clonePrototype()`

---

## 5. Command Pattern

**Location:** Controller Layer  
**Apply to:**

- Create `IControllerCommand` interface → `ControllerCommandInvoker` class
- Implement commands: `RegisterCommand`, `LoginCommand`, `CreateEventCommand`, `UpdateEventCommand`, `DeleteEventCommand`, `BookEventCommand`, `SendNotificationCommand`, `GenerateReportCommand`
- Used by `SystemController` for request routing

---

## 6. Strategy Pattern

**Location:** Booking & Ticketing  
**Apply to:**

- Create `PricingStrategy` interface
- Implement: `StandardPricingStrategy`, `EarlyBirdPricingStrategy`, `BulkGroupDiscountStrategy`
- Used by `BookingService` for dynamic pricing

---

## 7. State Pattern

**Location:** Activity Management  
**Apply to:**

- Create `ActivityState` interface → `ActivityLifecycle` context
- Implement: `UpcomingState`, `CompletedState`, `CancelledState`
- Used by `Activity` to manage lifecycle transitions

---

## 8. Chain of Responsibility Pattern

### Request Handler Chain

**Location:** Controller Layer  
**Apply to:**

- Create `RequestHandler` abstract class
- Implement: `AuthenticationHandler`, `AuthorizationHandler`, `ValidationHandler`, `RateLimitHandler`
- Used by `SystemController` for request preprocessing

### Booking Validation Chain

**Location:** Booking & Ticketing  
**Apply to:**

- Create `BookingHandler` abstract class
- Implement: `EligibilityHandler`, `CapacityHandler`, `PaymentHandler`
- Used by `BookingService` for booking validation

---

## 9. Decorator Pattern

**Location:** Booking & Ticketing  
**Apply to:**

- Create `ITicketService` interface → `BaseTicketService` implementation
- Create `TicketServiceDecorator` abstract class
- Implement: `SignedQrDecorator`, `AuditLogDecorator`
- Used by `BookingService` to add ticket features dynamically

---

## 10. Bridge Pattern

**Location:** Notification Component  
**Apply to:**

- Create `NotificationChannel` interface → Implement: `EmailChannel`, `InAppChannel`
- Create `NotificationMessage` abstract class → Implement: `NewEventMessage`, `EventUpdateMessage`, `ReminderMessage`
- Used by `NotificationService` to decouple channels from messages

---

## 11. Adapter Pattern

**Location:** Notification Component  
**Apply to:**

- Create `IEmailService` interface
- Implement `SmtpEmailAdapter` to wrap `JavaMailSender` (third-party)
- Used by `EmailChannel` for email integration

---

## 12. Memento Pattern

### Activity Memento

**Location:** Activity Management + Data Layer  
**Apply to:**

- Create `ActivityHistoryCaretaker` class
- Uses `ActivityMemento` and `ActivityMementoFactory` from Data Layer
- Used by `ActivityManagementService` to save/restore activity state

### Booking Memento

**Location:** Booking & Ticketing + Data Layer  
**Apply to:**

- Create `BookingHistoryCaretaker` class
- Uses `BookingMemento` and `BookingMementoFactory` from Data Layer
- Used by `BookingService` to save/restore booking state

---

## Implementation Priority

**Phase 1:** Factory, Command, Chain of Responsibility  
**Phase 2:** Builder, Abstract Factory, Strategy, State  
**Phase 3:** Bridge, Adapter, Decorator, Prototype, Memento
