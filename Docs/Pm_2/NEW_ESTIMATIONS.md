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
- Project Duration: 2 Months (40 working days per member → 200 developer-days total)

### Team Structure
The team consists of 5 members with the following role allocations:

1. **Member 1: Implementation and Deployment** (58 days)
   - Full-stack development
   - CI/CD pipeline setup and management
   - Production deployment and monitoring
   - DevOps responsibilities

2. **Member 2: Requirements, Planning and Testing** (25 days)
   - Requirements gathering and documentation
   - Test planning and test case creation
   - User acceptance testing
   - Project planning support

3. **Member 3: Architecture and System Design** (41 days)
   - System architecture design
   - Database schema design
   - High-level system design documentation
   - Architecture reviews

4. **Member 4: Architecture and System Design** (38 days)
   - API design and documentation
   - Component architecture design
   - Integration design
   - Architecture implementation support

5. **Member 5: Estimation and Testing** (38 days)
   - Project estimation and effort tracking
   - Quality assurance and testing
   - Performance testing
   - Estimation accuracy analysis

---

## Step 3: Phase Breakdown and Estimation

### Consolidated Breakdown (40 FP → 200 Days)

| Subsystem | Features | Fibonacci Points | Effort (Days) |
|---|---|---:|---:|
| Authentication | User Registration, Login, Reset, Verification | 5 | 25 |
| Event Management | Creator, Editor, Remover, Capacity Checker | 9 | 45 |
| Booking & Ticketing | Creator, Canceller, Checker, QR Generator, Validator | 9 | 45 |
| Notifications | Sender, Reminder Scheduler | 2 | 10 |
| Reports & Analytics | Generator, Trend Analyzer, Export Manager | 6 | 30 |
| Design, Implementation, Testing & Deployment | Additional project phases | 9 | 45 |

**Total**: 40 Points → 200 Days

---

## Step 4: Total Estimation

| Total Points | Conversion | Total Effort |
|---:|---:|---:|
| 40 Fibonacci Points | 1 Point = 5 Days | 200 Developer-Days |

---

## Step 5: Capacity vs Effort Analysis

| Parameter | Calculation | Result |
|---|---:|---:|
| Team Capacity | 5 Members × 40 Days | 200 Developer-Days |
| Required Effort From Estimation | — | 200 Developer-Days |
| Member 1 (Implementation & Deployment) | Full-stack + DevOps | 58 days |
| Member 2 (Requirements, Planning & Testing) | Requirements + Testing | 25 days |
| Member 3 (Architecture & Design) | System Architecture | 41 days |
| Member 4 (Architecture & Design) | API & Component Design | 38 days |
| Member 5 (Estimation & Testing) | QA + Estimation | 38 days |
| **Buffer Days** | For contingency | 0 days |

---

## Step 6: Schedule Summary
The estimated effort of 200 developer-days fits the 2-month duration with the new specialized team structure. Each member contributes according to their role specialization:
- Implementation and Deployment lead handles the core development work
- Two Architecture members ensure robust system design
- Requirements and Planning member ensures proper documentation and testing alignment
- Estimation and Testing member maintains quality and tracks project progress

This structure ensures proper separation of concerns, better quality control, and efficient resource utilization.

---

## Step 7: Final Conclusion
The total estimation for the AIU Trips & Events Management System equals 40 Fibonacci Points (200 developer-days). This estimation fits within the 2-month project duration for a 5-member team with specialized roles. The breakdown ensures:
- Clear role separation and accountability
- Balanced workload across specializations
- Better quality control through dedicated testing and estimation roles
- Robust architecture through two dedicated architecture members
- Efficient implementation through dedicated development and deployment specialist

### Total Effort Summary
- Total Fibonacci Estimate: 40 Points  
- Total Effort: 200 Days (no buffer)  
- Fits in 8 weeks with role specialization
- Utilization Rate: 100% (balanced to capacity)

---

## Assumptions
- Team Capacity: 5 Members (specialized roles)
- Workdays per Week: 5 Days  
- Total Working Duration: 2 Months (≈ 40 working days per member)  
- Conversion Rate: 1 Fibonacci Point = 5 Days  
- Role-based allocation ensures expertise in each area
- Total Duration: 40 Fibonacci Points distributed across specialized roles

---

## Team Role Distribution

### Member 1: Implementation and Deployment (58 days allocated)
- Core development: 40 days
- CI/CD setup and management: 8 days
- Production deployment: 6 days
- Monitoring and maintenance: 4 days

### Member 2: Requirements, Planning and Testing (25 days allocated)
- Requirements documentation: 6 days
- Test planning: 9 days
- User acceptance testing: 8 days
- Project planning support: 2 days

### Member 3: Architecture and System Design (41 days allocated)
- System architecture design: 12 days
- Database schema design: 10 days
- High-level design documentation: 11 days
- Architecture reviews: 8 days

### Member 4: Architecture and System Design (38 days allocated)
- API design and documentation: 12 days
- Component architecture: 12 days
- Integration design: 8 days
- Implementation support: 6 days

### Member 5: Estimation and Testing (38 days allocated)
- Project estimation: 8 days
- Quality assurance: 16 days
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
