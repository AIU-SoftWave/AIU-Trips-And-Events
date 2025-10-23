# Space Probe Configuration System - Next.js Frontend

This is an interactive web application demonstrating the **Builder**, **Prototype**, and **Singleton** design patterns through a Space Probe Configuration System.

## Features

### Interactive Visualizations

1. **Builder Pattern Visualization**
   - Watch the step-by-step construction of space probes
   - See each component being added (Propulsion, Power, Instruments, etc.)
   - Visual progress indicator for the build process

2. **Singleton Pattern Demonstration**
   - Shows the single instance of ConfigurationManager
   - Displays registered template count
   - Verifies singleton constraint with instance counter

3. **Prototype Pattern Showcase**
   - Visual representation of template-to-clone relationship
   - Compare original templates with cloned instances
   - Verify deep copy independence

### User Interactions

- **Build Templates**: Create Mars and Jupiter probe templates using the Builder pattern
- **Clone Probes**: Create deployment probes by cloning templates (Prototype pattern)
- **Modify Clones**: Adjust payload mass of cloned probes without affecting templates
- **Reset System**: Clear all probes and start fresh
- **Real-time Updates**: See patterns in action with smooth animations

## Tech Stack

- **Framework**: Next.js 16 (with App Router)
- **Language**: TypeScript
- **Styling**: Tailwind CSS
- **State Management**: React Hooks (useState)

## Getting Started

### Prerequisites

- Node.js 18+ 
- npm, yarn, pnpm, or bun

### Installation

```bash
# Install dependencies
npm install
```

### Development

```bash
# Run the development server
npm run dev
```

Open [http://localhost:3000](http://localhost:3000) in your browser to see the application.

### Production Build

```bash
# Build for production
npm run build

# Start production server
npm start
```

## Project Structure

```
nextjs-frontend/
├── app/
│   ├── components/
│   │   ├── ProbeCard.tsx              # Display space probe details
│   │   ├── BuilderVisualizer.tsx      # Visualize Builder pattern
│   │   ├── SingletonIndicator.tsx     # Show Singleton pattern
│   │   └── PrototypeVisualizer.tsx    # Demonstrate Prototype pattern
│   ├── types/
│   │   └── spaceprobe.ts              # TypeScript type definitions
│   ├── layout.tsx                     # Root layout
│   ├── page.tsx                       # Main application page
│   └── globals.css                    # Global styles
├── public/                            # Static assets
├── package.json
└── README.md                          # This file
```

## Design Patterns in Action

### Builder Pattern
The Builder pattern is visualized when you click "Build Mars Probe Template" or "Build Jupiter Probe Template". Watch as the system:
1. Initializes the builder
2. Sets the mission target
3. Configures propulsion system
4. Adds power source
5. Installs scientific instruments
6. Sets payload mass

### Singleton Pattern
The ConfigurationManager singleton is displayed at the top of the page, showing:
- Instance count (always 1)
- Number of registered templates
- Key singleton features explanation

### Prototype Pattern
When you clone a template, the Prototype Visualizer shows:
- The original template
- All clones created from that template
- Modifications to clones (payload mass changes)
- Visual confirmation that templates remain unchanged

## How to Use

1. **Start by Building Templates**
   - Click "Build Mars Probe Template" or "Build Jupiter Probe Template"
   - Watch the Builder pattern create the probe step-by-step
   - Templates are automatically registered in the ConfigurationManager (Singleton)

2. **Clone Templates**
   - Once templates are created, click the "Clone" button on any template
   - A new deployment probe is created using the Prototype pattern
   - The Prototype Visualizer shows the cloning relationship

3. **Modify Clones**
   - Click "Modify" on any cloned probe
   - Change the payload mass
   - Save to see that the original template remains unchanged

4. **Observe Pattern Behaviors**
   - Templates are marked with a yellow border and "Template Probe" label
   - Deployment probes have a white background
   - The Singleton shows it's managing all templates
   - The Prototype Visualizer proves deep copy independence

## Color Coding

- **Mars Probes**: Red indicator dot
- **Jupiter Probes**: Orange indicator dot
- **Template Probes**: Yellow border
- **Deployment Probes**: White background with gray border

## Components Overview

### ProbeCard
Displays detailed information about a space probe including:
- Mission target
- Propulsion system
- Power source
- Scientific instruments
- Payload mass
- Action buttons (Clone/Modify)

### BuilderVisualizer
Shows the Builder pattern in action with:
- Step-by-step progress indicators
- Current step highlighting
- Completed step checkmarks
- Build progress statistics

### SingletonIndicator
Demonstrates the Singleton pattern with:
- Instance count verification
- Registered templates count
- Key features explanation
- Visual confirmation of singleton constraint

### PrototypeVisualizer
Illustrates the Prototype pattern with:
- Template display
- Clone list with modifications
- Deep copy verification points
- Visual template-to-clone relationship

## Assignment Requirements Met

✅ Builder Pattern implemented with step-by-step construction
✅ Prototype Pattern with deep cloning capability
✅ Singleton Pattern for configuration management
✅ Interactive demonstration of all patterns
✅ Visual verification of pattern behaviors
✅ Clear separation of templates and deployment instances

## Learn More

- [Next.js Documentation](https://nextjs.org/docs)
- [React Documentation](https://react.dev)
- [Tailwind CSS](https://tailwindcss.com)
- [TypeScript](https://www.typescriptlang.org)
