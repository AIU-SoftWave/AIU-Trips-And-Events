# Project with Design Patterns (After DP)

## Quick Start

This version of the project has been refactored to strictly follow the PlantUML class diagrams in `Milestones/PM_3/Class Diagram/After DP/`.

### Running with Docker (Recommended)

```bash
./start.sh
```

### Running Manually

```bash
cd backend
mvn spring-boot:run -Dspring.profiles.active=docker
```

## What's New

### Major Refactoring

The codebase has been updated to align with the "After DP" (After Design Patterns) architecture:

1. **Activity-based Model**: Events and Trips now inherit from an abstract `Activity` class
2. **New Enums**: ActivityType, ActivityCategory, ActivityStatus (replacing old EventType, EventStatus)
3. **Memento Entities**: Separate entities for ActivityMemento and BookingMemento
4. **Ticket System**: New Ticket entity for QR code-based ticketing
5. **Fixed Database Seeder**: Now works with the new model structure

### Database Schema Changes

The most significant change is the migration from a single `events` table to an `activities` table using Single Table Inheritance:

**Before:**
```
events (id, title, description, type, ...)
```

**After:**
```
activities (activity_id, name, description, activity_type, category, ...)
  - activity_type = 'EVENT' → Event with speakers, topic, venue, agenda
  - activity_type = 'TRIP' → Trip with destination, transport_mode, itinerary
```

## Design Patterns Implemented

All design patterns from the After DP diagrams are implemented:

### Creational Patterns
- ✅ **Abstract Factory** (IActivityFactory → EventFactory, TripFactory)
- ✅ **Builder** (ActivityBuilder, ReportBuilder with Directors)
- ✅ **Prototype** (IPrototype for Activity cloning)
- ✅ **Factory** (IModelFactory for repository models)

### Structural Patterns
- ✅ **Adapter** (SmtpEmailAdapter for JavaMailSender)
- ✅ **Bridge** (Notification channels and messages)
- ✅ **Decorator** (Ticket service decorators)

### Behavioral Patterns
- ✅ **Chain of Responsibility** (Request handlers, Booking handlers)
- ✅ **Command** (Controller commands)
- ✅ **Memento** (Activity and Booking state preservation)
- ✅ **State** (Activity lifecycle states)
- ✅ **Strategy** (Pricing strategies)

## Testing the Database Seeder

After starting the application, the database will be automatically seeded with:

- 5 users (4 students + 1 admin)
- 4 events (Conference, Career Fair, Workshop, Cultural Festival)
- 3 bookings
- 3 feedback entries
- 5 notifications

### Sample Credentials

| Role      | Email                | Password    |
|-----------|---------------------|-------------|
| Admin     | admin@aiu.edu       | admin123    |
| Student   | john.doe@aiu.edu    | password123 |
| Student   | jane.smith@aiu.edu  | password123 |
| Student   | mike.johnson@aiu.edu| password123 |
| Student   | sarah.williams@aiu.edu| password123 |
| Organizer | organizer@aiu.edu   | password123 |

## Architecture Overview

```
┌─────────────────────────────────────────────────────────┐
│                  Controller Layer                        │
│  (Command Pattern + Chain of Responsibility)            │
│  SystemController → ControllerCommandInvoker             │
│                  ↓ delegates to                          │
│            [Request Handler Chain]                       │
│            ↓ if valid, execute                          │
│       [IControllerCommand implementations]               │
└─────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────┐
│                   Service Layer                          │
│  - UserManagementService (IAuthenticationUserManagement) │
│  - ActivityManagementService (IActivityManagement)       │
│  - BookingService (IBookingTicketingSystem)              │
│  - NotificationService (INotificationSystem)             │
│  - ReportService (IReportsAnalytics)                     │
└─────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────┐
│                Repository Layer                          │
│  IModelFactory → UserModel, ActivityModel, etc.          │
│  (Using JPA Repositories currently)                      │
└─────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────┐
│                    Data Layer                            │
│  - Activity (abstract)                                   │
│    - Event (speakers, topic, venue, agenda)              │
│    - Trip (destination, transport, itinerary)            │
│  - User, Booking, Ticket, Notification, Feedback, Report │
│  - ActivityMemento, BookingMemento                       │
└─────────────────────────────────────────────────────────┘
```

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user

### Events/Trips (Activities)
- `GET /api/events` - List all events/trips
- `POST /api/events` - Create new event/trip (Admin only)
- `GET /api/events/{id}` - Get event/trip details
- `PUT /api/events/{id}` - Update event/trip (Admin only)
- `DELETE /api/events/{id}` - Delete event/trip (Admin only)

### Bookings
- `POST /api/bookings` - Create booking
- `GET /api/bookings` - List user's bookings
- `GET /api/bookings/{id}` - Get booking details
- `PUT /api/bookings/{id}/cancel` - Cancel booking

### Feedback
- `POST /api/feedback` - Submit feedback
- `GET /api/feedback/event/{eventId}` - Get event feedback

### Notifications
- `GET /api/notifications` - List notifications
- `PUT /api/notifications/{id}/read` - Mark as read

## PlantUML Diagram References

All implementations strictly follow these diagrams:

1. `Controller.pu` - Controller layer with Command pattern
2. `User_Management_.pu` - User authentication and management
3. `Event_Management.pu` - Activity management (now Activity_Layer.pu concepts)
4. `Booking_Ticketing.pu` - Booking with Strategy and Chain patterns
5. `Notification.pu` - Bridge and Adapter patterns
6. `Reports_Analytics.pu` - Report generation with Builder pattern
7. `Data_Layer.pu` - Complete entity model with Activity hierarchy
8. `Repository_Layer.pu` - Repository interfaces and implementations
9. `Model_Factory.pu` - Model factory pattern for repositories

## Troubleshooting

### Database Connection Issues
Check `backend/src/main/resources/application-docker.properties` for database configuration.

### Seeder Not Running
The seeder only runs in `dev` or `docker` profiles. Make sure you're using:
```bash
mvn spring-boot:run -Dspring.profiles.active=docker
```

### Entity Mapping Errors
If you get errors about Activity/Event/Trip mappings, ensure your database is fresh:
```bash
docker-compose down -v
docker-compose up -d
```

## Development Notes

### Working with the New Model

**Creating an Event:**
```java
Event event = new Event();
event.setName("Tech Conference");
event.setDescription("A conference about technology");
event.setActivityDate(LocalDateTime.now().plusDays(30));
event.setLocation("Main Hall");
event.setPrice(new BigDecimal("50.00"));
event.setCapacity(200);
event.setOrganizerId(organizerId);
event.setCategory(ActivityCategory.CONFERENCE);
event.setStatus(ActivityStatus.UPCOMING);
event.setTopic("Technology and Innovation");
event.setVenue("Main Hall, Building A");
event.setAgenda("Registration, Talks, Networking");
event.setSpeakers(Arrays.asList("Speaker 1", "Speaker 2"));
eventRepository.save(event);
```

**Creating a Trip:**
```java
Trip trip = new Trip();
trip.setName("Mountain Adventure");
trip.setDescription("Hiking trip to the mountains");
trip.setActivityDate(LocalDateTime.now().plusDays(15));
trip.setLocation("Blue Ridge Mountains");
trip.setPrice(new BigDecimal("150.00"));
trip.setCapacity(50);
trip.setOrganizerId(organizerId);
trip.setCategory(ActivityCategory.FIELD_TRIP);
trip.setStatus(ActivityStatus.UPCOMING);
trip.setDestination("Blue Ridge Mountains");
trip.setDurationDays(3);
trip.setTransportMode("Bus");
trip.setStartLocation("Campus");
trip.setEndLocation("Mountain Base");
trip.setItinerary("Day 1: Travel, Day 2: Hiking, Day 3: Return");
tripRepository.save(trip);
```

## Contributing

When making changes, ensure they align with the After DP PlantUML diagrams in `Milestones/PM_3/Class Diagram/After DP/`.

## License

[Your License Here]
