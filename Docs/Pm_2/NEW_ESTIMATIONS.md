# Project Estimation: AIU Trips & Events Management System

## Step 1: Introduction
This report provides the estimation and scheduling for the AIU Trips & Events Management System based on the Fibonacci Function Point Estimation method.  
The project is divided into phases, subsystems, and class-level features. Each feature is assigned a Fibonacci point according to its complexity, then converted into effort days to estimate the total duration and project size.

---

## Step 2: Approach
- Estimation Method: Fibonacci-based Function Point Estimation  
- Breakdown Level: From Use Cases → Subsystems → Classes  
- Conversion Rate: 1 Fibonacci Point = 5 Days  
- Team Size: 5 Members with specialized roles  
- Project Duration: 8 weeks (2 months) (40 working days per member → 200 developer-days total)

### Team Structure
The team consists of 5 members with the following role allocations:

1. **Member 1: Implementation & Deployment** (40 days)
   - Full-stack development
   - CI/CD pipeline setup and management
   - Production deployment and monitoring
   - DevOps responsibilities

2. **Member 2: Requirements Planning & Testing** (40 days)
   - Requirements gathering and documentation
   - Test planning and test case creation
   - User acceptance testing
   - Project planning support

3. **Member 3: Architecture & Design** (40 days)
   - System architecture design
   - Database schema design
   - High-level system design documentation
   - Architecture reviews

4. **Member 4: Architecture & Design** (40 days)
   - API design and documentation
   - Component architecture design
   - Integration design
   - Architecture implementation support

5. **Member 5: Estimation & Testing** (40 days)
   - Project estimation and effort tracking
   - Quality assurance and testing
   - Performance testing
   - Estimation accuracy analysis

---

## Step 3: Phase Breakdown and Estimation

### Phase 1 – Use Cases & Core Functionalities

| Subsystem | Class / Feature | Fibonacci Points | Effort (Days) |
|---|---:|---:|---:|
| Authentication | UserRegistration | 2 | 10 |
|  | Login | 1 | 5 |
|  | ResetPassword | 1 | 5 |
|  | EmailVerification | 1 | 5 |
| Event Management | EventCreator | 3 | 15 |
|  | EventEditor | 2 | 10 |
|  | EventRemover | 1 | 5 |
|  | CapacityChecker | 3 | 15 |
| Booking & Ticketing | BookingCreator | 3 | 15 |
|  | BookingCanceller | 2 | 10 |
|  | DuplicateChecker | 1 | 5 |
|  | TicketGenerator (QR) | 2 | 10 |
|  | TicketValidator | 1 | 5 |
| Notifications | NotificationSender | 1 | 5 |
|  | ReminderScheduler | 1 | 5 |
| Reports & Analytics | ReportGenerator | 2 | 10 |
|  | TrendAnalyzer | 3 | 15 |
|  | ExportManager | 1 | 5 |

**Subtotal (Phase 1): 32 Points → 160 Days**

---

### Phase 2 – Design

| Task Description | Points | Days |
|---|---:|---:|
| System Architecture — Define system layers and interactions | 3 | 15 |
| Database Schema — Create ERD and relationships | 2 | 10 |
| UML Diagrams — Class and sequence diagrams | 1 | 5 |
| UI Mockups — Interface sketches | 1 | 5 |

**Subtotal (Phase 2): 7 Points → 35 Days**

---

### Phase 3 – Implementation

| Task Description | Points | Days |
|---|---:|---:|
| Backend Logic — Business logic and API controllers | 3 | 15 |
| Frontend Pages — User interface & validation | 3 | 15 |
| Integrations — Email, QR, and payment APIs | 2 | 10 |
| Database Integration — CRUD operations | 2 | 10 |

**Subtotal (Phase 3): 10 Points → 50 Days**

---

### Phase 4 – Testing

| Task Description | Points | Days |
|---|---:|---:|
| Unit Testing — Test individual classes | 2 | 10 |
| Integration Testing — Test interactions between modules | 2 | 10 |
| User Acceptance Testing — Simulate user scenarios | 1 | 5 |
| Non-Functional Testing — Security & performance | 2 | 10 |

**Subtotal (Phase 4): 7 Points → 35 Days**

---

### Phase 5 – Deployment & Documentation

| Task Description | Points | Days |
|---|---:|---:|
| Deployment Setup — Docker and environment configs | 2 | 10 |
| Documentation — User & developer guide | 1 | 5 |
| Maintenance — Minor fixes after release | 1 | 5 |

**Subtotal (Phase 5): 4 Points → 20 Days**

---

## Step 4: Total Estimation

| Total Points | Conversion | Total Effort |
|---:|---:|---:|
| 60 Fibonacci Points | 1 Point = 5 Days | 200 Developer-Days (compressed) |

---

## Step 5: Capacity vs Effort Analysis

| Parameter | Calculation | Result |
|---|---:|---:|
| Team Capacity | 5 Members × 40 Days | 200 Developer-Days |
| Required Effort From Estimation | — | 200 Developer-Days |
| Member 1 (Implementation & Deployment) | Full-stack + DevOps | 40 days |
| Member 2 (Requirements Planning & Testing) | Requirements + Testing | 40 days |
| Member 3 (Architecture & Design) | System Architecture | 40 days |
| Member 4 (Architecture & Design) | API & Component Design | 40 days |
| Member 5 (Estimation & Testing) | QA + Estimation | 40 days |
| **Buffer Days** | For contingency | 0 days (aggressive timeline) |

---

## Step 6: Schedule Summary
The estimated effort of 200 developer-days fits the 8-week (2-month) duration with the new specialized team structure. Each member contributes according to their role specialization:
- Implementation & Deployment lead handles the core development work
- Two Architecture members ensure robust system design
- Requirements Planning & Testing member ensures proper documentation and testing alignment
- Estimation & Testing member maintains quality and tracks project progress

This structure ensures proper separation of concerns, better quality control, and efficient resource utilization with an aggressive, compressed timeline.

---

## Step 7: Final Conclusion
The total estimation for the AIU Trips & Events Management System equals 60 Fibonacci Points (200 developer-days compressed). This estimation fits within the 8-week project duration for a 5-member team with specialized roles. The breakdown ensures:
- Clear role separation and accountability
- Balanced workload across specializations
- Better quality control through dedicated testing and estimation roles
- Robust architecture through two dedicated architecture members
- Efficient implementation through dedicated development and deployment specialist

### Total Effort Summary
- Total Fibonacci Estimate: 60 Points  
- Total Effort: 200 Days (all allocated, no buffer - aggressive schedule)  
- Fits in 8 weeks with intensive role specialization
- Utilization Rate: 100% (maximum efficiency required)

---

## Assumptions
- Team Capacity: 5 Members (specialized roles)
- Workdays per Week: 5 Days  
- Total Working Duration: 8 weeks (2 months) (≈ 40 working days per member)  
- Conversion Rate: 1 Fibonacci Point = 5 Days  
- Role-based allocation ensures expertise in each area
- Total Duration: 60 Fibonacci Points distributed across specialized roles (compressed timeline)
- Intensive work schedule with maximum efficiency required

---

## Team Role Distribution

### Member 1: Implementation & Deployment (40 days allocated)
- Core development: 28 days
- CI/CD setup and management: 5 days
- Production deployment: 4 days
- Monitoring and maintenance: 3 days

### Member 2: Requirements Planning & Testing (40 days allocated)
- Requirements documentation: 5 days
- Test planning: 8 days
- User acceptance testing: 20 days
- Project planning support: 7 days

### Member 3: Architecture & Design (40 days allocated)
- System architecture design: 10 days
- Database schema design: 8 days
- High-level design documentation: 12 days
- Architecture reviews: 10 days

### Member 4: Architecture & Design (40 days allocated)
- API design and documentation: 10 days
- Component architecture: 12 days
- Integration design: 10 days
- Implementation support: 8 days

### Member 5: Estimation & Testing (40 days allocated)
- Project estimation: 6 days
- Quality assurance: 20 days
- Performance testing: 8 days
- Estimation analysis: 6 days

---

## Data Files
All charts, diagrams, and tabular data are available in CSV format in the `csv_data/` directory:
- `team_allocation.csv` - Weekly team allocation by role
- `estimation_breakdown.csv` - Detailed estimation breakdown by phase
- `feature_allocation.csv` - Feature-wise effort distribution
- `burndown_chart.csv` - Project burndown data
- `velocity_analysis.csv` - Sprint velocity tracking
- `dependency_tasks.csv` - Task dependencies and critical path
- `story_points_by_epic.csv` - Story points distribution by epic
