# Manual Testing Suite - Complete Index

**AIU Trips & Events Management System**  
**Version:** 1.0  
**Date:** December 2025  
**Total Test Cases:** 74

---

## 📚 Documentation Suite

This manual testing suite provides comprehensive test coverage for all functional requirements of the AIU Trips & Events Management System. All test cases are derived from the official requirements document: `AIU Trips & Events Report.pdf`.

### Core Documents

1. **[MANUAL_TEST_CASES.md](./MANUAL_TEST_CASES.md)** ⭐ Main Document
   - 2,815 lines of detailed test documentation
   - All 74 test cases with complete structure
   - Test execution guidelines
   - Test templates and examples
   - Sample test data
   - **Use this for**: Detailed test execution

2. **[Manual_Test_Cases.csv](./Manual_Test_Cases.csv)** ⭐ Tracking Sheet
   - Excel/Google Sheets compatible
   - Real-time progress tracking
   - Columns: ID, Use Case, Description, Priority, Status, Tester, Date, Result, Notes
   - **Use this for**: Daily test tracking and reporting

3. **[QUICK_REFERENCE.md](./QUICK_REFERENCE.md)** ⭐ Quick Start
   - 3-step quick start guide
   - Test execution order
   - Time estimates per category
   - Common test scenarios
   - Tips and best practices
   - **Use this for**: Getting started quickly

4. **[TEST_REQUIREMENTS_MAPPING.md](./TEST_REQUIREMENTS_MAPPING.md)**
   - Complete traceability matrix
   - Maps all 74 test cases to functional requirements
   - 100% requirements coverage
   - Priority distribution analysis
   - **Use this for**: Requirements validation and coverage analysis

5. **[TEST_EXECUTION_TEMPLATE.md](./TEST_EXECUTION_TEMPLATE.md)**
   - Test session documentation template
   - Pre-test checklist
   - Execution log format
   - Bug reporting structure
   - **Use this for**: Documenting test sessions

6. **[README.md](./README.md)**
   - Overview and navigation
   - File descriptions
   - Quick start instructions
   - Integration info
   - **Use this for**: Understanding the suite structure

---

## 🎯 Test Coverage Overview

### By Use Case

| Use Case | Test Cases | Coverage | Priority | Time Est. |
|----------|------------|----------|----------|-----------|
| **UC-01: User Authentication** | 14 tests | F1.1-F1.8 | 🔴 Critical | 45-60 min |
| **UC-02: Event Management** | 13 tests | F2.1-F2.8 | 🔴 Critical | 45-60 min |
| **UC-03: Booking & Ticketing** | 12 tests | F3.1-F3.6 | 🔴 Critical | 40-50 min |
| **UC-04: Notifications** | 14 tests | F4.1-F4.5 | 🟡 High | 50-70 min |
| **UC-05: Reports & Analytics** | 21 tests | F5.1-F5.6 | 🟡 High | 90-120 min |
| **TOTAL** | **74 tests** | **30 FRs** | | **~5-6 hours** |

### Test Priority Breakdown

- **High Priority:** ~30 tests (40%) - Critical system functionality
- **Medium Priority:** ~44 tests (60%) - Important supporting features
- **Requirements Coverage:** 100% (All functional requirements covered)

---

## 🚀 How to Use This Suite

### For Testers

1. **First Time Setup**
   ```bash
   # Read QUICK_REFERENCE.md for fast onboarding
   # Open Manual_Test_Cases.csv in Excel/Sheets
   # Review MANUAL_TEST_CASES.md sections
   # Start test environment
   ```

2. **Daily Testing**
   ```bash
   # Open Manual_Test_Cases.csv
   # Follow test cases from MANUAL_TEST_CASES.md
   # Update CSV with results after each test
   # Document bugs using template in QUICK_REFERENCE.md
   ```

3. **End of Day**
   ```bash
   # Calculate pass rate from CSV
   # Document session using TEST_EXECUTION_TEMPLATE.md
   # Create bug reports for failures
   # Update team on progress
   ```

### For Test Managers

1. **Planning**
   - Review TEST_REQUIREMENTS_MAPPING.md for coverage
   - Allocate ~6 hours per tester for full suite
   - Plan critical tests (UC-01, UC-02, UC-03) first

2. **Tracking**
   - Monitor progress via Manual_Test_Cases.csv
   - Generate reports from CSV data
   - Track pass rates by use case

3. **Reporting**
   - Use metrics from TEST_EXECUTION_TEMPLATE.md
   - Reference requirements mapping for stakeholders
   - Track bug trends by category

### For Developers

1. **Before Implementation**
   - Review relevant test cases for feature
   - Check TEST_REQUIREMENTS_MAPPING.md for requirements

2. **After Bug Fixes**
   - Identify affected test cases
   - Coordinate with QA for retesting

3. **For New Features**
   - Add test cases to MANUAL_TEST_CASES.md
   - Update CSV with new rows
   - Update requirements mapping

---

## 📊 Test Categories Details

### UC-01: User Authentication & Account Management (14 tests)
**Test IDs:** T1.1.1 - T1.3.4  
**Focus Areas:**
- Login/Logout (T1.1.x)
- User Registration (T1.2.x)
- Password Management (T1.3.x)

**Key Tests:**
- T1.1.1: Valid login
- T1.1.4: Account lockout after 5 failed attempts
- T1.2.2: Duplicate email prevention
- T1.3.2: Password reset link expiration

### UC-02: Event & Trip Management (13 tests)
**Test IDs:** T2.1.1 - T2.4.3  
**Focus Areas:**
- Event Creation (T2.1.x)
- Event Editing (T2.2.x)
- Event Deletion (T2.3.x)
- Capacity Management (T2.4.x)

**Key Tests:**
- T2.1.3: Prevent past date events
- T2.2.2: Update notifications
- T2.3.2: Refunds on deletion
- T2.4.2: Capacity enforcement

### UC-03: Booking & Ticketing System (12 tests)
**Test IDs:** T3.1.1 - T3.5.1  
**Focus Areas:**
- Event Browsing (T3.1.x)
- Seat Reservation (T3.2.x)
- Digital Tickets (T3.3.x)
- Booking History (T3.4.x)
- QR Validation (T3.5.x)

**Key Tests:**
- T3.2.2: Prevent duplicate bookings
- T3.3.1: Unique QR codes
- T3.3.2: Email delivery
- T3.5.1: QR validation

### UC-04: Notification Management (14 tests)
**Test IDs:** T4.1.1 - T4.8.2  
**Focus Areas:**
- Event Notifications (T4.1.x, T4.2.x)
- Cancellations (T4.3.x)
- Confirmations (T4.4.x)
- Reminders (T4.5.x)
- Custom Messages (T4.6.x)
- Reliability (T4.7.x, T4.8.x)

**Key Tests:**
- T4.1.1: New event notifications
- T4.3.1: Cancellation promptness
- T4.5.1: 24-hour reminders
- T4.7.2: Delivery retry logic

### UC-05: Reports & Analytics (21 tests)
**Test IDs:** T5.1.1 - T5.9.2  
**Focus Areas:**
- Participant Reports (T5.1.x)
- Revenue Reports (T5.2.x)
- Statistics (T5.3.x)
- Export Functionality (T5.4.x)
- Feedback Analytics (T5.5.x)
- Attendance Tracking (T5.6.x)
- Performance Metrics (T5.7.x)
- Data Handling (T5.8.x)
- Scheduled Reports (T5.9.x)

**Key Tests:**
- T5.1.1: Participant accuracy
- T5.2.1: Revenue calculations
- T5.4.1-T5.4.3: PDF/CSV/JSON export
- T5.9.1: Automated scheduling

---

## 🔄 Test Workflow

### Standard Test Cycle

```
┌─────────────┐
│   Setup     │ Configure environment, seed data
└──────┬──────┘
       │
┌──────▼──────┐
│  Execute    │ Run tests following MANUAL_TEST_CASES.md
└──────┬──────┘
       │
┌──────▼──────┐
│  Document   │ Update CSV, take screenshots
└──────┬──────┘
       │
┌──────▼──────┐
│   Report    │ Create bug reports, calculate metrics
└──────┬──────┘
       │
┌──────▼──────┐
│   Retest    │ Verify fixes, update status
└─────────────┘
```

### Defect Lifecycle

```
Found (❌) → Reported (BUG-XXX) → Fixed → Retesting (🔄) → Verified (✅)
```

---

## 📁 File Sizes and Stats

| File | Size | Lines | Purpose |
|------|------|-------|---------|
| MANUAL_TEST_CASES.md | 61 KB | 2,815 | Main documentation |
| Manual_Test_Cases.csv | 7.9 KB | 75 | Tracking sheet |
| TEST_REQUIREMENTS_MAPPING.md | 12 KB | 450 | Traceability |
| QUICK_REFERENCE.md | 7.3 KB | 300 | Quick guide |
| TEST_EXECUTION_TEMPLATE.md | 3.2 KB | 150 | Session template |
| README.md | 3.0 KB | 101 | Overview |
| INDEX.md | 8.0 KB | 350 | This file |
| **TOTAL** | **~102 KB** | **~4,241** | Complete suite |

---

## 🎓 Training Resources

### For New Testers
1. Start with README.md
2. Read QUICK_REFERENCE.md
3. Review first 5 test cases in MANUAL_TEST_CASES.md
4. Practice with UC-01 tests
5. Graduate to full suite

### For Experienced Testers
1. Jump to QUICK_REFERENCE.md
2. Open Manual_Test_Cases.csv
3. Execute tests by priority
4. Reference detailed docs as needed

---

## 🔗 Related Documentation

- **Requirements:** `/Milestones/pm/AIU Trips & Events Report.pdf`
- **API Documentation:** `/Project/docs/API_DOCUMENTATION.md`
- **Automated Tests:** `/Project/backend/src/test/`
- **Testing Guide:** `/Project/docs/TESTING_GUIDE.md`
- **Architecture:** `/Milestones/pm/Architecture/`

---

## 📞 Contact & Support

- **For Test Execution Questions:** See QUICK_REFERENCE.md
- **For Coverage Questions:** See TEST_REQUIREMENTS_MAPPING.md
- **For Detailed Test Info:** See MANUAL_TEST_CASES.md
- **For Progress Tracking:** Use Manual_Test_Cases.csv

---

## ✅ Quality Metrics

### Documentation Quality
- ✅ All 74 test cases documented
- ✅ 100% requirements coverage
- ✅ Clear structure and organization
- ✅ Multiple format support (MD, CSV)
- ✅ Comprehensive examples and templates

### Usability
- ✅ Quick reference guide provided
- ✅ Multiple entry points for different roles
- ✅ Clear navigation and indexing
- ✅ Practical examples included
- ✅ Time estimates provided

### Completeness
- ✅ All use cases covered
- ✅ All functional requirements mapped
- ✅ Execution guidelines included
- ✅ Bug reporting templates provided
- ✅ Test data samples included

---

**Index Version:** 1.0  
**Suite Status:** ✅ Complete and Ready  
**Last Updated:** December 2025  
**Next Review:** After first test execution cycle

---

## Quick Navigation

- **Want to start testing immediately?** → [QUICK_REFERENCE.md](./QUICK_REFERENCE.md)
- **Need detailed test cases?** → [MANUAL_TEST_CASES.md](./MANUAL_TEST_CASES.md)
- **Want to track progress?** → [Manual_Test_Cases.csv](./Manual_Test_Cases.csv)
- **Need requirements traceability?** → [TEST_REQUIREMENTS_MAPPING.md](./TEST_REQUIREMENTS_MAPPING.md)
- **Want session template?** → [TEST_EXECUTION_TEMPLATE.md](./TEST_EXECUTION_TEMPLATE.md)
- **Need overview?** → [README.md](./README.md)
