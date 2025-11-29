# Behavioral Patterns Checklist – AIU Trips & Events System

Use this checklist to ensure each required behavioral pattern is identified and (if missing) supported by appropriate functionality in the system.

---

- [ ] **Command Pattern**

  - [ ] Define a common `ICommand` interface with `execute()` (and optionally `undo()`).
  - [ ] Implement concrete commands (e.g., `RegisterUserCommand`, `LoginCommand`, `CreateEventCommand`, `BookEventCommand`, `SendNotificationCommand`, `GenerateReportCommand`).
  - [ ] Make `SystemController` act as an invoker that maps `HttpRequest` to `ICommand` objects.
  - [ ] Ensure each command goes through the `RequestHandler` chain before `execute()`.

- [ ] **Mediator Pattern**

  - [ ] Treat `ApplicationFacade` as a mediator coordinating `IAuthenticationUserManagement`, `IActivityManagement`, `IBookingTicketingSystem`, `INotificationSystem`, and `IReportsAnalytics`.
  - [ ] Centralize cross-module workflows (e.g., create event → send notifications → update analytics) inside the facade.
  - [ ] (Optional) Introduce a `IDomainEventMediator` for publishing and handling domain events between services.

- [ ] **Memento Pattern**

  - [ ] Create `ActivityMemento` and `BookingMemento` capturing `Activity` and `Booking` state.
  - [ ] Add caretakers (draft/history components) to save and restore these mementos.
  - [ ] Support undo for organizers editing activities/trips using `ActivityMemento`.
  - [ ] Allow rollback of bookings when payment or validation fails using `BookingMemento`.

- [ ] **Observer Pattern**

  - [ ] Define subjects for key domain changes (e.g., `ActivityManagementService`, `BookingService`).
  - [ ] Implement observers such as `NotificationService` and `ReportService` that react to these changes.
  - [ ] Notify observers automatically when activities are updated/cancelled or bookings are created/cancelled.
  - [ ] Use observers to trigger messages like `NewEventMessage`, `EventUpdateMessage`, and `ReminderMessage` via the notification component.

- [ ] **Interpreter Pattern**

  - [ ] Define an expression hierarchy (e.g., `Expression`, `AndExpression`, `OrExpression`, `NotExpression`, `TypeExpression`, `CategoryExpression`, `PriceExpression`, `DateRangeExpression`).
  - [ ] Use the interpreter to evaluate complex search filters for events/trips beyond simple DTO fields.
  - [ ] (Optional) Use the same interpreter idea to express eligibility rules (e.g., GPA, major, year) for booking specific activities.

- [ ] **State Pattern**

  - [ ] Introduce `BookingState` (e.g., `PendingState`, `ConfirmedState`, `CancelledState`) to replace logic based on `BookingStatus`.
  - [ ] Introduce `ActivityState` (e.g., `UpcomingState`, `CompletedState`, `CancelledState`) for `ActivityStatus`.
  - [ ] Move state-specific behavior (confirm/cancel booking, publish/cancel event, close feedback) into these state classes.
  - [ ] Enforce valid transitions and trigger side effects when entering particular states (e.g., send notifications when an activity is cancelled).

- [ ] **Strategy Pattern**

  - [ ] Use `NotificationChannel` (`EmailChannel`, `SmsChannel`, `PushChannel`) explicitly as strategies for sending notifications.
  - [ ] Select the notification strategy at runtime based on user preferences or notification type.
  - [ ] Define `PricingStrategy` implementations (`StandardPricingStrategy`, `EarlyBirdPricingStrategy`, `BulkGroupDiscountStrategy`, etc.) for activity/trip pricing.
  - [ ] Configure each activity/trip with the appropriate pricing strategy.

- [ ] **Null Object Pattern**

  - [ ] Implement `NullNotificationChannel` that implements `NotificationChannel` but performs a no-op on `send`.
  - [ ] Use `NullNotificationChannel` when notifications are disabled or a user has opted out, instead of using `null`.
  - [ ] (Optional) Implement a `NullAuthService` for testing environments to avoid `null` checks around authentication.

- [ ] **Template Method Pattern**

  - [ ] Define template methods in `BaseRepository` for CRUD workflows (e.g., `saveTemplate`, `findByIdTemplate`) that handle validation, logging, and error handling.
  - [ ] Let concrete repositories implement only the variable steps (`doSave`, `doFindById`, etc.).
  - [ ] Ensure all repositories share the same template to keep cross-cutting concerns consistent.

- [ ] **Object Authenticator Pattern**
  - [ ] Define an `IAuthenticator` interface responsible for `authenticate(HttpRequest)` and `isAuthorized(AuthContext, permission)`.
  - [ ] Implement authenticators such as `JwtAuthenticator`, `SessionAuthenticator`, or `ExternalProviderAuthenticator`.
  - [ ] Make `AuthenticationHandler` depend on `IAuthenticator` instead of embedding authentication logic.
  - [ ] Reuse authenticator objects across controllers/services and align them with `SecurityValidator` in repository proxies where needed.
