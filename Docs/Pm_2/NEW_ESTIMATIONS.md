# Project Estimation: AIU Trips & Events Management System

## Step 1: Introduction
This report provides the estimation and scheduling for the AIU Trips & Events Management System based on the Fibonacci Function Point Estimation method.  
The project is divided into phases, subsystems, and class-level features. Each feature is assigned a Fibonacci point according to its complexity, then converted into effort days to estimate the total duration and project size.

---

## Step 2: Approach
- Estimation Method: Fibonacci-based Function Point Estimation  
- Breakdown Level: From Use Cases → Subsystems → Classes  
- Conversion Rate: 1 Fibonacci Point = 5 Days  
- Team Size: 5 Developers  
- Project Duration: 2 Months (40 working days per developer → 200 developer-days total)

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
| 40 Fibonacci Points | 1 Point = 5 Days | 200 Developer-Days (Fits Exactly) |

---

## Step 5: Capacity vs Effort Analysis

| Parameter | Calculation | Result |
|---|---:|---:|
| Team Capacity | 5 Developers × 40 Days | 200 Developer-Days |
| Required Effort From Estimation | — | 200 Developer-Days |

---

## Step 6: Schedule Summary
The estimated effort of 200 developer-days fits perfectly into the 2-month duration. Each developer contributes 40 working days, covering all project phases including design, implementation, testing, and deployment. This ensures balanced workload distribution and on-time project completion.

---

## Step 7: Final Conclusion
The total estimation for the AIU Trips & Events Management System equals 40 Fibonacci Points (200 developer-days). This estimation fits perfectly within the 2-month project duration for a 5-member team. The breakdown ensures balanced effort, realistic timelines, and alignment with the estimation methodology explained in the lecture.

### Total Effort Summary
- Total Fibonacci Estimate: 40 Points  
- Total Effort: 200 Days  
- Fits perfectly in 8 weeks (5 developers × 5 days/week × 2 months)

---

## Assumptions
- Team Capacity: 5 Developers  
- Workdays per Week: 5 Days  
- Total Working Duration: 2 Months (≈ 40 working days per developer)  
- Conversion Rate: 1 Fibonacci Point = 5 Days  
- Daily Capacity: 5 developers × (1 point / 5 days) = 1 point/day  
- Total Duration: 40 Fibonacci Points ÷ 1 point/day = 40 working days (≈ 8 weeks)
