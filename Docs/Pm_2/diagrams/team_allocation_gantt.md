# Team Allocation (Weeks 1–8)

```mermaid
%%{init: {
  'theme': 'default',
  'themeVariables': { 'fontSize': '16px' },
  'gantt': {
    'axisFormat': '%W',
    'barHeight': 28,
    'barGap': 8,
    'topPadding': 40,
    'leftPadding': 140,
    'rightPadding': 80,
    'gridLineStartPadding': 40,
    'titleTopMargin': 20,
    'numberSectionStyles': 4
  }
}}%%
gantt
  title Team Allocation (Weeks 1–8)
  dateFormat  X
  axisFormat  %W

  section Weeks 1–2: Foundation
  M1: DevOps Setup & Initial Implementation      :active, 0, 10
  M2: Requirements Documentation & Test Planning :0, 10
  M3: Database Schema Design                     :0, 10
  M4: System Architecture Design                 :0, 10
  M5: Effort Estimation & Test Cases             :0, 10
    M5: Effort Estimation & Test Cases             :0, 10

    section Weeks 3–4: Core Features
    M1: Backend & Frontend Implementation          :10, 10
    M2: User Stories Validation & Test Execution   :10, 10
    M3: Event System Architecture                  :10, 10
    M4: Booking System Architecture                :10, 10
    M5: Feature Estimation & Testing               :10, 10

    section Weeks 5–6: Feature Completion
    M1: Integration & Deployment Scripts           :20, 10
    M2: Acceptance Testing & Planning              :20, 10
    M3: System Integration Design                  :20, 10
    M4: API Design & Documentation                 :20, 10
    M5: Effort Tracking & Testing                  :20, 10

    section Weeks 7–8: Advanced Features
    M1: Feature Implementation & CI/CD             :30, 10
    M2: Requirements Traceability & Testing        :30, 10
    M3: Reports Architecture Design                :30, 10
    M4: Admin System Architecture                  :30, 10
    M5: Time Estimation & Quality Testing          :30, 10
```
