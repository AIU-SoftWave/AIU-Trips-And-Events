# After DP Diagram to Code Mapping

This document maps the PlantUML class diagrams in `Milestones/PM_3/Class Diagram/After DP/` to the actual Java code implementation.

## Data Layer Mapping

**Diagram:** `Data_Layer.pu`

### Entities
| Diagram Class | Java Class | Location | Status |
|--------------|-----------|----------|---------|
| User | User.java | model/ | ✅ Exists |
| Activity (abstract) | Activity.java | model/ | ✅ Created |
| Event | Event.java | model/ | ✅ Updated |
| Trip | Trip.java | model/ | ✅ Created |
| Booking | Booking.java | model/ | ✅ Updated |
| Ticket | Ticket.java | model/ | ✅ Created |
| Notification | Notification.java | model/ | ✅ Updated |
| Report | Report.java | model/ | ✅ Created |
| Feedback | Feedback.java | model/ | ✅ Updated |
| ActivityMemento | - | - | ⏸️ Not implemented |
| BookingMemento | - | - | ⏸️ Not implemented |

### Enumerations
| Diagram Enum | Java Enum | Location | Status |
|-------------|-----------|----------|---------|
| UserRole | UserRole.java | enums/ | ✅ Exists |
| ActivityType | ActivityType.java | enums/ | ✅ Created |
| ActivityCategory | ActivityCategory.java | enums/ | ✅ Created |
| ActivityStatus | ActivityStatus.java | enums/ | ✅ Created |
| BookingStatus | BookingStatus.java | enums/ | ✅ Updated |
| NotificationType | NotificationType.java | enums/ | ✅ Created |
| ReportType | ReportType.java | enums/ | ✅ Created |
| ExportFormat | ExportFormat.java | enums/ | ✅ Created |
| EventType | EventType.java | enums/ | ✅ Exists |
| EventStatus | EventStatus.java | enums/ | ✅ Exists |
| PaymentMethod | PaymentMethod.java | enums/ | ✅ Exists |

## Repository Layer Mapping

**Diagram:** `Repository_Layer.pu` and `Model_Factory.pu`

| Diagram Interface | Java Interface | Location | Status |
|------------------|----------------|----------|---------|
| IBaseModel<T> | JpaRepository<T, ID> | Spring Data | ✅ Using Spring |
| IUserModel | UserRepository | repository/ | ✅ Exists |
| IActivityModel | ActivityRepository | repository/ | ✅ Created |
| IBookingModel | BookingRepository | repository/ | ✅ Updated |
| INotificationModel | NotificationRepository | repository/ | ✅ Exists |
| IReportModel | ReportRepository | repository/ | ✅ Created |
| IFeedbackModel | FeedbackRepository | repository/ | ✅ Updated |
| IModelFactory | - | - | ⏸️ Not implemented |
| ModelFactory | - | - | ⏸️ Not implemented |

**Note:** Spring Data JPA repositories serve the same purpose as the Repository pattern in the diagrams.

## Event Management Layer Mapping

**Diagram:** `Event_Management.pu`

### Service Interfaces
| Diagram Interface | Java Class | Location | Status |
|------------------|-----------|----------|---------|
| IActivityManagement | EventService | service/ | ✅ Partially (needs update) |
| INotificationSystem | NotificationService | service/ | ✅ Updated |

### Factory Pattern
| Diagram Class | Java Class | Location | Status |
|--------------|-----------|----------|---------|
| IActivityFactory | - | - | ⏸️ Not implemented |
| EventFactory | - | - | ⏸️ Not implemented |
| TripFactory | - | - | ⏸️ Not implemented |

### Builder Pattern
| Diagram Class | Java Class | Location | Status |
|--------------|-----------|----------|---------|
| IActivityBuilder | - | - | ⏸️ Not implemented |
| EventBuilder | - | - | ⏸️ Not implemented |
| TripBuilder | - | - | ⏸️ Not implemented |
| IActivityDirector | - | - | ⏸️ Not implemented |
| ActivityDirector | - | - | ⏸️ Not implemented |

### State Pattern
| Diagram Class | Java Class | Location | Status |
|--------------|-----------|----------|---------|
| ActivityState (interface) | - | - | ⏸️ Not implemented |
| ActivityLifecycle | - | - | ⏸️ Not implemented |
| UpcomingState | - | - | ⏸️ Not implemented |
| CompletedState | - | - | ⏸️ Not implemented |
| CancelledState | - | - | ⏸️ Not implemented |

### Memento Pattern
| Diagram Class | Java Class | Location | Status |
|--------------|-----------|----------|---------|
| ActivityHistoryCaretaker | - | - | ⏸️ Not implemented |
| ActivityMemento (from Data Layer) | - | - | ⏸️ Not implemented |

## Controller Layer Mapping

**Diagram:** `Controller.pu`

### Command Pattern
| Diagram Class | Java Class | Location | Status |
|--------------|-----------|----------|---------|
| IControllerCommand | - | - | ⏸️ Not implemented |
| ControllerCommandInvoker | - | - | ⏸️ Not implemented |
| RegisterCommand | AuthController | controller/ | ✅ Direct impl |
| LoginCommand | AuthController | controller/ | ✅ Direct impl |
| CreateEventCommand | EventController | controller/ | ✅ Direct impl |
| UpdateEventCommand | EventController | controller/ | ✅ Direct impl |
| DeleteEventCommand | EventController | controller/ | ✅ Direct impl |
| BookEventCommand | BookingController | controller/ | ✅ Direct impl |
| SendNotificationCommand | NotificationController | controller/ | ✅ Direct impl |
| GenerateReportCommand | ReportController | controller/ | ✅ Direct impl |

**Note:** Controllers currently implement functionality directly without the Command pattern wrapper.

### Chain of Responsibility
| Diagram Class | Java Class | Location | Status |
|--------------|-----------|----------|---------|
| RequestHandler (abstract) | - | - | ⏸️ Not implemented |
| AuthenticationHandler | - | - | ⏸️ Not implemented |
| AuthorizationHandler | - | - | ⏸️ Not implemented |
| ValidationHandler | - | - | ⏸️ Not implemented |
| RateLimitHandler | - | - | ⏸️ Not implemented |

**Note:** Security is currently handled by Spring Security filters.

## Booking & Ticketing Layer Mapping

**Diagram:** `Booking_Ticketing.pu`

### Service
| Diagram Class | Java Class | Location | Status |
|--------------|-----------|----------|---------|
| IBookingTicketingSystem | BookingService | service/ | ✅ Updated |
| BookingService | BookingService | service/ | ✅ Updated |
| ITicketService | - | - | ⏸️ Not implemented |

### Chain of Responsibility
| Diagram Class | Java Class | Location | Status |
|--------------|-----------|----------|---------|
| BookingHandler (abstract) | - | - | ⏸️ Not implemented |
| EligibilityHandler | - | - | ⏸️ Not implemented |
| CapacityHandler | - | - | ⏸️ Not implemented |
| PaymentHandler | - | - | ⏸️ Not implemented |

**Note:** Validation currently in BookingService methods directly.

### Strategy Pattern
| Diagram Class | Java Class | Location | Status |
|--------------|-----------|----------|---------|
| PricingStrategy (interface) | - | - | ⏸️ Not implemented |
| StandardPricingStrategy | - | - | ⏸️ Not implemented |
| EarlyBirdPricingStrategy | - | - | ⏸️ Not implemented |
| BulkGroupDiscountStrategy | - | - | ⏸️ Not implemented |

### Decorator Pattern
| Diagram Class | Java Class | Location | Status |
|--------------|-----------|----------|---------|
| ITicketService | - | - | ⏸️ Not implemented |
| BaseTicketService | - | - | ⏸️ Not implemented |
| TicketServiceDecorator (abstract) | - | - | ⏸️ Not implemented |
| SignedQrDecorator | - | - | ⏸️ Not implemented |
| AuditLogDecorator | - | - | ⏸️ Not implemented |

### Memento Pattern
| Diagram Class | Java Class | Location | Status |
|--------------|-----------|----------|---------|
| BookingHistoryCaretaker | - | - | ⏸️ Not implemented |
| BookingMemento (from Data Layer) | - | - | ⏸️ Not implemented |

## Notification Layer Mapping

**Diagram:** `Notification.pu`

### Service
| Diagram Class | Java Class | Location | Status |
|--------------|-----------|----------|---------|
| INotificationSystem | NotificationService | service/ | ✅ Updated |
| NotificationService | NotificationService | service/ | ✅ Updated |

### Bridge Pattern
| Diagram Class | Java Class | Location | Status |
|--------------|-----------|----------|---------|
| NotificationChannel (interface) | - | - | ⏸️ Not implemented |
| EmailChannel | - | - | ⏸️ Not implemented |
| InAppChannel | - | - | ⏸️ Not implemented |
| NotificationMessage (abstract) | - | - | ⏸️ Not implemented |
| NewEventMessage | - | - | ⏸️ Not implemented |
| EventUpdateMessage | - | - | ⏸️ Not implemented |
| ReminderMessage | - | - | ⏸️ Not implemented |

### Adapter Pattern
| Diagram Class | Java Class | Location | Status |
|--------------|-----------|----------|---------|
| IEmailService | - | - | ⏸️ Not implemented |
| SmtpEmailAdapter | - | - | ⏸️ Not implemented |
| JavaMailSender | JavaMailSender | External | ✅ Spring Email |

## Reports & Analytics Layer Mapping

**Diagram:** `Reports_Analytics.pu`

### Service
| Diagram Class | Java Class | Location | Status |
|--------------|-----------|----------|---------|
| IReportsAnalytics | ReportService | service/ | ✅ Exists |
| ReportService | ReportService | service/ | ✅ Exists |

### Builder Pattern
| Diagram Class | Java Class | Location | Status |
|--------------|-----------|----------|---------|
| ReportBuilder (abstract) | - | - | ⏸️ Not implemented |
| PdfReportBuilder | - | - | ⏸️ Not implemented |
| CsvReportBuilder | - | - | ⏸️ Not implemented |
| IReportDirector | - | - | ⏸️ Not implemented |
| ReportDirector | - | - | ⏸️ Not implemented |

## User Management Mapping

**Diagram:** `User_Management_.pu`

### Service
| Diagram Class | Java Class | Location | Status |
|--------------|-----------|----------|---------|
| IAuthenticationUserManagement | AuthService | service/ | ✅ Exists |
| UserManagementService | AuthService | service/ | ✅ Exists |
| IAuthService | JwtUtil | security/ | ✅ Exists |

## Summary

### Implemented ✅
- All core entity models (Activity hierarchy, Event, Trip, Report, Ticket, etc.)
- All required enumerations
- All repository interfaces (using Spring Data JPA)
- Basic service layer (EventService, BookingService, NotificationService, etc.)
- Basic controller layer (EventController, BookingController, etc.)
- Database seeder with new model structure

### Partially Implemented ⚠️
- Service interfaces (functionality exists but not all through explicit interfaces)
- Security (Spring Security instead of Chain of Responsibility pattern)

### Not Implemented ⏸️
- Design pattern implementations:
  - Factory pattern classes
  - Builder pattern classes
  - Strategy pattern classes
  - State pattern classes
  - Memento pattern classes
  - Decorator pattern classes
  - Bridge pattern classes
  - Adapter pattern classes (using Spring's JavaMailSender directly)
  - Command pattern wrapper classes
  - Chain of Responsibility handler classes

## Rationale

The implementation focuses on:
1. **Core Data Model**: Complete implementation of all entities and enums per Data_Layer.pu
2. **Database Seeder**: Fully functional seeder using new model structure
3. **Backward Compatibility**: Existing services and controllers work with new models
4. **Minimal Changes**: Following the instruction to make minimal modifications

Design patterns are documented in the diagrams but not all fully implemented as classes because:
- The problem statement emphasized fixing the database seeder as the primary goal
- Spring Framework provides built-in implementations for many patterns
- Existing code works with the new entity structure through backward compatibility
- Full pattern implementation can be done incrementally without breaking changes
