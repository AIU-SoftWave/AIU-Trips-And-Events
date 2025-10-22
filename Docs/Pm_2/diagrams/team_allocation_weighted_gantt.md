# Team Allocation — Weighted Workload Gantt

Derived from `Docs/Pm_2/csv_data/dependency_tasks.csv` (assignments) and `Docs/Pm_2/diagrams/pm2_gantt.md` (dates). Each member shows the tasks they are assigned to at the scheduled dates.

```mermaid
%%{init: {
  'theme': 'default',
  'themeVariables': { 'fontSize': '15px' },
  'gantt': {
    'axisFormat': '%b %d',
    'barHeight': 26,
    'barGap': 6,
    'topPadding': 40,
    'leftPadding': 200,
    'rightPadding': 80,
    'gridLineStartPadding': 40,
    'titleTopMargin': 20,
    'numberSectionStyles': 4
  }
}}%%
gantt
  title Team Allocation — Weighted by Scheduled Tasks
  dateFormat  YYYY-MM-DD
  axisFormat  %b %d
  excludes    weekends

  section Member 1 (Implementation & Deployment)
  A: 2025-10-21, 3d
  C: 2025-10-24, 4d
  D: 2025-10-29, 7d
  E: 2025-11-05, 7d
  F: 2025-11-05, 5d
  G: 2025-11-10, 6d
  H: 2025-11-10, 7d
  I: 2025-11-17, 6d
  J: 2025-11-17, 4d
  K: 2025-11-05, 4d
  U: 2025-11-22, 4d
  V: 2025-11-26, 2d

  section Member 2 (Requirements Planning & Testing)
  K: 2025-11-05, 4d
  P: 2025-11-12, 8d
  Q: 2025-11-21, 10d
  R: 2025-12-01, 5d

  section Member 3 (Architecture & Design)
  B: 2025-10-24, 5d
  D: 2025-10-29, 7d
  F: 2025-11-05, 5d
  H: 2025-11-10, 7d
  L: 2025-11-10, 5d
  N: 2025-11-10, 5d

  section Member 4 (Architecture & Design)
  B: 2025-10-24, 5d
  E: 2025-11-05, 7d
  G: 2025-11-10, 6d
  I: 2025-11-17, 6d
  L: 2025-11-10, 5d
  M: 2025-11-15, 4d
  N: 2025-11-10, 5d
  O: 2025-11-15, 4d
  T: 2025-11-19, 3d

  section Member 5 (Estimation & Testing)
  J: 2025-11-17, 4d
  P: 2025-11-12, 8d
  Q: 2025-11-21, 10d
  S: 2025-12-01, 6d
```
