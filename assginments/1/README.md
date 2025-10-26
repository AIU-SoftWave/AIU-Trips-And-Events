# Space Probe Configuration System

## Software Engineering Assignment: Complex Object Construction & Management

This project demonstrates the practical implementation of three fundamental design patterns:
- **Builder Pattern** - For step-by-step construction of complex objects
- **Prototype Pattern** - For efficient cloning with deep copying
- **Singleton Pattern** - For ensuring single instance management

## Project Structure

```
assginments/1/
├── backend/                    # Spring Boot Java Backend
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/
│   │               └── spaceprobe/
│   │                   ├── builder/           # Builder Pattern Implementation
│   │                   │   ├── SpaceProbeBuilder.java
│   │                   │   ├── MarsProbeBuilder.java
│   │                   │   ├── JupiterProbeBuilder.java
│   │                   │   └── MissionControl.java
│   │                   ├── prototype/         # Prototype Pattern Implementation
│   │                   │   └── IPrototype.java
│   │                   ├── singleton/         # Singleton Pattern Implementation
│   │                   │   └── ConfigurationManager.java
│   │                   ├── model/
│   │                   │   └── SpaceProbe.java
│   │                   ├── controller/
│   │                   │   └── SpaceProbeController.java
│   │                   ├── SpaceProbeApplication.java
│   │                   └── SpaceMissionApp.java
│   └── pom.xml
├── frontend/                   # Next.js TypeScript Frontend
│   ├── app/
│   │   ├── components/
│   │   │   ├── ProbeDisplay.tsx
│   │   │   └── DemoRunner.tsx
│   │   └── page.tsx
│   └── package.json
├── class_digram.pu            # PlantUML Class Diagram
├── assinment_exolain.md       # Assignment Requirements
└── README.md                   # This file
```

## Design Patterns Implementation

### 1. Builder Pattern

**Purpose**: Constructs complex SpaceProbe objects step-by-step, hiding internal representation.

**Components**:
- `SpaceProbeBuilder` (Interface): Defines construction steps
- `MarsProbeBuilder` (Concrete Builder): Builds Mars-specific probes
- `JupiterProbeBuilder` (Concrete Builder): Builds Jupiter-specific probes
- `MissionControl` (Director): Orchestrates the building process

**Key Features**:
- Step-by-step construction
- Multiple builder implementations for different probe types
- Director manages the construction sequence
- Hides complex construction logic from clients

### 2. Prototype Pattern

**Purpose**: Enables efficient cloning of complex objects with deep copying.

**Components**:
- `IPrototype` (Interface): Defines the cloning contract
- `SpaceProbe` (Concrete Prototype): Implements deep cloning

**Key Features**:
- Deep copy implementation (not shallow)
- Independent clones - modifications don't affect originals
- Efficient object creation from templates
- List and object properties are properly cloned

### 3. Singleton Pattern

**Purpose**: Ensures only one instance of ConfigurationManager exists throughout the application.

**Components**:
- `ConfigurationManager` (Singleton): Manages prototype registry

**Key Features**:
- Private constructor prevents external instantiation
- Static `getInstance()` method for access
- Thread-safe eager initialization
- Manages prototype storage and retrieval

## Prerequisites

- **Java**: JDK 17 or higher
- **Maven**: 3.6 or higher
- **Node.js**: 18 or higher
- **npm**: 8 or higher

## Setup and Running

### Backend (Spring Boot)

1. Navigate to the backend directory:
```bash
cd assginments/1/backend
```

2. Build the project:
```bash
mvn clean install
```

3. Run the Spring Boot application:
```bash
mvn spring-boot:run
```

The backend API will be available at `http://localhost:8080`

4. **Alternative**: Run the standalone demo:
```bash
mvn exec:java -Dexec.mainClass="com.spaceprobe.SpaceMissionApp"
```

### Frontend (Next.js)

1. Navigate to the frontend directory:
```bash
cd assginments/1/frontend
```

2. Install dependencies:
```bash
npm install
```

3. Run the development server:
```bash
npm run dev
```

The frontend will be available at `http://localhost:3000`

## API Endpoints

### GET /api/probes/templates
Get all available probe templates from the ConfigurationManager.

**Response**:
```json
{
  "MarsTemplate": {
    "missionTarget": "Mars",
    "propulsionSystem": "Chemical Rocket",
    "powerSource": "Solar Panels",
    "scientificInstruments": [...],
    "payloadMass": 800.0
  },
  "JupiterTemplate": {...}
}
```

### GET /api/probes/templates/{key}
Get a specific template by key.

### POST /api/probes/clone/{templateKey}
Clone a probe from a template with optional modifications.

**Request Body**:
```json
{
  "payloadMass": 850.0
}
```

### POST /api/probes/build/{probeType}
Build a new probe using the Builder pattern.

**Probe Types**: `mars`, `jupiter`

### GET /api/probes/demo
Run a complete demonstration of all three design patterns.

## Features Demonstrated

### Builder Pattern Demo
1. Create concrete builders for Mars and Jupiter probes
2. Use MissionControl (Director) to construct standard probes
3. Step-by-step building process with multiple configuration options

### Prototype Pattern Demo
1. Store template probes in ConfigurationManager
2. Clone templates multiple times
3. Modify clones independently
4. Verify deep copy (modifications don't affect originals)

### Singleton Pattern Demo
1. Access ConfigurationManager via `getInstance()`
2. Single instance manages all prototype templates
3. Thread-safe implementation
4. Consistent access across application

## Testing

### Manual Testing via Console

Run the standalone demo application:
```bash
cd assginments/1/backend
mvn exec:java -Dexec.mainClass="com.spaceprobe.SpaceMissionApp"
```

This will output:
- Template probe configurations
- Cloned probe configurations with modifications
- Deep copy verification showing independence

### Testing via Web Interface

1. Start both backend and frontend
2. Open `http://localhost:3000`
3. Use the "View Templates" tab to see available templates
4. Clone templates with custom payload masses
5. Use the "Run Demo" tab to execute the complete workflow

### Testing via API

Use curl or Postman to test the REST API:

```bash
# Get all templates
curl http://localhost:8080/api/probes/templates

# Clone a probe
curl -X POST http://localhost:8080/api/probes/clone/MarsTemplate \
  -H "Content-Type: application/json" \
  -d '{"payloadMass": 850.0}'

# Run full demo
curl http://localhost:8080/api/probes/demo
```

## Design Justification

### Why Builder Pattern?
SpaceProbe objects are complex with multiple required components (propulsion, power, instruments, target, mass). The Builder pattern:
- Separates construction logic from representation
- Makes construction process clear and maintainable
- Allows different probe configurations with same building steps
- Improves code readability and testability

### Why Prototype Pattern?
Creating complex probe objects from scratch is expensive. The Prototype pattern:
- Enables efficient cloning of pre-configured templates
- Supports creating many similar objects quickly
- Allows post-cloning modifications without affecting templates
- Deep copying ensures complete independence

### Why Singleton Pattern?
ConfigurationManager must be centralized and unique. The Singleton pattern:
- Ensures consistent prototype registry across application
- Prevents duplicate managers and conflicting state
- Provides global access point for prototype management
- Thread-safe implementation prevents race conditions

## Challenges and Solutions

### Challenge 1: Deep Copy Implementation
**Problem**: Ensuring complete independence between clones and templates, especially for List properties.

**Solution**: Implemented proper deep cloning by creating new ArrayList instances and copying all primitive and String values individually.

### Challenge 2: Thread Safety in Singleton
**Problem**: Multiple threads might create multiple ConfigurationManager instances.

**Solution**: Used eager initialization with static final field, guaranteeing thread-safe singleton creation.

### Challenge 3: Builder State Management
**Problem**: Builders needed to maintain state between construction steps.

**Solution**: Implemented `reset()` method called at construction start and in `getResult()` to ensure clean state for next build.

### Challenge 4: Frontend-Backend Integration
**Problem**: Coordinating data format between Spring Boot REST API and Next.js frontend.

**Solution**: Created consistent JSON serialization and proper TypeScript interfaces matching backend model structure.

## Requirements Fulfillment

✅ **Complex Object (SpaceProbe)** with all required attributes:
- Propulsion System
- Power Source
- Scientific Instruments (List of 3+)
- Mission Target
- Payload Mass
- describe() method

✅ **Builder Pattern** implementation:
- SpaceProbeBuilder interface
- Two concrete builders (Mars, Jupiter)
- MissionControl Director class
- Step-by-step construction

✅ **Prototype Pattern** implementation:
- IPrototype interface
- deepClone() method with deep copying
- Template and clone creation demonstrated
- Independence verified

✅ **Singleton Pattern** implementation:
- ConfigurationManager class
- Private constructor
- Static getInstance() method
- Thread-safe implementation

✅ **Demonstration**:
- Client code using all three patterns
- Multiple clones with modifications
- Verification of independence
- Clear output showing all configurations

## Technologies Used

### Backend
- Java 17
- Spring Boot 3.2.0
- Maven 3.9.11
- Jackson for JSON serialization

### Frontend
- Next.js 16
- React 18
- TypeScript 5
- Tailwind CSS 4
- Modern ES6+ features

## Author

Software Engineering Assignment - AIU
Design Patterns: Builder, Prototype, and Singleton

## License

Educational project for academic purposes.
