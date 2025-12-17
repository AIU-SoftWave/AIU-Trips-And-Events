# Example: How to Use This Framework

## Example Test: P001 - Observer Pattern

Let's walk through a complete example of testing prompt P001 with GPT-4.

---

## Step 1: Read the Context

First, open and read `data/system_context.md` to understand:
- The AIU Trips & Events system
- Technology stack (Java Spring Boot, React)
- 11 design patterns used
- Domain entities (User, Activity, Booking, etc.)

---

## Step 2: Find the Prompt

Open `prompts.md` and find **P001 - Observer Pattern for Notifications**

You'll see:
- Description of what to implement
- Complete prompt ready to copy
- Output location: `vibe_prompts/outputs/P001/`
- Time estimate: 5-10 minutes

---

## Step 3: Copy the Prompt

Copy this entire text from `prompts.md`:

```
I'm working on the AIU Trips & Events Management System (Java Spring Boot backend).

Task: Implement the Observer Pattern for the notification system.

Requirements:
1. Create Subject interface for notification publishers
2. Create Observer interface for notification subscribers  
3. Implement concrete observers: EmailObserver, SMSObserver, PushObserver
4. Ensure proper subscribe/unsubscribe mechanisms
5. Implement notification broadcasting to all observers

Technical Details:
- Package: com.aiu.trips.observer
- Use Spring @Component annotations
- Include proper error handling
- Add logging

Generate complete Java code with:
- All interfaces and classes
- Proper package structure
- JavaDoc comments
- Usage example

Output all files to: vibe_prompts/outputs/P001/
```

---

## Step 4: Paste to AI Platform

Open ChatGPT (GPT-4) and paste the prompt. 

**Start time:** 10:00 AM

The AI generates:
1. `Subject.java` interface
2. `Observer.java` interface
3. `EmailObserver.java` class
4. `SMSObserver.java` class
5. `PushObserver.java` class
6. `NotificationPublisher.java` context
7. Usage example

**End time:** 10:08 AM (8 minutes)

---

## Step 5: Save the Output

Create the output folder and save files:

```bash
cd vibe_prompts
mkdir -p outputs/P001/src/main/java/com/aiu/trips/observer
cd outputs/P001/src/main/java/com/aiu/trips/observer

# Save each file the AI generated
# - Subject.java
# - Observer.java
# - EmailObserver.java
# - SMSObserver.java
# - PushObserver.java
# - NotificationPublisher.java
```

---

## Step 6: Test Compilation

Try to compile the code:

```bash
cd vibe_prompts/outputs/P001
# If using Maven
mvn compile

# Result: SUCCESS
# 0 errors, 0 warnings
```

---

## Step 7: Fill the Report

Copy the template:

```bash
cp ../../report_template.md ../../reports/P001_gpt4_report.md
```

Open `reports/P001_gpt4_report.md` and fill it:

### Test Information
- **Prompt ID:** P001
- **Test Name:** Observer Pattern for Notifications
- **Platform:** GPT-4
- **Model Version:** gpt-4-turbo
- **Framework:** Zero-Shot
- **Test Date:** 2024-12-17
- **Tester:** John Doe
- **Duration:** 8 minutes

### Generation Results
- **Lines of Code:** 247
- **Files Generated:** 6
- **Generation Time:** 8 minutes
- **Iterations Needed:** 1
- **Compilation Success:** Yes (100%)

### Quality Scores

#### 1. Compilation & Syntax (20%)
**Score:** 10/10
- ✅ Compiles without errors
- ✅ No syntax issues
- ✅ All imports correct
- ✅ Proper package structure

#### 2. Pattern Implementation (25%)
**Score:** 9/10
- ✅ Pattern correctly identified
- ✅ Proper interface design
- ✅ Correct implementations
- ✅ Appropriate usage
- ⚠️ Minor: Could use more robust unsubscribe

**Pattern Correctness:** 95%

#### 3. Architecture & Design (20%)
**Score:** 9/10
- ✅ Clear architecture
- ✅ SOLID principles followed
- ✅ Clean abstractions
- ✅ Proper separation

**SOLID Score:** 90%

#### 4. Documentation (15%)
**Score:** 8/10
- ✅ JavaDoc present
- ✅ Clear comments
- ⚠️ No README
- ✅ Usage examples

#### 5. Testing (10%)
**Score:** 6/10
- ❌ No unit tests
- ⚠️ Only usage example provided

#### 6. Best Practices (10%)
**Score:** 9/10
- ✅ Naming conventions
- ✅ Proper structure
- ✅ Error handling
- ✅ Logging included

### Overall Score

| Category | Weight | Score | Weighted |
|----------|--------|-------|----------|
| Compilation | 20% | 10/10 | 2.0 |
| Patterns | 25% | 9/10 | 2.25 |
| Architecture | 20% | 9/10 | 1.8 |
| Documentation | 15% | 8/10 | 1.2 |
| Testing | 10% | 6/10 | 0.6 |
| Best Practices | 10% | 9/10 | 0.9 |
| **TOTAL** | **100%** | | **8.75/10** |

**Overall Rating:** Very Good

### Strengths
1. ✅ Perfect compilation - no errors at all
2. ✅ Clean Observer pattern implementation
3. ✅ Good use of Spring annotations
4. ✅ Proper error handling and logging
5. ✅ Clear, readable code

### Weaknesses
1. ❌ No unit tests provided
2. ⚠️ Missing README or setup instructions
3. ⚠️ Unsubscribe mechanism could be more robust

### Issues Encountered
None - code worked first time!

### Conclusion
GPT-4 provided an excellent implementation of the Observer pattern. The code is production-ready with only minor improvements needed (tests and documentation).

---

## Step 8: Test Another Platform

Now test the same prompt with Claude:

1. Copy the **exact same prompt**
2. Paste to Claude
3. Save output to `outputs/P001_claude/`
4. Fill report: `reports/P001_claude_report.md`

**Results:**
- Time: 10 minutes (vs 8 for GPT-4)
- Score: 8.5/10 (vs 8.75 for GPT-4)
- Claude included tests! (bonus)
- But had 2 compilation errors (needed fixes)

---

## Step 9: Compare Results

Copy comparison template:

```bash
cp comparison_template.md reports/P001_comparison.md
```

Fill in the comparison:

### Summary Table

| Metric | GPT-4 | Claude-3 | Winner |
|--------|-------|----------|--------|
| Time | 8 min | 10 min | GPT-4 ✓ |
| LOC | 247 | 318 | Claude ✓ |
| Files | 6 | 8 | Claude ✓ |
| Compilation | 100% | 90% | GPT-4 ✓ |
| Quality | 8.75/10 | 8.5/10 | GPT-4 ✓ |
| Pattern | 95% | 95% | Tie |
| Tests | No | Yes | Claude ✓ |

**Winner: GPT-4** (4 wins vs 3)

### Key Findings

**GPT-4 Strengths:**
- Faster generation (8 min vs 10)
- Perfect compilation
- Cleaner, more focused code

**Claude Strengths:**
- Included unit tests
- More comprehensive (more files)
- Better documentation

**Recommendation:**
- Use **GPT-4** for quick, clean implementations
- Use **Claude** when tests are critical

---

## Step 10: Use the Data

Now you have:
- Objective comparison data
- Detailed quality scores
- Evidence for tool selection
- Shareable reports

You can:
1. Share with your team
2. Make informed tool choices
3. Aggregate data across multiple prompts
4. Build best practices guide

---

## Summary

This example showed:
✅ How to use a prompt (copy & paste)
✅ How to save output (organized folders)
✅ How to fill reports (structured templates)
✅ How to compare platforms (side-by-side)
✅ How to make decisions (data-driven)

**Total time:** ~45 minutes for complete comparison
**Value:** Evidence-based tool selection

---

## Try It Yourself!

Start with simple prompts:
1. P001 - Observer Pattern (this example)
2. P002 - React Component (frontend)
3. P003 - JPA Queries (database)

Then move to complex ones:
- P006 - Strategy Pattern (step-by-step)
- P010 - Waiting List (complete feature)
- P008 - Complete Backend (multi-stage)

**Happy testing!** 🚀
