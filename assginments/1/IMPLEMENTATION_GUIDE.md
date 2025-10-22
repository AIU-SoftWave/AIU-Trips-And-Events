# Space Probe System Implementation Guide

## Overview

This implementation demonstrates three fundamental design patterns as specified in the PlantUML class diagram (`class_digram.pu`):

1. **Builder Pattern** - Complex object construction
2. **Prototype Pattern** - Efficient object cloning
3. **Singleton Pattern** - Global instance management

## Architecture

### Design Pattern Flow

```
1. Builder Pattern creates Templates
   ↓
2. Singleton Pattern registers Templates
   ↓
3. Prototype Pattern clones Templates for deployment
```

## Java Implementation

### Directory Structure

```
java/
├── pom.xml
└── src/main/java/com/aiu/spaceprobe/
    ├── app/
    │   └── SpaceMissionApp.java          # Main client application
    ├── builder/
    │   ├── SpaceProbeBuilder.java        # Builder interface
    │   ├── MarsProbeBuilder.java         # Concrete builder for Mars
    │   ├── JupiterProbeBuilder.java      # Concrete builder for Jupiter
    │   └── MissionControl.java           # Director
    ├── model/
    │   └── SpaceProbe.java               # Product (implements IPrototype)
    ├── prototype/
    │   └── IPrototype.java               # Prototype interface
    └── singleton/
        └── ConfigurationManager.java     # Singleton registry
```

### Running the Java Application

```bash
cd assginments/1/java
mvn clean compile exec:java
```

**Expected Output:**
- Creates Mars and Jupiter templates using Builder pattern
- Registers templates with ConfigurationManager (Singleton)
- Deploys multiple probe instances by cloning (Prototype)
- Demonstrates template integrity (originals unchanged)

### Key Java Classes

#### 1. SpaceProbe (Product + Prototype)
```java
public class SpaceProbe implements IPrototype {
    private String propulsionSystem;
    private String powerSource;
    private List<String> scientificInstruments;
    private String missionTarget;
    private double payloadMass;
    
    @Override
    public IPrototype deepClone() {
        // Deep copy implementation
    }
}
```

#### 2. ConfigurationManager (Singleton)
```java
public class ConfigurationManager {
    private static ConfigurationManager instance;
    private Map<String, IPrototype> prototypes;
    
    private ConfigurationManager() { }
    
    public static synchronized ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }
}
```

#### 3. MarsProbeBuilder (Concrete Builder)
```java
public class MarsProbeBuilder implements SpaceProbeBuilder {
    private SpaceProbe probe;
    
    @Override
    public void buildPropulsionSystem() {
        probe.setPropulsionSystem("Chemical Rocket Propulsion");
    }
    
    @Override
    public SpaceProbe getResult() {
        SpaceProbe result = this.probe;
        this.reset();
        return result;
    }
}
```

## Next.js/TypeScript Implementation

### Directory Structure

```
nextjs/
├── package.json
├── tsconfig.json
├── next.config.ts
├── tailwind.config.ts
├── postcss.config.mjs
├── app/
│   ├── layout.tsx
│   ├── page.tsx
│   └── globals.css
├── components/
│   ├── PatternDemo.tsx           # Main UI component
│   └── ProbeCard.tsx             # Probe display component
└── lib/
    └── spaceProbe.ts             # TypeScript implementation of patterns
```

### Running the Frontend

```bash
cd assginments/1/nextjs
npm install
npm run dev
```

Open [http://localhost:3000](http://localhost:3000) in your browser.

### Key TypeScript Features

#### 1. Pattern Implementation in TypeScript
```typescript
// Prototype Pattern
export interface IPrototype {
  deepClone(): IPrototype;
}

// Singleton Pattern
export class ConfigurationManager {
  private static instance: ConfigurationManager;
  
  private constructor() { }
  
  static getInstance(): ConfigurationManager {
    if (!ConfigurationManager.instance) {
      ConfigurationManager.instance = new ConfigurationManager();
    }
    return ConfigurationManager.instance;
  }
}
```

#### 2. React Integration
- Uses React hooks (useState, useEffect) for state management
- Client-side pattern execution with 'use client' directive
- Real-time UI updates when deploying probes

## Features Demonstrated

### Builder Pattern ✅
- **MarsProbeBuilder**: Builds Mars-specific configurations
- **JupiterProbeBuilder**: Builds Jupiter-specific configurations
- **MissionControl**: Director orchestrates the build process
- **Benefits**: 
  - Step-by-step construction
  - Different representations from same process
  - Separation of construction and representation

### Prototype Pattern ✅
- **IPrototype interface**: Defines cloning contract
- **deepClone() method**: Creates independent copies
- **Efficient deployment**: Clone instead of rebuild
- **Benefits**:
  - Fast object creation
  - Reduces initialization overhead
  - Independent modifications

### Singleton Pattern ✅
- **ConfigurationManager**: Single global instance
- **getInstance()**: Thread-safe access
- **Prototype registry**: Centralized template management
- **Benefits**:
  - Single point of access
  - Consistent state across application
  - Resource efficiency

## UI Features

### Interactive Demonstrations

1. **Initialize System**: Click to create templates using Builder pattern
2. **View Templates**: See Mars and Jupiter probe specifications
3. **Deploy Probes**: Clone templates with optional customization
4. **Manage Fleet**: View and delete deployed probes
5. **Pattern Explanation**: Built-in documentation of each pattern

### Visual Design

- **Dark space theme**: Navy background with gradient accents
- **Color coding**: Mars (red), Jupiter (orange), patterns (green/purple/yellow)
- **Responsive layout**: Grid system adapts to screen size
- **Interactive controls**: Buttons, cards, and delete functionality

## Testing the Implementation

### Java Tests

1. **Compile**: `mvn clean compile`
2. **Run**: `mvn exec:java`
3. **Verify**: Check console output for pattern demonstrations

### Frontend Tests

1. **Build**: `npm run build` (production build test)
2. **Dev**: `npm run dev` (development server)
3. **Interact**: Use UI controls to test functionality

### Pattern Verification

#### Builder Pattern
- ✅ Templates constructed step-by-step
- ✅ Different builders produce different configurations
- ✅ Director coordinates the process

#### Prototype Pattern
- ✅ Clones are independent of originals
- ✅ Modifications don't affect templates
- ✅ Efficient creation without rebuilding

#### Singleton Pattern
- ✅ Only one ConfigurationManager instance
- ✅ Shared across entire application
- ✅ Thread-safe access

## Code Quality

### Best Practices Implemented

- ✅ **SOLID Principles**: Single Responsibility, Open/Closed, etc.
- ✅ **DRY**: No code duplication
- ✅ **Clean Code**: Meaningful names, clear structure
- ✅ **Type Safety**: Strong typing in both Java and TypeScript
- ✅ **Documentation**: Comprehensive comments and README

### Security Considerations

- ✅ No sensitive data exposure
- ✅ Input validation (type safety)
- ✅ No external dependencies with vulnerabilities
- ✅ Proper encapsulation (private constructors, etc.)

## Learning Outcomes

After studying this implementation, you will understand:

1. **When to use Builder Pattern**
   - Complex object construction
   - Multiple configuration options
   - Step-by-step assembly required

2. **When to use Prototype Pattern**
   - Object creation is expensive
   - Need many similar objects
   - Want independent copies

3. **When to use Singleton Pattern**
   - Need exactly one instance
   - Global access point required
   - Shared resource management

4. **How patterns work together**
   - Builder creates prototypes
   - Singleton manages prototypes
   - Prototype enables efficient deployment

## Troubleshooting

### Java Issues

**Problem**: Maven compilation fails
**Solution**: Ensure Java 17+ is installed: `java -version`

**Problem**: Execution fails
**Solution**: Run `mvn clean` first, then `mvn compile exec:java`

### Frontend Issues

**Problem**: Build fails with Tailwind error
**Solution**: Ensure `@tailwindcss/postcss` is in dependencies and properly configured in `postcss.config.mjs`

**Problem**: TypeScript errors
**Solution**: Run `npm install` to ensure all dependencies are installed

**Problem**: Port 3000 already in use
**Solution**: Kill existing process or use different port: `npm run dev -- -p 3001`

## Additional Resources

- PlantUML Diagram: `class_digram.pu`
- Main README: `README.md`
- Java Source: `java/src/main/java/com/aiu/spaceprobe/`
- Frontend Source: `nextjs/`

## Credits

Developed as Assignment 1 for Software Design Patterns course at AIU.

**Implementation Date**: October 2025

**Technologies Used**:
- Java 17
- Maven
- Spring (not used - pure Java implementation)
- Next.js 15
- React 19
- TypeScript 5
- Tailwind CSS 4
