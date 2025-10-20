# PM_2 Charts Data (CSV Format)

This directory contains CSV files for all charts and diagrams referenced in the PM_2 documentation. These CSV files can be imported into spreadsheet applications or data visualization tools.

## Files Overview

### 1. estimation_breakdown.csv
**Description:** Complete breakdown of all project features with Fibonacci point estimates and team assignments.

**Columns:**
- Phase: Project phase (Phase 1-5)
- Subsystem: Feature category (Authentication, Event Management, etc.)
- Feature: Specific feature name
- Fibonacci_Points: Complexity points assigned
- Effort_Days: Estimated effort in days
- Responsible_Team: Primary team implementing the feature
- Testing_Team: Team responsible for testing

**Usage:** Import into Excel/Google Sheets for pivot tables and charts showing effort distribution by team.

---

### 2. team_structure.csv
**Description:** Overview of all 5 teams with their responsibilities and composition.

**Columns:**
- Team_ID: Team identifier (Team 1-5)
- Team_Name: Full team name
- Team_Size: Number of members
- Primary_Responsibilities: Main duties
- Key_Deliverables: Expected outputs
- Working_Weeks: Duration of team engagement

**Usage:** Reference for understanding team organization and roles.

---

### 3. team_allocation.csv
**Description:** Weekly breakdown of activities for all 5 teams across the project timeline.

**Columns:**
- Week: Project week (1-2, 3-4, etc.)
- Phase: Project phase name
- Team_1_Implementation: Activities for implementation team
- Team_2_Requirements_Testing: Activities for requirements/testing team
- Team_3_Backend_Architecture: Activities for backend architecture team
- Team_4_Frontend_Architecture: Activities for frontend architecture team
- Team_5_Estimation_Testing: Activities for estimation/testing team

**Usage:** Create Gantt charts or timeline visualizations showing parallel team activities.

---

### 4. dependency_graph.csv
**Description:** All project tasks with dependencies, durations, and team assignments.

**Columns:**
- Task_ID: Unique task identifier (A, B, C, etc.)
- Task_Name: Descriptive task name
- Duration_Days: Task duration
- Depends_On: Prerequisites (task IDs)
- Responsible_Team: Team executing the task
- Slack_Days: Available slack time
- Critical_Path: Yes/No flag for critical path tasks

**Usage:** Generate network diagrams, critical path analysis, or project scheduling tools.

---

### 5. gantt_chart.csv
**Description:** Week-by-week task timeline showing team allocation across all weeks.

**Columns:**
- Week: Project week number
- Task_Name: Name of the task
- Team_1 through Team_5: Visual indicators (████) showing team involvement
- Dependencies: Required predecessor tasks
- Milestone: Key project milestone markers

**Usage:** Create Gantt chart visualizations in project management tools.

---

### 6. burndown_chart.csv
**Description:** Sprint-by-sprint progress tracking with planned vs actual story points.

**Columns:**
- Sprint: Sprint identifier (Sprint 1-6)
- Week: Project week number
- Planned_Remaining_SP: Planned story points remaining
- Actual_Remaining_SP: Actual story points remaining
- Variance: Difference between planned and actual
- Status: Current status indicator
- Completed_By_Team: Teams that contributed to the sprint

**Usage:** Generate burndown charts showing project progress and team contributions.

---

### 7. velocity_analysis.csv
**Description:** Sprint velocity metrics showing planned vs actual delivery rates.

**Columns:**
- Sprint: Sprint identifier
- Week_Range: Week range for the sprint
- Planned_Velocity_SP: Expected story points per week
- Actual_Velocity_SP: Delivered story points per week
- Variance_Percent: Percentage variance
- Primary_Team: Main team for the sprint
- Support_Teams: Supporting teams

**Usage:** Analyze team velocity trends and forecast future sprints.

---

## How to Use These Files

### Excel/Google Sheets
1. Import CSV files using File > Import
2. Create pivot tables for summarization
3. Generate charts and graphs
4. Analyze team workload distribution

### Project Management Tools
1. Import dependency_graph.csv into MS Project, Jira, or similar tools
2. Use gantt_chart.csv to create timeline visualizations
3. Track burndown using burndown_chart.csv

### Data Visualization Tools
1. Load CSV files into Tableau, Power BI, or similar platforms
2. Create interactive dashboards
3. Generate custom reports

### Python/R Analysis
```python
import pandas as pd

# Example: Load estimation data
estimation_df = pd.read_csv('estimation_breakdown.csv')

# Analyze effort by team
team_effort = estimation_df.groupby('Responsible_Team')['Effort_Days'].sum()
print(team_effort)
```

---

## File Integrity

All CSV files use:
- UTF-8 encoding
- Comma (,) as delimiter
- Double quotes (") for text fields containing commas
- First row as header

---

## Related Documentation

- **Main Document:** `/Docs/Pm_2/index.md`
- **Detailed Estimation:** `/Docs/Pm_2/NEW_ESTIMATIONS.md`
- **Dependency Analysis:** `/Docs/Pm_2/DEPENDENCY_GRAPH_DETAILS.md`
- **Feature Demos:** `/Docs/Pm_2/FEATURE_DEMO_DETAILS.md`
- **Overview:** `/Docs/Pm_2/README.md`

---

## Version History

- **v2.0** (Current) - Added 5-team structure with specialized roles
- **v1.0** - Initial CSV exports

---

Last Updated: October 2025
