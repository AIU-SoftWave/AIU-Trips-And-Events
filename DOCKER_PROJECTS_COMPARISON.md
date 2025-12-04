# Docker Setup Comparison - Both Projects

This document compares the Docker setup for both projects in the repository.

## Projects Overview

### Project 1: Milestones/PM_3/Project_without_DP_UML
- **Purpose:** Implementation with 12 Design Patterns
- **Location:** `Milestones/PM_3/Project_without_DP_UML/`

### Project 2: Main Project
- **Purpose:** Main application without design patterns
- **Location:** `/Project/`

## Port Allocation

Both projects can run simultaneously with different port mappings:

| Service | PM3 Project | Main Project |
|---------|-------------|--------------|
| Frontend | 3000 | 3001 |
| Backend | 8080 | 8081 |
| PostgreSQL | 5432 | 5433 |

## Quick Start Comparison

### PM3 Project (Design Patterns)
```bash
cd Milestones/PM_3/Project_without_DP_UML
./start.sh
# Access frontend at http://localhost:3000
# Access backend at http://localhost:8080
```

### Main Project
```bash
cd Project
./start.sh
# Access frontend at http://localhost:3001
# Access backend at http://localhost:8081
```

## Running Both Projects Simultaneously

Yes! You can run both projects at the same time:

```bash
# Terminal 1 - Start PM3 Project
cd Milestones/PM_3/Project_without_DP_UML
./start.sh

# Terminal 2 - Start Main Project  
cd Project
./start.sh
```

Then access:
- PM3 Frontend: http://localhost:3000
- PM3 Backend: http://localhost:8080
- Main Frontend: http://localhost:3001
- Main Backend: http://localhost:8081

## File Structure Comparison

Both projects have identical Docker setup structure:

```
project-root/
├── docker-compose.yml
├── start.sh
├── .env.example
├── DOCKER_SETUP.md
├── database/
│   └── init.sql
├── backend/
│   ├── Dockerfile
│   ├── .dockerignore
│   ├── pom.xml (updated with PostgreSQL)
│   └── src/main/
│       ├── java/com/aiu/trips/config/
│       │   ├── DataInitializer.java (updated)
│       │   └── DatabaseSeeder.java (NEW)
│       └── resources/
│           └── application-docker.properties (NEW)
└── frontend/
    ├── Dockerfile
    ├── .dockerignore
    └── next.config.ts (updated)
```

## Database Seeding

Both projects use identical seeding logic:

**Users Created (Both):**
- 1 Admin: `admin@aiu.edu`
- 3 Students: `john.doe@aiu.edu`, `jane.smith@aiu.edu`, `mike.johnson@aiu.edu`
- 1 Organizer: `organizer@aiu.edu`

**Events Created (Both):**
- AI and Machine Learning Conference 2025
- Mountain Hiking Adventure
- Annual Career Fair 2025
- Summer Beach Getaway
- Web Development Workshop
- International Cultural Festival

**Data Seeded (Both):**
- 5 users
- 6 events/trips
- 3 bookings
- 3 feedback entries
- 5 notifications

## Key Differences

### PM3 Project Unique Features
- ✅ 12 Design Patterns implemented
- ✅ Additional pattern classes (Factory, Builder, Strategy, etc.)
- ✅ Pattern documentation (DESIGN_PATTERNS_IMPLEMENTATION.md, etc.)
- ✅ 8 documentation files total

### Main Project Unique Features
- ✅ Different port mappings (no conflicts)
- ✅ Runs on ports 3001, 8081, 5433
- ✅ Can run alongside PM3 project

### Common Features (Both)
- ✅ Docker Compose with 3 services
- ✅ PostgreSQL 16 database
- ✅ Spring Boot backend
- ✅ Next.js frontend
- ✅ Automated database seeding
- ✅ Health checks
- ✅ Complete documentation

## Docker Compose Differences

### PM3 Project (docker-compose.yml)
```yaml
ports:
  - "3000:3000"  # frontend
  - "8080:8080"  # backend
  - "5432:5432"  # postgres
networks:
  - aiu-network
volumes:
  - postgres_data
```

### Main Project (docker-compose.yml)
```yaml
ports:
  - "3001:3000"  # frontend
  - "8081:8080"  # backend
  - "5433:5432"  # postgres (mapped to different host port)
networks:
  - aiu-network-main
volumes:
  - postgres_data_main
```

## Environment Variables

Both projects use similar environment variables but with different API URLs:

### PM3 Project
```env
NEXT_PUBLIC_API_URL=http://localhost:8080
cors.allowed-origins=http://localhost:3000
```

### Main Project
```env
NEXT_PUBLIC_API_URL=http://localhost:8081
cors.allowed-origins=http://localhost:3001
```

## Network Isolation

Each project has its own Docker network:
- PM3 Project: `aiu-network`
- Main Project: `aiu-network-main`

This ensures complete isolation and no interference when running both.

## Volume Naming

Each project has its own volume:
- PM3 Project: `postgres_data`
- Main Project: `postgres_data_main`

Data is completely separate between the two projects.

## Documentation

### PM3 Project Documentation (8 files)
1. DESIGN_PATTERNS_IMPLEMENTATION.md
2. PATTERN_TO_DIAGRAM_MAPPING.md
3. README.md
4. IMPLEMENTATION_STATISTICS.md
5. QUICK_REFERENCE.md
6. PROJECT_COMPLETION_SUMMARY.md
7. DOCKER_SETUP.md
8. DOCKER_IMPLEMENTATION_SUMMARY.md

### Main Project Documentation (1 file)
1. DOCKER_SETUP.md

## Common Commands

Both projects use identical commands:

```bash
# Start
./start.sh
# or
docker-compose up -d

# Stop
docker-compose down

# View logs
docker-compose logs -f

# Rebuild
docker-compose up -d --build

# Reset database
docker-compose down -v
docker-compose up -d
```

## When to Use Which Project

### Use PM3 Project When:
- Learning or demonstrating design patterns
- Testing pattern implementations
- Understanding SOLID principles
- Reviewing pattern documentation

### Use Main Project When:
- Running the base application
- Testing without pattern overhead
- Comparing performance
- Running alongside PM3 for comparison

## Stopping Both Projects

```bash
# Stop PM3 Project
cd Milestones/PM_3/Project_without_DP_UML
docker-compose down

# Stop Main Project
cd Project
docker-compose down
```

## Resource Usage

When running both projects simultaneously:
- **RAM:** ~8GB recommended (4GB per project)
- **Storage:** ~10GB total (Docker images + volumes)
- **CPU:** 4 cores recommended

## Troubleshooting

### Port Conflicts
If you get port conflicts, ensure you're using the correct ports for each project:
- PM3: 3000, 8080, 5432
- Main: 3001, 8081, 5433

### Network Issues
Each project has its own network. Check with:
```bash
docker network ls
```

You should see:
- `project_without_dp_uml_aiu-network`
- `project_aiu-network-main`

### Volume Issues
Each project has its own volume. Check with:
```bash
docker volume ls
```

You should see:
- `project_without_dp_uml_postgres_data`
- `project_postgres_data_main`

## Summary

✅ **Both projects fully Dockerized**
✅ **Can run simultaneously**
✅ **No port conflicts**
✅ **Identical sample data**
✅ **Complete isolation**
✅ **Easy to switch between**

Choose the project that fits your needs, or run both for comparison!
