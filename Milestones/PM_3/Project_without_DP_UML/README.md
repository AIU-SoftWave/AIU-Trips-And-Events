# AIU Trips and Events Management System

## Project Overview

This directory contains the full-stack implementation of the AIU Trips and Events Management System with 12 design patterns, based on the PlantUML class diagrams in `Milestones/PM_3/Class Diagram/Before DP/`.

## 🚀 Quick Start with Docker

The easiest way to run the entire application:

```bash
# Start all services (frontend, backend, database)
./start.sh

# Or manually with docker-compose
docker-compose up -d
```

**Access the application:**
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080
- Database: localhost:5432

**Sample Credentials:**
- Admin: `admin@aiu.edu` / `admin123`
- Student: `john.doe@aiu.edu` / `password123`
- Organizer: `organizer@aiu.edu` / `password123`

📖 **See [DOCKER_SETUP.md](DOCKER_SETUP.md) for complete Docker documentation**

## Architecture

### Stack
- **Frontend:** Next.js 15, React 19, TypeScript, Tailwind CSS
- **Backend:** Spring Boot 3.2, Java 17, JPA/Hibernate
- **Database:** PostgreSQL 16 (Docker) / H2 (Development)
- **Containerization:** Docker & Docker Compose

## Design Patterns Implementation

### Implementation Location

All design patterns have been implemented in:
```
Milestones/PM_3/Project_without_DP_UML/backend/src/main/java/com/aiu/trips/
```

## Package Structure

```
com/aiu/trips/
├── adapter/          # Adapter Pattern
├── bridge/           # Bridge Pattern  
├── builder/          # Builder Pattern (Activity & Report)
├── chain/            # Chain of Responsibility Pattern
├── command/          # Command Pattern
├── decorator/        # Decorator Pattern
├── factory/          # Factory Pattern
│   └── activity/     # Abstract Factory Pattern
├── memento/          # Memento Pattern
├── prototype/        # Prototype Pattern (IPrototype interface)
├── state/            # State Pattern
└── strategy/         # Strategy Pattern
```

## Implemented Patterns (12 Total)

### Creational Patterns (5)
1. **Factory Pattern** - `factory/`
2. **Abstract Factory Pattern** - `factory/activity/`
3. **Builder Pattern (Activity)** - `builder/` (Event & Trip builders)
4. **Builder Pattern (Report)** - `builder/` (PDF & CSV builders)
5. **Prototype Pattern** - `prototype/` + `model/Event.java`

### Behavioral Patterns (5)
6. **Command Pattern** - `command/`
7. **Strategy Pattern** - `strategy/`
8. **State Pattern** - `state/`
9. **Chain of Responsibility (Request)** - `chain/` (Request handlers)
10. **Chain of Responsibility (Booking)** - `chain/` (Booking handlers)

*Note: Memento is counted separately for Activity and Booking*
11. **Memento Pattern (Activity)** - `memento/`
12. **Memento Pattern (Booking)** - `memento/`

### Structural Patterns (3)
13. **Decorator Pattern** - `decorator/`
14. **Bridge Pattern** - `bridge/`
15. **Adapter Pattern** - `adapter/`

## Key Documentation Files

1. **DESIGN_PATTERNS_IMPLEMENTATION.md** - Comprehensive guide to all patterns with usage examples
2. **PATTERN_TO_DIAGRAM_MAPPING.md** - Maps each pattern to source PlantUML diagrams
3. **patterns_to_use.md** - Original specification document

## Source Materials

All implementations are based on:
- **PlantUML Diagrams**: `Milestones/PM_3/Class Diagram/Before DP/*.pu`
- **Pattern Specifications**: `patterns_to_use.md`

## Compliance

✅ All patterns implemented according to specifications  
✅ Code compiles without errors  
✅ Based exclusively on "Before DP" PlantUML diagrams  
✅ Follows SOLID principles  
✅ Spring-managed components  
✅ Compatible with existing services  

## Integration

All design patterns are implemented as Spring components and can be autowired into existing services:

```java
// Example: Using Factory Pattern
@Autowired
private IModelFactory modelFactory;

Event event = modelFactory.createModel(Event.class);
```

```java
// Example: Using Strategy Pattern
@Autowired
private PricingStrategy earlyBirdPricing;

Double price = earlyBirdPricing.calculatePrice(event, numberOfTickets);
```

```java
// Example: Using Command Pattern
@Autowired
private ControllerCommandInvoker invoker;

IControllerCommand command = new CreateEventCommand(eventService, event, userEmail);
invoker.executeCommand(command);
```

## Build Status

✅ Maven build successful  
✅ All Java files compile  
✅ No compilation errors  

## Next Steps

1. Integrate patterns into existing service classes
2. Add unit tests for pattern implementations
3. Update API controllers to use command pattern
4. Apply pricing strategies in booking service
5. Implement state management in event service

## Contact

For questions about the implementation, please refer to:
- DESIGN_PATTERNS_IMPLEMENTATION.md for detailed pattern usage
- PATTERN_TO_DIAGRAM_MAPPING.md for PlantUML diagram references
