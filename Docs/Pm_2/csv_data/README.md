# CSV Data Files for PM_2

This directory contains all charts, diagrams, and tabular data in CSV format for easy import into project management tools, spreadsheet applications, and visualization software.

## Files Overview

### 1. team_allocation.csv
**Purpose:** Weekly team allocation by role across the 8-week project timeline

**Columns:**
- Week: Project week range (e.g., 1-2, 3-4)
- Phase: Project phase name
- Member 1 (Implementation & Deployment): Activities for implementation lead
- Member 2 (Requirements Planning & Testing): Activities for testing lead
- Member 3 (Architecture & Design): Activities for architecture lead 1
- Member 4 (Architecture & Design): Activities for architecture lead 2
- Member 5 (Estimation & Testing): Activities for QA and estimation lead

**Use Cases:**
- Resource planning
- Weekly standup preparation
- Sprint planning
- Team capacity visualization

---

### 2. burndown_chart.csv
**Purpose:** Sprint burndown data showing planned vs actual story point completion

**Columns:**
- Week: Project week number (0-8)
- Planned Remaining Story Points: Expected remaining work
- Actual Remaining Story Points: Real remaining work
- Variance: Difference between planned and actual

**Use Cases:**
- Progress tracking
- Velocity calculation
- Sprint retrospectives
- Stakeholder reporting

---

### 3. velocity_analysis.csv
**Purpose:** Sprint velocity analysis across 4 sprints (2 weeks each)

**Columns:**
- Sprint: Sprint identifier
- Week Range: Weeks covered by sprint
- Planned Velocity (SP/week): Expected story points per week
- Actual Velocity (SP/week): Achieved story points per week
- Variance (%): Percentage difference
- Notes: Sprint-specific observations

**Use Cases:**
- Performance analysis
- Estimation accuracy improvement
- Team capacity planning
- Historical velocity reference

---

### 4. estimation_breakdown.csv
**Purpose:** Detailed Fibonacci-based estimation breakdown by phase and feature

**Columns:**
- Phase: Project phase (Phase 1-5)
- Subsystem/Feature: Specific functionality or task
- Fibonacci Points: Complexity rating
- Effort (Days): Converted effort in days
- Team Members Assigned: Responsible team members

**Use Cases:**
- Effort tracking
- Budget planning
- Task assignment
- Estimation validation

---

### 5. feature_allocation.csv
**Purpose:** Feature-wise effort distribution across all team members

**Columns:**
- Feature: Major system feature
- Total Dev-Days: Total effort required
- Member 1-5 columns: Individual effort allocation
- Utilization Rate: Resource utilization percentage

**Use Cases:**
- Workload balancing
- Feature planning
- Capacity management
- Individual contribution tracking

---

### 6. dependency_tasks.csv
**Purpose:** Complete task list with dependencies and critical path identification

**Columns:**
- Task ID: Unique task identifier (A-V)
- Task Name: Descriptive task name
- Duration (Days): Task duration
- Dependencies: Prerequisite tasks
- Team Members: Assigned team members
- Critical Path: Yes/No indicator

**Use Cases:**
- Schedule planning
- Critical path analysis
- Dependency management
- Risk identification

---

### 7. story_points_by_epic.csv
**Purpose:** Story point distribution across project epics

**Columns:**
- Epic: Feature epic name
- User Stories: List of user story IDs
- Story Points: Total story points
- Percentage: Percentage of total project scope
- Priority: Feature priority level

**Use Cases:**
- Epic planning
- Scope management
- Priority setting
- Progress tracking by feature area

---

## Team Structure

The project uses a specialized team structure with 5 members:

### Member 1: Implementation and Deployment (73 days)
- Full-stack development (backend + frontend)
- CI/CD pipeline setup and maintenance
- Production deployment and monitoring
- DevOps and infrastructure management

### Member 2: Requirements, Planning and Testing (32 days)
- Requirements gathering and documentation
- Test strategy and test case creation
- User acceptance testing execution
- Requirements traceability and validation

### Member 3: Architecture and System Design (50 days)
- System architecture design and documentation
- Database schema design and optimization
- High-level system design
- Architecture review and validation

### Member 4: Architecture and System Design (50 days)
- API design and documentation
- Component architecture and integration design
- Technical documentation
- Architecture implementation support

### Member 5: Estimation and Testing (50 days)
- Project estimation and effort tracking
- Quality assurance and test automation
- Performance testing and optimization
- Estimation accuracy analysis and reporting

---

## How to Use These Files

### For Project Management Tools
1. Import CSV files into tools like:
   - Microsoft Project
   - JIRA
   - Asana
   - Monday.com
   - Trello

2. Use for:
   - Gantt chart generation
   - Resource allocation
   - Progress tracking
   - Report generation

### For Spreadsheet Analysis
1. Open in:
   - Microsoft Excel
   - Google Sheets
   - LibreOffice Calc

2. Create:
   - Custom charts and graphs
   - Pivot tables
   - What-if scenarios
   - Budget forecasts

### For Visualization Tools
1. Import into:
   - Tableau
   - Power BI
   - Grafana
   - Custom dashboards

2. Generate:
   - Real-time dashboards
   - Progress indicators
   - Team performance metrics
   - Burndown/burnup charts

---

## Data Accuracy

All CSV data is synchronized with the following source documents:
- `index.md` - Main project management plan
- `NEW_ESTIMATIONS.md` - Updated estimation with new team structure
- `DEPENDENCY_GRAPH_DETAILS.md` - Detailed dependency analysis
- `FEATURE_DEMO_DETAILS.md` - Feature demonstration details

Last Updated: October 2025

---

## Support

For questions about this data:
1. Review the main documentation in `index.md`
2. Check supporting documents for detailed explanations
3. Refer to `NEW_ESTIMATIONS.md` for estimation methodology
4. See `README.md` in parent directory for complete file structure
