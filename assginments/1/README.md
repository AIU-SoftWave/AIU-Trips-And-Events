# Assignment 1: Space Probe Configuration System

This assignment demonstrates the implementation of three design patterns (**Builder**, **Prototype**, and **Singleton**) in a Space Probe Configuration System using Java (backend) and Next.js (frontend).

## 📋 Assignment Overview

**Goal**: Design a software module that manages the configuration of a complex object (Space Probe) using three design patterns:
1. **Builder Pattern**: For step-by-step construction of complex SpaceProbe objects
2. **Prototype Pattern**: For efficient deep cloning of probe templates
3. **Singleton Pattern**: For centralized configuration management

## 🗂️ Project Structure

```
assginments/1/
├── java-backend/              # Java implementation
│   ├── src/
│   │   └── main/java/com/spaceprobe/
│   │       ├── SpaceProbe.java
│   │       ├── builder/
│   │       │   ├── SpaceProbeBuilder.java
│   │       │   ├── MarsProbeBuilder.java
│   │       │   ├── JupiterProbeBuilder.java
│   │       │   └── MissionControl.java
│   │       ├── prototype/
│   │       │   └── IPrototype.java
│   │       ├── singleton/
│   │       │   └── ConfigurationManager.java
│   │       └── client/
│   │           └── SpaceMissionApp.java
│   ├── compile-and-run.sh
│   └── README.md
│
├── nextjs-frontend/           # Next.js web application
│   ├── app/
│   │   ├── components/
│   │   │   ├── ProbeCard.tsx
│   │   │   ├── BuilderVisualizer.tsx
│   │   │   ├── SingletonIndicator.tsx
│   │   │   └── PrototypeVisualizer.tsx
│   │   ├── types/
│   │   │   └── spaceprobe.ts
│   │   ├── page.tsx
│   │   └── layout.tsx
│   ├── package.json
│   └── README.md
│
├── class_digram.pu           # PlantUML class diagram
├── assinment_exolain.md      # Assignment requirements
└── README.md                 # This file
```

## 🎯 Design Patterns Implementation

### 1. Builder Pattern

**Purpose**: Separates the construction of complex SpaceProbe objects from their representation.

**Components**:
- `SpaceProbeBuilder` (Interface): Defines construction steps
- `MarsProbeBuilder` & `JupiterProbeBuilder` (Concrete Builders): Implement specific configurations
- `MissionControl` (Director): Orchestrates the construction process
- `SpaceProbe` (Product): The complex object being built

**Benefits**:
- Step-by-step construction process
- Hides internal representation
- Different builders can create different representations
- Clear separation of construction logic

### 2. Prototype Pattern

**Purpose**: Creates new objects by cloning existing prototypes, avoiding expensive reconstruction.

**Components**:
- `IPrototype` (Interface): Defines the clone method
- `SpaceProbe` (Concrete Prototype): Implements deep cloning

**Features**:
- Deep copy implementation (properly copies List objects)
- Efficient object creation
- Independence verification (clones don't affect originals)
- Post-cloning modifications

**Benefits**:
- Faster object creation than building from scratch
- Reduces code duplication
- Independent object instances

### 3. Singleton Pattern

**Purpose**: Ensures only one instance of ConfigurationManager exists throughout the application.

**Components**:
- `ConfigurationManager` (Singleton): Manages template registry

**Features**:
- Private constructor prevents external instantiation
- Static `getInstance()` method for global access
- Thread-safe (eager initialization)
- Centralized template management

**Benefits**:
- Single point of access
- Controlled instantiation
- Resource efficiency
- Centralized configuration

## 🚀 Getting Started

### Java Backend

**Prerequisites**: Java JDK 8+

```bash
cd java-backend
chmod +x compile-and-run.sh
./compile-and-run.sh
```

The Java application will:
1. Build Mars and Jupiter template probes using Builder pattern
2. Register templates in the Singleton ConfigurationManager
3. Clone templates using Prototype pattern (3 Mars, 1 Jupiter)
4. Modify cloned probes without affecting templates
5. Verify independence of clones

### Next.js Frontend

**Prerequisites**: Node.js 18+

```bash
cd nextjs-frontend
npm install
npm run dev
```

Open [http://localhost:3000](http://localhost:3000) to see the interactive web application.

The web application provides:
- Visual Builder pattern demonstration
- Singleton instance indicator
- Prototype cloning visualization
- Interactive probe management
- Real-time pattern verification

## 📊 Key Features

### SpaceProbe Components

Each SpaceProbe has:
1. **Propulsion System**: Ion Thruster, Chemical Rocket
2. **Power Source**: Solar Panels, RTG
3. **Scientific Instruments**: List of 4 instruments (minimum 3 required)
4. **Mission Target**: Mars, Jupiter, etc.
5. **Payload Mass**: Numerical value in kilograms

### Mars Probe Configuration
- Propulsion: Chemical Rocket
- Power: Solar Panels
- Instruments: High-Resolution Camera, Spectrometer, Soil Analysis Kit, Weather Station
- Default Mass: 150.0 kg

### Jupiter Probe Configuration
- Propulsion: Ion Thruster
- Power: Radioisotope Thermoelectric Generator (RTG)
- Instruments: Magnetometer, Ultraviolet Spectrometer, Plasma Wave Detector, Infrared Camera
- Default Mass: 300.0 kg

## ✅ Assignment Requirements Met

- [x] **Builder Pattern**: Fully implemented with Director and Concrete Builders
- [x] **Prototype Pattern**: Deep cloning with independence verification
- [x] **Singleton Pattern**: Thread-safe implementation with private constructor
- [x] **Complex Object**: SpaceProbe has all 5 required components
- [x] **Demonstration**: Both Java CLI and Web UI show all patterns
- [x] **Documentation**: Comprehensive README and inline comments
- [x] **UML Diagram**: Provided as class_digram.pu
- [x] **Independence Test**: Verifies clones don't affect templates

## 🧪 Testing & Verification

### Java Backend Tests

Run the application to see:
- ✓ Template creation using Builder pattern
- ✓ Singleton verification (manager == manager2)
- ✓ Prototype cloning (3 Mars + 1 Jupiter)
- ✓ Modification of clones
- ✓ Template integrity verification

### Frontend Tests

Use the web application to:
1. Build templates and watch Builder pattern steps
2. Verify Singleton shows 1 instance always
3. Clone templates and see Prototype visualizer
4. Modify clones and verify templates unchanged
5. Reset and repeat to verify consistency

## 📚 Documentation

- **Java Backend**: See `java-backend/README.md`
- **Next.js Frontend**: See `nextjs-frontend/README.md`
- **Class Diagram**: See `class_digram.pu`
- **Assignment Details**: See `assinment_exolain.md`

## 🎓 Learning Outcomes

This assignment demonstrates:
1. How to separate object construction from representation (Builder)
2. How to efficiently clone complex objects (Prototype)
3. How to ensure single instance across application (Singleton)
4. How to integrate multiple patterns in a cohesive system
5. How to document and visualize design patterns
6. How to create both CLI and web-based demonstrations

## 🔒 Design Principles Applied

- **Single Responsibility**: Each class has one clear purpose
- **Open/Closed**: New probe types can be added without modifying existing code
- **Dependency Inversion**: Code depends on abstractions (interfaces)
- **Separation of Concerns**: Builder, cloning, and management are separate
- **DRY (Don't Repeat Yourself)**: Template reuse via Prototype pattern

## 💡 Usage Scenarios

### Scenario 1: Mission Planning
1. Use Builder to create template probes for different missions
2. Store templates in Singleton ConfigurationManager
3. Clone templates for multiple mission deployments

### Scenario 2: Configuration Management
1. Create baseline probe configurations once
2. Clone and customize for specific mission requirements
3. Maintain template integrity while allowing modifications

### Scenario 3: Resource Optimization
1. Expensive probe construction done once (Builder)
2. Efficient duplication via cloning (Prototype)
3. Centralized management reduces overhead (Singleton)

## 🛠️ Technologies Used

- **Backend**: Java 8+
- **Frontend**: Next.js 16, React, TypeScript
- **Styling**: Tailwind CSS
- **Build Tools**: javac, npm
- **Documentation**: Markdown, PlantUML

## 📝 Notes

- All code is well-documented with inline comments
- Both implementations demonstrate the same patterns
- Web UI provides visual verification of pattern behaviors
- Java CLI provides detailed console output
- Deep copy is properly implemented (List objects are cloned)
- Thread-safety is ensured in Singleton (eager initialization)

## 🎯 Grading Criteria Coverage

- **Builder Pattern (30%)**: ✅ Fully implemented with Director and Concrete Builders
- **Prototype Pattern (20%)**: ✅ Deep cloning with independence verification
- **Singleton Pattern (20%)**: ✅ Thread-safe with proper access control
- **Working Demo (20%)**: ✅ Both CLI and web demos provided
- **Documentation (10%)**: ✅ Comprehensive README, comments, and UML

---

**Assignment completed by**: Software Engineering Student
**Submission**: Java backend + Next.js frontend + Documentation
**Status**: Ready for TA discussion and evaluation
