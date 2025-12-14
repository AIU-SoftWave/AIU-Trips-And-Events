# Manual Testing Quick Reference Guide

Quick reference for executing manual tests on the AIU Trips & Events Management System.

---

## 📁 Test Files Overview

| File | Purpose | Use When |
|------|---------|----------|
| **MANUAL_TEST_CASES.md** | Detailed test documentation | Executing tests, reviewing requirements |
| **Manual_Test_Cases.csv** | Test tracking spreadsheet | Tracking progress, generating reports |
| **TEST_EXECUTION_TEMPLATE.md** | Session documentation | Recording test sessions |
| **TEST_REQUIREMENTS_MAPPING.md** | Traceability matrix | Verifying coverage, requirement validation |
| **README.md** | Documentation overview | Getting started |

---

## 📋 CSV File Structure

The `Manual_Test_Cases.csv` file follows a comprehensive test case format with the following columns:

| Column | Description | Example |
|--------|-------------|---------|
| **TC_ID** | Unique test case identifier | TC_001, TC_002 |
| **Feature** | Feature or module being tested | User Authentication - Login |
| **Title** | Short test case title | Validate login with valid credentials |
| **Description** | Detailed description of what to test | Verify that the system allows users to log in with valid credentials |
| **Preconditions** | Required setup before test execution | User account exists in the system |
| **Test Steps** | Step-by-step instructions | 1. Navigate to login page<br>2. Enter credentials<br>3. Click login |
| **Test Data** | Data needed for the test | Email: test@aiu.edu, Password: Test@123 |
| **Expected Result** | What should happen | User successfully logs in and sees dashboard |
| **Postconditions** | System state after test | User session is active |
| **Actual Result** | What actually happened (filled during execution) | _(Empty until test execution)_ |
| **Status** | Current test status | Not Run, Passed, Failed, Blocked |
| **Author** | Who created the test case | QA Team |
| **Execution Cycle** | Test cycle number | Cycle 1, Cycle 2 |
| **Priority** | Test importance level | High, Medium, Low |

### How to Use the CSV

1. **Open in Excel/Google Sheets** for best experience
2. **Filter by Feature** to focus on specific modules
3. **Sort by Priority** to execute critical tests first
4. **Update Status** column as you complete each test
5. **Fill Actual Result** column with observations
6. **Track progress** by counting Passed/Failed/Not Run

---

## 🚀 Quick Start (3 Steps)

### 1. Setup (5 minutes)
```bash
# Navigate to project
cd /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/Project

# Start the application
cd backend
mvn spring-boot:run

# Application runs on: http://localhost:8080
```

### 2. Prepare Test Data (5 minutes)
- Application auto-seeds test data on startup
- Test accounts available:
  - Student: `john.doe@aiu.edu` / `password123`
  - Organizer: `organizer@aiu.edu` / `password123`
  - Admin: `admin@aiu.edu` / `password123`

### 3. Execute Tests (Variable)
- Open `Manual_Test_Cases.csv` in Excel/Google Sheets
- Follow test cases from `MANUAL_TEST_CASES.md`
- Update CSV with results as you go

---

## 📊 Test Categories & Time Estimates

| Category | Tests | Estimated Time | Priority |
|----------|-------|----------------|----------|
| **UC-01: Authentication** | 14 | 45-60 min | 🔴 Critical |
| **UC-02: Event Management** | 13 | 45-60 min | 🔴 Critical |
| **UC-03: Booking & Ticketing** | 12 | 40-50 min | 🔴 Critical |
| **UC-04: Notifications** | 14 | 50-70 min | 🟡 High |
| **UC-05: Reports & Analytics** | 21 | 90-120 min | 🟡 High |
| **TOTAL** | **74** | **~5-6 hours** | |

---

## 🎯 Test Execution Order (Recommended)

### Phase 1: Core Functionality (Critical)
1. **UC-01: Authentication** (T1.1.1 - T1.3.4)
   - Login/Logout
   - Registration
   - Password Reset

2. **UC-02: Event Management** (T2.1.1 - T2.4.3)
   - Create Event
   - Edit Event
   - Delete Event
   - Capacity Management

3. **UC-03: Booking & Ticketing** (T3.1.1 - T3.5.1)
   - Browse Events
   - Make Booking
   - Generate Ticket
   - QR Code Validation

### Phase 2: Supporting Features (High Priority)
4. **UC-04: Notifications** (T4.1.1 - T4.8.2)
   - Event Notifications
   - Booking Confirmations
   - Reminders

5. **UC-05: Reports & Analytics** (T5.1.1 - T5.9.2)
   - Generate Reports
   - Export Data
   - View Analytics

---

## ✅ Test Status Symbols

Use these in your CSV file:

| Symbol | Status | Description |
|--------|--------|-------------|
| ⏳ | Not Run | Test not yet executed |
| ✅ | Pass | Test passed successfully |
| ❌ | Fail | Test failed, bug found |
| 🚫 | Blocked | Cannot execute due to blocker |
| 🔄 | Retest | Need to retest after bug fix |
| ⚠️ | Warning | Passed with minor issues |

---

## 🐛 Bug Reporting Template

When you find a bug, document it immediately:

```markdown
**Bug ID:** BUG-XXX
**Test Case:** T1.1.1
**Severity:** Critical / High / Medium / Low
**Priority:** P0 / P1 / P2 / P3

**Description:**
Brief description of the issue

**Steps to Reproduce:**
1. Step 1
2. Step 2
3. Step 3

**Expected Result:**
What should happen

**Actual Result:**
What actually happened

**Environment:**
- Browser: Chrome 120
- OS: Windows 11
- Build: v1.0.0

**Screenshots:**
Attach screenshots or screen recording

**Additional Notes:**
Any other relevant information
```

---

## 📈 Progress Tracking

### Daily Summary Template
```
Date: ___/___/___
Tester: ___________

Tests Executed: ___ / 74
Pass: ___
Fail: ___
Blocked: ___

Pass Rate: ___%

Critical Bugs: ___
High Priority Bugs: ___
Medium Priority Bugs: ___
Low Priority Bugs: ___

Notes:
___________
```

---

## 🔍 Common Test Scenarios

### Scenario 1: Complete Student Journey
1. Register new account (T1.2.1)
2. Verify email (T1.2.3)
3. Login (T1.1.1)
4. Browse events (T3.1.1)
5. Book event (T3.2.1)
6. Receive ticket (T3.3.2)
7. View booking history (T3.4.2)

### Scenario 2: Complete Organizer Journey
1. Login as organizer (T1.1.1)
2. Create new event (T2.1.1)
3. Edit event details (T2.2.1)
4. Send notification (T4.6.1)
5. Generate report (T5.1.1)

### Scenario 3: Complete Admin Journey
1. Login as admin (T1.1.1)
2. View all events (T2.4.1)
3. Generate revenue report (T5.2.1)
4. Export data (T5.4.1)
5. View dashboard (T5.3.2)

---

## 🛠️ Test Environment Checklist

Before starting tests, verify:

- [ ] Application is running and accessible
- [ ] Database is properly seeded
- [ ] Test accounts are created and credentials are known
- [ ] Email service is configured (for notification tests)
- [ ] Browser DevTools is open (for debugging)
- [ ] Screenshot tool is ready
- [ ] CSV file is open for tracking
- [ ] Bug tracking system is accessible

---

## 📞 Support & Resources

### Documentation
- Full Test Cases: `MANUAL_TEST_CASES.md`
- Requirements: `/Milestones/pm/AIU Trips & Events Report.pdf`
- API Docs: `/Project/docs/API_DOCUMENTATION.md`
- Testing Guide: `/Project/docs/TESTING_GUIDE.md`

### Test Data
- Sample users are automatically created on app startup
- Sample events are available in the database
- Use provided credentials for testing

### Troubleshooting
- If application won't start: Check port 8080 is free
- If tests fail unexpectedly: Check browser console for errors
- If data issues: Restart application to reseed database

---

## 💡 Tips for Efficient Testing

1. **Test in batches** - Complete one use case before moving to next
2. **Document immediately** - Update CSV right after each test
3. **Take screenshots** - Capture evidence for both pass and fail
4. **Test edge cases** - Don't just test happy path
5. **Note observations** - Write down anything unusual
6. **Check logs** - Review application logs for errors
7. **Test different browsers** - If time permits
8. **Verify data** - Check database to confirm actions
9. **Test responsiveness** - Try different screen sizes
10. **Security testing** - Try invalid inputs, XSS, SQL injection

---

## 📋 Test Execution Checklist

### Before Testing
- [ ] Read all test cases in MANUAL_TEST_CASES.md
- [ ] Understand test objectives
- [ ] Prepare test environment
- [ ] Open tracking spreadsheet
- [ ] Set up screen capture tool

### During Testing
- [ ] Follow test order
- [ ] Execute all steps carefully
- [ ] Document all results
- [ ] Take screenshots for failures
- [ ] Log bugs immediately
- [ ] Note any blockers

### After Testing
- [ ] Calculate pass rate
- [ ] Summarize findings
- [ ] Prioritize bugs
- [ ] Create bug reports
- [ ] Schedule retests
- [ ] Update stakeholders

---

## 🎓 Best Practices

### DO ✅
- Follow test steps exactly as written
- Document every test result
- Report bugs immediately
- Use provided test data
- Test with different user roles
- Verify both UI and backend behavior
- Take detailed notes

### DON'T ❌
- Skip test steps
- Assume tests pass without verification
- Test in production environment
- Use real student data
- Rush through tests
- Ignore minor issues
- Mix test data between sessions

---

**Quick Reference Version:** 1.0  
**Last Updated:** December 2025  
**For Questions:** Refer to README.md or MANUAL_TEST_CASES.md
