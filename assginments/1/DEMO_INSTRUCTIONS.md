# Space Probe Configuration System - Demo Instructions

This document provides step-by-step instructions for demonstrating the Space Probe Configuration System to the TA.

## 📁 Project Location

All assignment files are located in: `/assginments/1/`

This is a **separate assignment** and does **not** affect the main project in the root directory.

## 🎯 What This System Demonstrates

Three design patterns working together:
1. **Builder Pattern** - Step-by-step construction of complex objects
2. **Prototype Pattern** - Efficient deep cloning
3. **Singleton Pattern** - Single instance management

## 🚀 Demo Part 1: Java Backend (CLI Demo)

### Running the Java Application

```bash
cd assginments/1/java-backend
./compile-and-run.sh
```

### What the Demo Shows

The console output will demonstrate:

1. **Builder Pattern in Action**
   - Creates Mars probe template using MarsProbeBuilder
   - Creates Jupiter probe template using JupiterProbeBuilder
   - MissionControl (Director) orchestrates the construction
   - Shows all components being configured

2. **Singleton Pattern Verification**
   - ConfigurationManager instance is created once
   - getInstance() is called twice, returns same instance
   - Verification: `manager == manager2 ? true`
   - Templates are registered in the single instance

3. **Prototype Pattern Demonstration**
   - Clones Mars template 3 times (as required)
   - Clones Jupiter template 1 time (as required)
   - Each clone has modified payload mass
   - Shows independence: original templates remain unchanged

### Expected Output

```
========================================
Space Probe Configuration System
========================================

STEP 1: Building Template Probes using Builder Pattern
[Shows Mars and Jupiter templates being built]

STEP 2: Registering Templates in ConfigurationManager (Singleton)
ConfigurationManager instance created (Singleton)
Singleton verification: manager == manager2 ? true

STEP 3: Creating Deployment Probes using Prototype Pattern
[Shows 3 Mars clones and 1 Jupiter clone with modified masses]

STEP 4: Verifying Independence of Clones (Deep Copy)
[Shows original templates unchanged at 150.0 kg and 300.0 kg]

STEP 5: Mission Deployment Summary
[Summary statistics]
```

### Key Points to Highlight

- ✅ Builder creates complex objects step-by-step
- ✅ Singleton ensures only one ConfigurationManager exists
- ✅ Prototype enables efficient cloning
- ✅ Deep copy ensures independence (templates unchanged)
- ✅ All required components present (5 attributes per probe)

## 🌐 Demo Part 2: Next.js Frontend (Web Demo)

### Running the Web Application

```bash
cd assginments/1/nextjs-frontend
npm install  # Only needed first time
npm run dev
```

Then open: http://localhost:3000

### Interactive Demo Flow

#### Step 1: Show Singleton Pattern
- Point out the green "Singleton Pattern" box at the top
- Show "Instance Count: 1" (always)
- Explain: Only one ConfigurationManager exists

#### Step 2: Build a Mars Probe Template
- Click "Build Mars Probe Template"
- Watch the Builder Visualizer animate through 5 steps:
  1. Mission Target ✓
  2. Propulsion System ✓
  3. Power Source ✓
  4. Scientific Instruments ✓
  5. Payload Mass ✓
- Show the completed Mars template in yellow border
- Point out "Registered Templates: 1" in Singleton indicator

#### Step 3: Build a Jupiter Probe Template
- Click "Build Jupiter Probe Template"
- Watch the same Builder process for Jupiter
- Now show "Registered Templates: 2" in Singleton indicator
- Both templates displayed in "Template Probes" section

#### Step 4: Demonstrate Prototype Pattern
- Click "Clone" on Mars template
- Show the Prototype Visualizer appears
- The visualizer shows:
  - Original Mars template at top
  - Arrow showing deepClone() operation
  - New clone in the list below
- Click "Clone" 2 more times on Mars (total 3 Mars clones)
- Click "Clone" once on Jupiter (1 Jupiter clone)

#### Step 5: Modify Clones (Independence Test)
- Click "Modify" on any clone
- Change payload mass (e.g., from 150.0 to 155.5)
- Click "Save"
- Show in Prototype Visualizer:
  - Original template still shows 150.0 kg
  - Clone shows 155.5 kg
  - This proves deep copy independence

#### Step 6: Pattern Summary
- Scroll to bottom "Design Patterns Summary" section
- Explain each pattern box:
  - Builder: Step-by-step construction
  - Prototype: Efficient cloning
  - Singleton: Single instance management

### Interactive Features to Demonstrate

1. **Real-time Animations**
   - Builder steps animate with pulse effect
   - Progress indicators show completion

2. **Visual Distinctions**
   - Templates: Yellow border, "Template Probe" label
   - Clones: White background, no template label
   - Mars probes: Red dot
   - Jupiter probes: Orange dot

3. **Data Integrity**
   - Modify multiple clones
   - Show original templates never change
   - This proves deep copy works correctly

4. **Reset Functionality**
   - Click "Reset All" to clear everything
   - Start fresh demonstration

## 📊 Assignment Requirements Checklist

During the demo, verify these requirements are met:

### SpaceProbe Components (Required)
- ✅ Propulsion System
- ✅ Power Source
- ✅ Scientific Instruments (4 items, minimum 3 required)
- ✅ Mission Target
- ✅ Payload Mass
- ✅ describe() method (Java only)

### Builder Pattern (30% of grade)
- ✅ SpaceProbeBuilder interface
- ✅ MarsProbeBuilder concrete builder
- ✅ JupiterProbeBuilder concrete builder
- ✅ MissionControl director class
- ✅ Step-by-step construction
- ✅ Hides internal representation

### Prototype Pattern (20% of grade)
- ✅ IPrototype interface with deepClone()
- ✅ SpaceProbe implements cloning
- ✅ Deep copy (List is properly copied)
- ✅ Independence verification
- ✅ Post-cloning modifications
- ✅ Templates remain unchanged

### Singleton Pattern (20% of grade)
- ✅ Private constructor
- ✅ Static getInstance() method
- ✅ Thread-safe (eager initialization)
- ✅ Single instance verification
- ✅ Manages prototype registry

### Working Demo (20% of grade)
- ✅ Java CLI demonstration
- ✅ Next.js web demonstration
- ✅ All three patterns working together
- ✅ Creates 3 Mars + 1 Jupiter clones
- ✅ Shows modifications don't affect templates

### Documentation (10% of grade)
- ✅ UML Class Diagram (class_digram.pu)
- ✅ Well-documented code (inline comments)
- ✅ Java backend README
- ✅ Next.js frontend README
- ✅ Overall assignment README
- ✅ Demo instructions (this file)

## 🎓 Discussion Points for TA

### Design Decisions

1. **Why Builder?**
   - Space probes have many complex components
   - Different configurations for different missions
   - Step-by-step construction is clearer than constructor with many parameters

2. **Why Prototype?**
   - Template probes are expensive to construct
   - Many missions need similar configurations
   - Cloning is faster than rebuilding

3. **Why Singleton?**
   - Only one configuration manager should exist
   - Centralized access to all templates
   - Prevents configuration conflicts

### Challenges Overcome

1. **Deep Copy Implementation**
   - Challenge: Ensuring List objects are properly cloned
   - Solution: Create new ArrayList with copied elements
   - Verification: Modifications to clones don't affect templates

2. **Thread Safety**
   - Challenge: Ensuring Singleton is thread-safe
   - Solution: Eager initialization (instance created at class load time)
   - Benefit: Simple and guaranteed thread-safe

3. **Pattern Integration**
   - Challenge: Making three patterns work together
   - Solution: Clear separation of concerns
   - Flow: Build → Register (Singleton) → Clone

### Real-World Applications

1. **Software Deployment**
   - Template configurations for servers
   - Clone and customize for specific environments

2. **Game Development**
   - Character templates (Builder)
   - Clone for enemy instances (Prototype)
   - Game manager (Singleton)

3. **Document Management**
   - Document templates (Builder)
   - Copy and customize (Prototype)
   - Central template library (Singleton)

## 📸 Screenshots to Take (if required)

If you need to submit screenshots or a video:

1. Java console output showing all 5 steps
2. Web UI showing Singleton indicator
3. Web UI showing Builder animation
4. Web UI showing Template probes section
5. Web UI showing Prototype visualizer
6. Web UI showing clone modification
7. Web UI showing design patterns summary

## 🔍 Code Review Points

If TA wants to review code:

### Java Files to Show
- `SpaceProbe.java` - Product with deepClone()
- `SpaceProbeBuilder.java` - Builder interface
- `MarsProbeBuilder.java` - Concrete builder example
- `ConfigurationManager.java` - Singleton implementation
- `SpaceMissionApp.java` - Demo client

### TypeScript Files to Show
- `app/page.tsx` - Main application logic
- `app/components/BuilderVisualizer.tsx` - Builder visualization
- `app/components/SingletonIndicator.tsx` - Singleton display
- `app/components/PrototypeVisualizer.tsx` - Prototype demonstration
- `app/types/spaceprobe.ts` - Type definitions

## ⚡ Quick Start Commands

```bash
# Java Demo
cd assginments/1/java-backend && ./compile-and-run.sh

# Web Demo
cd assginments/1/nextjs-frontend && npm install && npm run dev
# Then open http://localhost:3000
```

## 📝 Important Notes

- This is assignment 1, separate from the main project
- All files are in `/assginments/1/` directory
- Both Java and Next.js implementations demonstrate the same patterns
- Web UI provides visual verification of pattern behaviors
- Deep copy is properly implemented (List objects are cloned)
- Thread-safety is ensured (eager initialization)

## ✅ Final Checklist Before Demo

- [ ] Java code compiles successfully
- [ ] Java demo runs without errors
- [ ] Web application builds successfully
- [ ] Web application runs on localhost:3000
- [ ] Can build both Mars and Jupiter templates
- [ ] Can clone templates multiple times
- [ ] Can modify clones without affecting templates
- [ ] Singleton shows only 1 instance
- [ ] All documentation is present
- [ ] Understand the design decisions and can explain them

---

**Status**: Ready for TA evaluation
**Estimated Demo Time**: 10-15 minutes
**Complexity**: Medium (3 design patterns integrated)
**Completeness**: All requirements met
