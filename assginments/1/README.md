# Space Probe Mission Control System

A complete implementation of the Space Probe system demonstrating three essential design patterns: **Builder**, **Prototype**, and **Singleton**.

## 📋 Overview

This project implements the class diagram specified in `class_digram.pu` with both Java backend and Next.js frontend implementations.

### Design Patterns Demonstrated

1. **Builder Pattern** 🏗️
   - Creates complex `SpaceProbe` objects step-by-step
   - `MarsProbeBuilder` and `JupiterProbeBuilder` are concrete builders
   - `MissionControl` acts as the Director

2. **Prototype Pattern** 🔄
   - Enables efficient cloning of pre-configured probes
   - `IPrototype` interface with `deepClone()` method
   - `SpaceProbe` implements the prototype interface

3. **Singleton Pattern** ⭐
   - `ConfigurationManager` ensures single instance
   - Manages prototype registry globally
   - Thread-safe implementation with `getInstance()`

## 🚀 Project Structure

```
assginments/1/
├── class_digram.pu           # PlantUML class diagram
├── README.md                 # This file
├── java/                     # Java implementation
│   ├── pom.xml              # Maven configuration
│   └── src/main/java/com/aiu/spaceprobe/
│       ├── app/
│       │   └── SpaceMissionApp.java      # Main application
│       ├── builder/
│       │   ├── SpaceProbeBuilder.java    # Builder interface
│       │   ├── MarsProbeBuilder.java     # Concrete builder for Mars
│       │   ├── JupiterProbeBuilder.java  # Concrete builder for Jupiter
│       │   └── MissionControl.java       # Director
│       ├── model/
│       │   └── SpaceProbe.java           # Product class
│       ├── prototype/
│       │   └── IPrototype.java           # Prototype interface
│       └── singleton/
│           └── ConfigurationManager.java # Singleton registry
└── nextjs/                   # Next.js frontend
    ├── package.json
    ├── tsconfig.json
    ├── app/
    │   ├── layout.tsx
    │   ├── page.tsx
    │   └── globals.css
    ├── components/
    │   ├── PatternDemo.tsx   # Main UI component
    │   └── ProbeCard.tsx     # Probe display component
    └── lib/
        └── spaceProbe.ts     # TypeScript implementation
```

## 🛠️ Java Implementation

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Running the Java Application

```bash
cd assginments/1/java
mvn clean compile exec:java
```

### Expected Output

The application demonstrates:
1. Creating Mars and Jupiter template probes using Builder Pattern
2. Registering templates with ConfigurationManager (Singleton)
3. Deploying multiple probes by cloning templates (Prototype)
4. Verifying template integrity (templates remain unchanged)

## 💻 Next.js Frontend

### Prerequisites
- Node.js 18 or higher
- npm or pnpm

### Running the Frontend

```bash
cd assginments/1/nextjs
npm install
npm run dev
```

Open [http://localhost:3000](http://localhost:3000) in your browser.

### Features

- **Interactive UI**: Visual demonstration of all three design patterns
- **Template Management**: View Mars and Jupiter probe templates
- **Probe Deployment**: Clone templates with optional customization
- **Real-time Updates**: See deployed probes instantly
- **Pattern Explanation**: Built-in explanations of each pattern

## 📖 How It Works

### 1. Template Creation (Builder Pattern)

```java
// Java
MissionControl director = new MissionControl();
MarsProbeBuilder marsBuilder = new MarsProbeBuilder();
director.constructStandardProbe(marsBuilder);
SpaceProbe marsTemplate = marsBuilder.getResult();
```

```typescript
// TypeScript
const director = new MissionControl();
const marsBuilder = new MarsProbeBuilder();
director.constructStandardProbe(marsBuilder);
const marsTemplate = marsBuilder.getResult();
```

### 2. Template Registration (Singleton Pattern)

```java
// Java
ConfigurationManager manager = ConfigurationManager.getInstance();
manager.addPrototype("MarsTemplate", marsTemplate);
```

```typescript
// TypeScript
const manager = ConfigurationManager.getInstance();
manager.addPrototype("MarsTemplate", marsTemplate);
```

### 3. Probe Deployment (Prototype Pattern)

```java
// Java
SpaceProbe probe = (SpaceProbe) manager.getClone("MarsTemplate");
probe.setPayloadMass(850.5);
```

```typescript
// TypeScript
const probe = manager.getClone("MarsTemplate") as SpaceProbe;
probe.payloadMass = 850.5;
```

## 🎯 Key Features

### Builder Pattern Benefits
- ✅ Step-by-step construction of complex objects
- ✅ Different representations using same construction process
- ✅ Separates construction logic from representation

### Prototype Pattern Benefits
- ✅ Efficient object creation through cloning
- ✅ Reduces initialization overhead
- ✅ Independent modifications of clones

### Singleton Pattern Benefits
- ✅ Single point of access to prototype registry
- ✅ Ensures consistency across application
- ✅ Thread-safe implementation

## 📝 Class Diagram Mapping

The implementation follows the PlantUML class diagram exactly:

- `SpaceMissionApp` → Client that uses all patterns
- `MissionControl` → Director for Builder pattern
- `SpaceProbeBuilder` → Builder interface
- `MarsProbeBuilder`, `JupiterProbeBuilder` → Concrete builders
- `SpaceProbe` → Product implementing IPrototype
- `IPrototype` → Prototype interface with deepClone()
- `ConfigurationManager` → Singleton managing prototypes

## 🧪 Testing the System

### Java Testing

Run the application to see console output demonstrating:
- Template creation
- Registration in singleton
- Probe cloning and deployment
- Template integrity verification

### Frontend Testing

1. Click "Initialize Mission Control System"
2. View the created templates (Mars and Jupiter)
3. Deploy probes using the buttons
4. Customize payload mass with custom deployment options
5. Delete deployed probes
6. Verify templates remain unchanged

## 🎨 UI Features

- **Dark Theme**: Space-themed UI with gradients
- **Responsive Design**: Works on mobile and desktop
- **Interactive Controls**: Deploy and manage probes
- **Pattern Visualization**: See patterns in action
- **Real-time Feedback**: Console logs for pattern operations

## 📚 Learning Outcomes

By exploring this project, you'll understand:

1. **When to use Builder Pattern**: Complex object construction
2. **When to use Prototype Pattern**: Object cloning for efficiency
3. **When to use Singleton Pattern**: Global state management
4. **Pattern Combination**: How patterns work together
5. **Implementation in Multiple Languages**: Java and TypeScript

## 🔍 Code Quality

- ✅ Follows SOLID principles
- ✅ Clean code architecture
- ✅ Comprehensive documentation
- ✅ Type-safe implementations
- ✅ Consistent naming conventions

## 📄 License

This project is for educational purposes as part of AIU coursework.

## 👥 Author

Developed as Assignment 1 for Software Design Patterns course.
