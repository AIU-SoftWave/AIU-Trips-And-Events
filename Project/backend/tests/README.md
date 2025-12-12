# Manual Test Cases for AIU Trips & Events

This directory contains comprehensive manual test cases for the AIU Trips & Events Management System.

## Files

### 1. MANUAL_TEST_CASES.md
Comprehensive markdown documentation with detailed test cases, execution guidelines, and test templates.
- **Format:** Markdown
- **Total Test Cases:** 74
- **Best For:** Detailed review, execution guidance, documentation

### 2. Manual_Test_Cases.csv
Spreadsheet-compatible CSV file for easy tracking and reporting.
- **Format:** CSV (Excel/Google Sheets compatible)
- **Columns:** Test Case ID, Use Case, Description, Priority, Status, Tester, Date Tested, Result, Notes
- **Best For:** Test execution tracking, progress reports, team collaboration

## Test Categories

| Category | Test Cases | File Section |
|----------|------------|--------------|
| UC-01: User Authentication & Account Management | 14 | T1.x.x |
| UC-02: Event & Trip Management | 13 | T2.x.x |
| UC-03: Booking & Ticketing System | 12 | T3.x.x |
| UC-04: Notification Management | 14 | T4.x.x |
| UC-05: Reports & Analytics | 21 | T5.x.x |
| **Total** | **74** | |

## Quick Start

### 1. Review Test Cases
```bash
# View markdown documentation
cat MANUAL_TEST_CASES.md

# Open CSV in spreadsheet application
libreoffice Manual_Test_Cases.csv
# or
excel Manual_Test_Cases.csv
```

### 2. Prepare Test Environment
- Deploy the application (see `/Project/DOCKER_SETUP.md`)
- Seed test data
- Create test user accounts
- Configure email/notification services

### 3. Execute Tests
- Follow the test execution guidelines in `MANUAL_TEST_CASES.md`
- Update status in the CSV file as you complete tests
- Document failures with screenshots and logs

### 4. Report Results
- Generate test summary from CSV
- Create bug reports for failures
- Schedule retests for fixed issues

## Test Execution Status

Track your progress:
- ⏳ **Not Run** - Test not yet executed
- ✅ **Pass** - Test passed successfully
- ❌ **Fail** - Test failed, bug reported
- 🚫 **Blocked** - Cannot execute due to dependency

## Integration with Automated Tests

This manual test suite complements the automated tests in `/Project/backend/src/test/`:
- **Automated Tests:** Unit and integration tests (JUnit)
- **Manual Tests:** End-to-end, UI, and user acceptance tests

See `/Project/docs/TESTING_GUIDE.md` for comprehensive testing information.

## Reference Documentation

- **Requirements:** `/Milestones/pm/AIU Trips & Events Report.pdf`
- **API Docs:** `/Project/docs/API_DOCUMENTATION.md`
- **Testing Guide:** `/Project/docs/TESTING_GUIDE.md`
- **Architecture:** `/Milestones/pm/Architecture/`

## Contributing

When adding new test cases:
1. Follow the template in `MANUAL_TEST_CASES.md`
2. Add entry to CSV file
3. Update test count in this README
4. Link to relevant functional requirements

## Contact

For questions about test cases:
- Review the requirements document
- Check existing test coverage
- Consult with QA team

---

**Last Updated:** December 2025  
**Version:** 1.0  
**Status:** Initial Release
