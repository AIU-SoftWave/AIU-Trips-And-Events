# Data for AI-Assisted Project Generation

This folder contains all the necessary data and diagrams for AI to generate the complete AIU Trips & Events Management System.

## Contents

### PlantUML Diagrams (After Design Patterns)

The following PlantUML diagrams represent the complete system architecture with all design patterns implemented:

1. **University_Trips_Events_Management_System.pu** - Main system overview
2. **Event_Management.pu** - Event and activity management with patterns
3. **Booking_Ticketing.pu** - Booking system with Strategy pattern for pricing
4. **User_Management_.pu** - User authentication and management
5. **Notification.pu** - Notification system with Bridge pattern
6. **Reports_Analytics.pu** - Reporting and analytics functionality
7. **Controller.pu** - Controller layer with Command pattern
8. **Repository_Layer.pu** - Data persistence layer with Repository pattern
9. **Data_Layer.pu** - Data models and Memento pattern
10. **Model_Factory.pu** - Factory pattern for model creation

## Design Patterns Included

The diagrams incorporate the following 11 design patterns:

### Creational Patterns
- **Factory Pattern** - Model creation (Model_Factory.pu)
- **Abstract Factory + Builder** - Activity creation (Event_Management.pu)
- **Prototype Pattern** - Activity cloning (Event_Management.pu)

### Structural Patterns
- **Adapter Pattern** - Email service integration (Notification.pu)
- **Bridge Pattern** - Notification channels and messages (Notification.pu)
- **Decorator Pattern** - Ticket service enhancements (Booking_Ticketing.pu)

### Behavioral Patterns
- **Command Pattern** - Controller operations (Controller.pu)
- **Chain of Responsibility** - Request handling (Controller.pu)
- **State Pattern** - Activity lifecycle management (Event_Management.pu)
- **Strategy Pattern** - Pricing strategies (Booking_Ticketing.pu)
- **Memento Pattern** - State history (Data_Layer.pu)

## Project Structure

The target project structure is:

```
AIU-Trips-And-Events/
├── Project/
│   ├── backend/              # Spring Boot Java backend
│   │   ├── src/main/java/com/aiu/trips/
│   │   │   ├── adapter/     # Adapter pattern
│   │   │   ├── bridge/      # Bridge pattern
│   │   │   ├── builder/     # Builder pattern
│   │   │   ├── chain/       # Chain of Responsibility
│   │   │   ├── command/     # Command pattern
│   │   │   ├── decorator/   # Decorator pattern
│   │   │   ├── factory/     # Factory pattern
│   │   │   ├── memento/     # Memento pattern
│   │   │   ├── prototype/   # Prototype pattern
│   │   │   ├── state/       # State pattern
│   │   │   ├── strategy/    # Strategy pattern
│   │   │   ├── model/       # Core entities
│   │   │   ├── service/     # Business logic
│   │   │   ├── controller/  # REST endpoints
│   │   │   ├── repository/  # Data access
│   │   │   ├── dto/         # Data transfer objects
│   │   │   └── exception/   # Custom exceptions
│   │   └── pom.xml
│   └── frontend/             # Next.js frontend
│       ├── src/
│       │   ├── components/  # React components
│       │   ├── services/    # API integration
│       │   ├── contexts/    # State management
│       │   └── utils/       # Utilities
│       └── package.json
└── docker-compose.yml        # Container orchestration
```

## Technology Stack

### Backend
- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17+
- **Security**: Spring Security with JWT
- **Database**: Spring Data JPA with H2 (in-memory)
- **Build Tool**: Maven
- **Additional**: QR Code generation (ZXing library)

### Frontend
- **Framework**: Next.js 15
- **Language**: TypeScript
- **Styling**: Tailwind CSS
- **HTTP Client**: Axios
- **State Management**: React Context
- **Additional**: QR code display

## Key Features to Implement

1. **Authentication & Profiles**
   - Login/Register with JWT
   - User profile management
   
2. **Event/Trip Management**
   - CRUD operations for events and trips
   - Activity lifecycle (Upcoming → Completed/Cancelled)
   
3. **Booking & Ticketing**
   - Reservation system
   - QR Code generation
   - Ticket validation
   
4. **Payment Tracking**
   - Cash-only payment tracking
   
5. **Notifications**
   - Multi-channel notifications (Email, SMS, Push)
   - Real-time updates
   
6. **Feedback System**
   - Post-event ratings and comments
   
7. **Reports & Analytics**
   - Participant tracking
   - Income reporting
   - Feedback analysis

## Notes for AI Generation

- All diagrams use PlantUML format (.pu extension)
- Follow the exact class relationships and patterns shown in diagrams
- Implement all 11 design patterns as specified
- Maintain package structure as shown in project layout
- Ensure 100% compilation success
- Focus on After DP diagrams (these have patterns already designed)
- Reference: `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/` is the repository root
