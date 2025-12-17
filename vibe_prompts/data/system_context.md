# AIU Trips & Events Management System - Context Data

## System Overview

AIU Trips & Events Management System is a comprehensive university event and trips management platform.

## Technology Stack

### Backend Stack
- **Framework:** Java Spring Boot
- **API:** RESTful APIs
- **ORM:** JPA/Hibernate for persistence
- **Database:** MySQL database
- **Build Tool:** Maven for build management

### Frontend Stack
- **Framework:** React.js
- **HTTP Client:** Axios for API integration
- **Routing:** React Router for navigation
- **State Management:** Context API for state management

## Architecture

### Design Patterns (11 Patterns)
1. **Factory Pattern** - Model creation
2. **Builder Pattern** - Activity construction
3. **Prototype Pattern** - Activity cloning
4. **Command Pattern** - Controller operations
5. **Chain of Responsibility** - Request handling
6. **State Pattern** - Activity lifecycle
7. **Strategy Pattern** - Pricing calculation
8. **Decorator Pattern** - Ticket enhancements
9. **Bridge Pattern** - Notification channels
10. **Adapter Pattern** - External service integration
11. **Memento Pattern** - State preservation

### Architecture Layers
- **Controller Layer:** REST endpoints
- **Service Layer:** Business logic
- **Repository Layer:** Data access
- **Model Layer:** Domain entities

### Design Principles
- Clean separation of concerns
- SOLID principles adherence
- Multi-layered architecture

## Domain Entities

### Core Entities

#### User
- Authentication and authorization
- Role-based access control
- Profile management

#### Activity (Abstract Base Class)
- Common properties for events and trips
- Lifecycle management
- Capacity tracking

##### Event (extends Activity)
- University events
- Conference, seminars, workshops
- Date and location management

##### Trip (extends Activity)
- Educational trips
- Destination and itinerary
- Transportation details

#### Booking
- User activity registrations
- Payment tracking
- Status management
- Cancellation handling

#### Ticket
- Booking confirmations
- QR code generation
- Access control

#### Notification
- Multi-channel system (Email, SMS, Push)
- Template management
- Delivery tracking
- Priority handling

#### Report
- Analytics and exports
- Custom report generation
- Data visualization
- Export formats (PDF, Excel, CSV)

#### Feedback
- User reviews and ratings
- 5-star rating system
- Comments and suggestions
- Response from organizers

## Key Features

### User Management
- User registration and authentication
- Role-based access (Student, Admin, Organizer)
- Profile management
- Password reset

### Event & Trip Management
- Create, update, delete events/trips
- Schedule management
- Capacity management
- Category and tagging

### Booking & Ticketing System
- Activity booking workflow
- Payment integration
- Ticket generation
- Cancellation and refunds
- Waiting list management

### Notification System
- Multi-channel notifications (Email, SMS, Push)
- Event reminders
- Booking confirmations
- Status updates

### Reporting System
- Activity reports (attendance, revenue, ratings)
- User reports (booking history, preferences)
- Financial reports (revenue breakdown)
- Export capabilities

### Feedback & Rating System
- Post-event reviews
- Star ratings
- Comment moderation
- Analytics dashboard

### Admin Dashboard
- System overview
- User management
- Activity management
- Report generation
- Settings configuration

## Current State

The system has two implementations:
1. **Before Design Patterns** - Located in `Milestones/PM_3/Project_without_DP_UML`
2. **After Design Patterns** - Located in `Project` folder (main implementation)

## Reference Documentation

- Full analysis: `/report/05_vibe_coding_analysis.md`
- Class diagrams: `Milestones/PM_3/Class Diagram/`
- Project comparison: Shows 62.5% time savings with proper design patterns

## Project Structure

### Backend Package Structure
```
com.aiu.trips/
├── adapter/          - Email service adapters
├── bridge/           - Notification system
├── builder/          - Activity builders
├── chain/            - Request handler chain
├── command/          - Command pattern implementations
├── decorator/        - Ticket decorators
├── factory/          - Model factories
├── memento/          - State mementos
├── prototype/        - Prototype interface
├── state/            - Activity states
├── strategy/         - Pricing strategies
├── model/            - Domain entities
├── repository/       - Data access layer
├── service/          - Business logic
└── controller/       - REST endpoints
```

### Frontend Component Structure
```
src/
├── components/
│   ├── auth/           - Authentication components
│   ├── activities/     - Activity management
│   ├── bookings/       - Booking workflow
│   ├── notifications/  - Notification center
│   ├── reports/        - Report dashboards
│   ├── admin/          - Admin panel
│   └── common/         - Reusable components
├── services/           - API integration
├── contexts/           - State management
├── hooks/              - Custom hooks
└── utils/              - Helper functions
```

## Quality Metrics (Target)

- **Compilation Success:** 100%
- **Pattern Correctness:** 95%+
- **Code Organization:** 95%+
- **Documentation:** 85%+
- **SOLID Principles:** 90%+
- **Integration Quality:** 95%+

## Best Practices

### Backend
- Use proper Spring annotations
- Implement comprehensive error handling
- Follow naming conventions
- Write clear JavaDoc
- Implement validation
- Use DTOs for API requests/responses

### Frontend
- Use functional components with hooks
- Implement proper error boundaries
- Handle loading states
- Provide accessibility features
- Follow responsive design
- Implement proper form validation

### General
- Write clean, maintainable code
- Follow SOLID principles
- Implement proper logging
- Write comprehensive tests
- Document complex logic
- Use meaningful variable names
