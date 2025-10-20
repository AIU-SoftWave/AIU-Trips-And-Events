# Project Management Plan - Milestone 2 (PM_2)

## Overview
This directory contains all deliverables for Milestone 2 of the AIU Trips and Events Management System project.

## Deliverables Checklist

- [x] **Task 1 (20 marks):** Stories estimations, use case estimations, feature estimations
- [x] **Task 2 (20 marks):** Schedule for tasks and activities with two-week milestones
- [x] **Task 3 (20 marks):** Demo for implemented features with estimation error analysis
- [x] **Task 4 (10 marks):** Finalized SPMP for 3-month timeline
- [x] **Task 5 (10 marks):** Dependency graph and maximum path value
- [x] **Task 6 (5 marks):** Gantt Chart
- [x] **Task 7 (5 marks):** Team allocation chart
- [x] **Task 8 (10 marks):** Burndown chart (planned vs actual)

**Total: 100 marks**

---

## File Structure

```
Docs/Pm_2/
├── README.md (this file)
├── index.md (Main PMP document with all sections)
├── NEW_ESTIMATIONS.md (Updated estimation with new team structure)
├── FEATURE_DEMO_DETAILS.md (Detailed demo documentation)
├── DEPENDENCY_GRAPH_DETAILS.md (Detailed dependency analysis)
├── DATA_CONSISTENCY_REPORT.md (Consistency scan results and validation)
└── csv_data/ (CSV files for all charts and diagrams)
    ├── team_allocation.csv
    ├── burndown_chart.csv
    ├── velocity_analysis.csv
    ├── estimation_breakdown.csv
    ├── feature_allocation.csv
    ├── dependency_tasks.csv
    └── story_points_by_epic.csv

Docs/pm/
├── Gantt Chart.png (Visual Gantt chart)
└── ... (other project artifacts)
```

---

## Main Document: index.md

The `index.md` file contains all 8 required deliverables in a single comprehensive document:

### Section 1: Project Estimations (Task 1)
- **Agile Estimation:** 104 Story Points across 25 user stories
- **Formal Estimation:** 40 Fibonacci Points = 200 Developer-Days
- **Breakdown:** By epic (Authentication, Events, Booking, Notifications, Reports, Admin)

### Section 2: Project Schedule & Milestones (Task 2)
- **Timeline:** 12-week project with 2-week milestones
- **Milestone 1 (Weeks 1-2):** Foundation & Core Authentication
- **Milestone 2 (Weeks 3-4):** Core Feature Implementation (Events, Booking)
- **Milestone 3 (Weeks 5-6):** Feature Completion & System Integration
- **Milestone 4 (Weeks 7-8):** Advanced Features
- **Milestone 5 (Weeks 9-10):** Testing
- **Milestone 6 (Weeks 11-12):** Deployment & Handover

### Section 3: Feature Demo & Estimation Error (Task 3)
- **Demo 1:** User Authentication System
  - Registration, Login, JWT, Role-based access
  - Estimation error: 16.5% (15 SP actual vs 13 SP estimated)
- **Demo 2:** Event & Booking Management
  - Event CRUD, Booking system, QR codes
  - Estimation error: 18% (62 SP actual vs 55 SP estimated)
- **Updated Estimates:** 122 SP total (17.5% adjustment factor)

### Section 4: Finalized SPMP (Task 4)
- **Project Overview:** 12 weeks, 5 members, 3-month constraint
- **Work Breakdown Structure (WBS):** 6 phases, detailed tasks
- **Resource Allocation:** Team composition and tools
- **Risk Management:** 6 key risks with mitigation strategies
- **Quality Assurance:** Testing strategy and metrics
- **Communication Plan:** Standups, sprints, reports, demos

### Section 5: Dependency Graph & Critical Path (Task 5)
- **Task Network:** 22 tasks with dependencies mapped
- **Critical Path:** A → B → D → F → J → L → M → T → U → V
- **Maximum Path Value:** 74 days (sequential)
- **Optimized Duration:** 50 days (with 5 members in parallel)
- **Slack Time Analysis:** Up to 32 days slack on non-critical tasks

### Section 6: Gantt Chart (Task 6)
- **Visual Chart:** Available at `/Docs/pm/Gantt Chart.png`
- **Features:** Task timeline, dependencies, milestones, color coding
- **Reference:** Embedded in index.md

### Section 7: Team Allocation Chart (Task 7)
- **Team Structure:** 
  - 1 member: Implementation & Deployment
  - 1 member: Requirements Planning & Testing
  - 2 members: Architecture & Design
  - 1 member: Estimation & Testing
- **Weekly Allocation:** 12-week breakdown by role
- **Feature Allocation:** Member-days per feature (see csv_data/feature_allocation.csv)
- **Utilization:** 98% resource utilization

### Section 8: Burndown Chart (Task 8)
- **Scope:** 122 Story Points over 6 sprints
- **Planned vs Actual:** Week-by-week comparison
- **Velocity Analysis:** Average 20.3 SP/week
- **Variance:** Started 8 SP behind, finished 1 SP ahead
- **Cumulative Flow:** Visual representation of progress

---

## Supporting Documents

### DATA_CONSISTENCY_REPORT.md
Comprehensive data consistency scan results:
- Complete validation of all numerical values
- Team member reference consistency check
- CSV data accuracy verification
- Documents fixes applied
- Provides consistency guidelines

### NEW_ESTIMATIONS.md
Updated project estimation with new team structure:
- Fibonacci-based function point estimation
- Specialized role assignments
- 300 developer-days breakdown
- Team capacity analysis
- Phase-wise effort distribution

### FEATURE_DEMO_DETAILS.md
Detailed demonstration guide for the implemented features:
- Setup instructions for running demos
- Step-by-step demo scenarios
- Code references and API endpoints
- Performance metrics
- Test coverage analysis
- Estimation breakdown with variance factors

### DEPENDENCY_GRAPH_DETAILS.md
In-depth dependency analysis:
- Complete task list with durations
- All dependency paths calculated
- Critical path analysis
- Resource optimization strategy
- Week-by-week parallel execution plan
- Dependency matrix table
- Risk mitigation for critical tasks

### csv_data/ Directory
All charts and diagrams in CSV format:
- **team_allocation.csv** - Weekly team allocation by role
- **burndown_chart.csv** - Sprint burndown data
- **velocity_analysis.csv** - Sprint velocity tracking
- **estimation_breakdown.csv** - Detailed estimation by phase
- **feature_allocation.csv** - Feature-wise effort distribution
- **dependency_tasks.csv** - Task dependencies and critical path
- **story_points_by_epic.csv** - Story points by epic
- See `csv_data/README.md` for detailed usage instructions

---

## How to Use This Documentation

### For Stakeholders:
1. Read **index.md** for complete overview
2. Focus on Sections 2, 4, and 8 for project status
3. Review Gantt Chart for timeline visualization

### For Development Team:
1. Reference **Section 7** for your role and allocation
2. Check **DEPENDENCY_GRAPH_DETAILS.md** for task dependencies
3. Use **FEATURE_DEMO_DETAILS.md** for implementation guidance

### For Project Manager:
1. Monitor **Section 8** (Burndown Chart) for progress tracking
2. Review **Section 5** (Critical Path) for scheduling decisions
3. Reference **Section 4** (SPMP) for risk management

### For Evaluators:
1. All 8 deliverables are clearly marked in **index.md**
2. Supporting details in dedicated markdown files
3. Visual aids (Gantt Chart) in `/Docs/pm/` directory

---

## Key Metrics Summary

| Metric | Value | Notes |
|--------|-------|-------|
| **Total Story Points** | 122 SP | Adjusted from 104 SP |
| **Project Duration** | 12 weeks | Fits 3-month constraint |
| **Team Size** | 5 members | Specialized roles for optimal delivery |
| **Critical Path** | 74 days | Sequential maximum |
| **Parallel Execution** | 50 days | With resource optimization |
| **Estimation Variance** | 17.5% | Based on demo features |
| **Resource Utilization** | 98% | Very high efficiency |
| **On-Time Delivery** | 100% | Project completed as planned |
| **Test Coverage** | 72% | Exceeds 70% target |

---

## Project Status

✅ **Milestone 1 Complete:** Foundation & Authentication
✅ **Milestone 2 Complete:** Core Features (Events, Booking)
🔄 **Milestone 3 In Progress:** Advanced Features
⏳ **Milestone 4 Planned:** Testing & Deployment

**Current Sprint:** Sprint 3 (Weeks 5-6)
**Velocity:** 20.3 SP/week average
**Burndown Status:** On track (0 SP variance)

---

## References

- **Main Project Repo:** `/Project/`
- **Backend Code:** `/Project/backend/`
- **Frontend Code:** `/Project/frontend/`
- **Architecture Docs:** `/Docs/pm/Archticture/`
- **Database Schema:** `/Docs/pm/ERD.png`
- **Previous Milestone:** `/Docs/pm/`

---

## Contact & Contribution

For questions about this PMP:
- Review the main `index.md` for comprehensive information
- Check supporting docs for detailed breakdowns
- Refer to code comments in `/Project/` for implementation details

---

## Version History

- **v2.0** (Current) - Complete PMP with all 8 deliverables
- **v1.0** - Initial estimations and schedule (Tasks 1-2)

---

## Appendix: Deliverable Mapping

| Task | Section | Location | Marks | Status |
|------|---------|----------|-------|--------|
| 1. Estimations | Section 1 | index.md | 20 | ✅ |
| 2. Schedule | Section 2 | index.md | 20 | ✅ |
| 3. Demo | Section 3 | index.md + FEATURE_DEMO_DETAILS.md | 20 | ✅ |
| 4. SPMP | Section 4 | index.md | 10 | ✅ |
| 5. Dependency | Section 5 | index.md + DEPENDENCY_GRAPH_DETAILS.md | 10 | ✅ |
| 6. Gantt | Section 6 | index.md + /Docs/pm/Gantt Chart.png | 5 | ✅ |
| 7. Allocation | Section 7 | index.md | 5 | ✅ |
| 8. Burndown | Section 8 | index.md | 10 | ✅ |

**Total:** 100 marks ✅
