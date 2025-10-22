# Team Allocation — Weighted by Task Durations

Computed from `Docs/Pm_2/csv_data/dependency_tasks.csv` by splitting each task's duration equally among its listed team members.

```mermaid
%%{init: {'theme':'default','pie':{'textPosition':0.6},'themeVariables':{'pieSectionTextSize':'16px','pieTitleTextSize':'20px'}}}%%
pie showData
    title Workload Share by Member (Person-Days, Weighted)
    "Member 1 — Implementation & Deployment" : 36
    "Member 2 — Requirements Planning & Testing" : 17.5
    "Member 3 — Architecture & Design" : 17
    "Member 4 — Architecture & Design" : 26.5
    "Member 5 — Estimation & Testing" : 17
```

Notes:
- Person-days are calculated as: for each task, `Duration / (# of assigned members)` added to each member.
- Example: Task `D` (7d) assigned to Member 1 & Member 3 contributes `3.5` to each.
- If you prefer different splits (e.g., weight backend/frontend differently), share ratios and I’ll recompute.
