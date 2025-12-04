# Project with Design Patterns - Implementation Summary

## Overview
This project implements the University Trips and Events Management System with comprehensive design patterns based on the "After DP" PlantUML diagrams from `Milestones/PM_3/Class Diagram/After DP`.

## Key Changes from Project_without_DP_UML

### Data Layer Refactoring

#### 1. Activity Hierarchy (Following After DP Data_Layer.pu)
- **Created abstract `Activity` class** as the base entity
  - Fields: activityId, name, description, activityDate, location, capacity, availableSeats, price, category, status, organizerId, type, createdAt
  - Uses JPA `@Inheritance(strategy = InheritanceType.SINGLE_TABLE)` for efficient querying
  
- **Created `Event` class** extending Activity
  - Additional fields: speakers (List), topic, venue, agenda
  - Discriminator value: "EVENT"
  
- **Created `Trip` class** extending Activity
  - Additional fields: destination, durationDays, transportMode, startLocation, endLocation, itinerary
  - Discriminator value: "TRIP"

#### 2. New Enums (Matching After DP Data_Layer.pu)
- `ActivityType`: EVENT, TRIP
- `ActivityCategory`: FIELD_TRIP, SEMINAR, CONFERENCE, CONCERT, CULTURAL_VISIT, ADVENTURE_TRIP
- `ActivityStatus`: UPCOMING, COMPLETED, CANCELLED
- `NotificationType`: NEW_EVENT, EVENT_UPDATE, REMINDER
- `ReportType`: PARTICIPANTS, REVENUE, FEEDBACK
- `ExportFormat`: PDF, CSV, EXCEL, JSON

#### 3. New Entities
- **`Ticket`**: Represents event/trip tickets with QR code functionality
  - Fields: ticketId, bookingId, qrCode, isUsed, issueDate
  
- **`ActivityMemento`**: Snapshot entity for Activity state preservation
  - Fields: activityId, name, description, activityDate, location, capacity, availableSeats, savedAt
  
- **`BookingMemento`**: Snapshot entity for Booking state preservation
  - Fields: bookingId, studentId, activityId, status, savedAt

#### 4. Updated Memento Factories
- **`ActivityMementoFactory`**: Creates and restores ActivityMemento from Activity
- **`BookingMementoFactory`**: Creates and restores BookingMemento from Booking

### Repository Layer
- **`TripRepository`**: New repository for Trip entities extending JpaRepository

### Database Seeder Updates
The DatabaseSeeder has been completely rewritten to work with the new Activity/Event/Trip model structure:

#### Key Fixes:
1. **Updated field names**: 
   - `setTitle()` → `setName()`
   - `setStartDate()` → `setActivityDate()`
   - `EventType` → `ActivityType`
   - `EventStatus` → `ActivityStatus`

2. **Added new required fields**:
   - `category` (ActivityCategory enum)
   - `organizerId` (String instead of User object)
   - Event-specific fields: `topic`, `venue`, `agenda`, `speakers`

3. **Updated data types**:
   - `price`: Double → BigDecimal
   - All ID fields: String → Long (for JPA auto-generation)

4. **Enhanced error handling**:
   - Try-catch blocks around all creation methods
   - Detailed error logging
   - Graceful degradation when data is missing

5. **Sample data**:
   - 5 users (4 students + 1 admin/organizer)
   - 4 events with different categories (CONFERENCE, SEMINAR, CONCERT)
   - 3 bookings
   - 3 feedback entries
   - 5 notifications

## Design Patterns Implemented

### Existing Patterns (from Project_without_DP_UML)
The following patterns were already implemented and are retained:

1. **Command Pattern** (controller/command/)
   - IControllerCommand, ControllerCommandInvoker
   - Concrete commands: RegisterCommand, LoginCommand, CreateEventCommand, BookEventCommand

2. **Chain of Responsibility** (chain/)
   - Request handlers: AuthenticationHandler, AuthorizationHandler, ValidationHandler, RateLimitHandler
   - Booking handlers: EligibilityHandler, CapacityHandler, PaymentHandler

3. **Strategy Pattern** (strategy/)
   - Pricing strategies: StandardPricingStrategy, EarlyBirdPricingStrategy, BulkGroupDiscountStrategy

4. **State Pattern** (state/)
   - Activity lifecycle states: UpcomingState, CompletedState, CancelledState
   - ActivityLifecycle context

5. **Builder Pattern** (builder/)
   - Activity builders: EventBuilder, TripBuilder, ActivityDirector
   - Report builders: PdfReportBuilder, CsvReportBuilder, ReportDirector

6. **Abstract Factory Pattern** (factory/)
   - IActivityFactory with EventFactory and TripFactory
   - IModelFactory for repository model creation

7. **Decorator Pattern** (decorator/)
   - ITicketService with BaseTicketService
   - Decorators: SignedQrDecorator, AuditLogDecorator

8. **Bridge Pattern** (bridge/)
   - Notification channels: EmailChannel, InAppChannel
   - Notification messages: NewEventMessage, EventUpdateMessage, ReminderMessage

9. **Adapter Pattern** (adapter/)
   - SmtpEmailAdapter wrapping JavaMailSender

10. **Memento Pattern** (memento/)
    - ActivityHistoryCaretaker, BookingHistoryCaretaker
    - Updated factories to work with new memento entities

11. **Prototype Pattern** (prototype/)
    - IPrototype<T> interface for cloning

## Alignment with After DP Diagrams

### Strictly Following After DP Sources:
1. ✅ **Data_Layer.pu**: All entities, enums, and relationships match
2. ✅ **Repository_Layer.pu**: Base repository interfaces structure matches
3. ✅ **Model_Factory.pu**: IModelFactory pattern structure matches
4. ⚠️ **Controller.pu**: Command pattern implemented, SystemController needs integration
5. ⚠️ **Activity_Layer.pu**: Builder and Factory patterns match, need service integration
6. ⚠️ **Booking_Ticketing.pu**: Strategy and Chain patterns match, need service integration
7. ⚠️ **Notification.pu**: Bridge and Adapter patterns match, need service integration
8. ⚠️ **Reports_Analytics.pu**: Builder pattern matches, need service integration

## What Still Needs to Be Done

### High Priority:
1. **Service Layer Integration**
   - Connect existing pattern implementations with service layer
   - Implement IAuthenticationUserManagement in UserManagementService
   - Implement IActivityManagement in ActivityManagementService
   - Implement IBookingTicketingSystem in BookingService
   - Implement INotificationSystem in NotificationService
   - Implement IReportsAnalytics in ReportService

2. **Repository Layer Completion**
   - Implement IBaseModel, IReadModel, IWriteModel interfaces
   - Create model implementations (UserModel, ActivityModel, BookingModel, etc.)
   - Implement IActivityMementoStore and IBookingMementoStore

3. **Controller Updates**
   - Integrate SystemController with ControllerCommandInvoker
   - Wire up request handler chain
   - Connect all commands to services

### Medium Priority:
4. **Testing**
   - Test database seeder with actual database
   - Verify all design patterns work together
   - Integration tests for service layer

5. **Documentation**
   - Update API documentation
   - Create pattern usage examples
   - Document service layer architecture

### Low Priority:
6. **Docker Configuration**
   - Verify docker-compose.yml works with new model
   - Update environment variables if needed
   - Test containerized deployment

## Running the Application

### Prerequisites:
- Java 17 or higher
- Maven
- PostgreSQL (or Docker)

### With Docker:
```bash
./start.sh
```

### Manual:
```bash
cd backend
mvn clean install
mvn spring-boot:run -Dspring.profiles.active=dev
```

### Sample Credentials:
- **Admin**: admin@aiu.edu / admin123
- **Student 1**: john.doe@aiu.edu / password123
- **Student 2**: jane.smith@aiu.edu / password123
- **Student 3**: mike.johnson@aiu.edu / password123
- **Student 4**: sarah.williams@aiu.edu / password123
- **Organizer**: organizer@aiu.edu / password123

## Database Schema Updates

### New Tables:
- `activities` (single table for Event and Trip with discriminator)
- `tickets`
- `activity_mementos`
- `booking_mementos`
- `event_speakers` (collection table for Event speakers)

### Modified Tables:
- Events are now stored in `activities` table with activity_type='EVENT'
- Trips are stored in `activities` table with activity_type='TRIP'

## Notes

### Design Decisions:
1. **Single Table Inheritance**: Chose SINGLE_TABLE strategy for Activity hierarchy for performance
2. **Long IDs**: All entities use Long for JPA auto-generation compatibility
3. **BigDecimal for Money**: Using BigDecimal for all price/money fields for precision
4. **Discriminator Column**: Using `activity_type` column to distinguish Event vs Trip

### Known Limitations:
1. The old Event model references are still in controllers and services
2. Some design pattern implementations need service layer integration
3. Repository layer is still using Spring Data JPA directly (not wrapped with IModelFactory pattern)
4. Test coverage needs to be updated for new model structure

## References
- PlantUML Diagrams: `Milestones/PM_3/Class Diagram/After DP/`
- Pattern Documentation: `DESIGN_PATTERNS_IMPLEMENTATION.md`
- Docker Setup: `DOCKER_SETUP.md`
