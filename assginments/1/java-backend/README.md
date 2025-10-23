# Space Probe Configuration System - Java Backend

This is a Java implementation demonstrating three design patterns: **Builder**, **Prototype**, and **Singleton**.

## Design Patterns Implemented

### 1. Builder Pattern
- **SpaceProbeBuilder** (Interface): Defines the construction steps
- **MarsProbeBuilder** (Concrete Builder): Builds Mars-specific probes
- **JupiterProbeBuilder** (Concrete Builder): Builds Jupiter-specific probes
- **MissionControl** (Director): Orchestrates the construction process

### 2. Prototype Pattern
- **IPrototype** (Interface): Defines the clone method
- **SpaceProbe** (Concrete Prototype): Implements deep cloning

### 3. Singleton Pattern
- **ConfigurationManager** (Singleton): Manages probe templates, ensures single instance

## Project Structure

```
java-backend/
├── src/main/java/com/spaceprobe/
│   ├── SpaceProbe.java                  # Product class
│   ├── builder/
│   │   ├── SpaceProbeBuilder.java       # Builder interface
│   │   ├── MarsProbeBuilder.java        # Concrete builder for Mars
│   │   ├── JupiterProbeBuilder.java     # Concrete builder for Jupiter
│   │   └── MissionControl.java          # Director class
│   ├── prototype/
│   │   └── IPrototype.java              # Prototype interface
│   ├── singleton/
│   │   └── ConfigurationManager.java    # Singleton registry
│   └── client/
│       └── SpaceMissionApp.java         # Main application
├── compile-and-run.sh                   # Build and run script
└── README.md                            # This file
```

## How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher

### Compilation and Execution

```bash
# Make the script executable (first time only)
chmod +x compile-and-run.sh

# Run the application
./compile-and-run.sh
```

### Manual Compilation (Alternative)

```bash
# Create build directory
mkdir -p build/classes

# Compile
javac -d build/classes -sourcepath src/main/java \
  src/main/java/com/spaceprobe/client/SpaceMissionApp.java

# Run
java -cp build/classes com.spaceprobe.client.SpaceMissionApp
```

## Features Demonstrated

1. **Template Probe Creation**: Uses Builder pattern to construct complex Mars and Jupiter probes
2. **Singleton Registry**: ConfigurationManager ensures only one instance manages all templates
3. **Efficient Cloning**: Prototype pattern enables quick creation of deployment probes
4. **Deep Copy Verification**: Modifications to clones don't affect original templates
5. **Independence Test**: Demonstrates that each cloned probe is completely independent

## Output

The application demonstrates:
- Creating 2 template probes (Mars and Jupiter) using Builder pattern
- Registering templates in the Singleton ConfigurationManager
- Cloning 3 Mars probes and 1 Jupiter probe using Prototype pattern
- Modifying cloned probes without affecting templates
- Verifying template integrity after cloning

## Key Design Decisions

1. **Thread-Safe Singleton**: Uses eager initialization for simplicity and thread-safety
2. **Deep Cloning**: Properly copies List objects to ensure independence
3. **Factory Method**: SpaceProbe uses factory method for controlled instantiation
4. **Step-by-Step Construction**: Builder pattern separates construction logic from representation
5. **Director Orchestration**: MissionControl encapsulates construction algorithms

## SpaceProbe Components

Each SpaceProbe has:
- **Propulsion System**: Type of propulsion (e.g., Ion Thruster, Chemical Rocket)
- **Power Source**: Energy system (e.g., Solar Panels, RTG)
- **Scientific Instruments**: List of at least 3 instruments
- **Mission Target**: Destination (e.g., Mars, Jupiter)
- **Payload Mass**: Mass in kilograms

## Design Pattern Benefits

- **Builder**: Simplifies complex object construction, allows different representations
- **Prototype**: Enables efficient object creation by cloning instead of rebuilding
- **Singleton**: Centralizes configuration management, prevents multiple instances
