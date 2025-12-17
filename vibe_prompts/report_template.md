# Test Report Template

> Copy this template to `vibe_prompts/reports/PROMPT_ID_report.md` and fill it out

---

# Test Report: [PROMPT_ID] - [Test Name]

## Test Information

| Field | Value |
|-------|-------|
| **Prompt ID** | [e.g., P001] |
| **Test Name** | [e.g., Observer Pattern for Notifications] |
| **Platform** | [GPT-4 / Claude-3 / Gemini / Copilot / Other] |
| **Model Version** | [e.g., gpt-4-turbo, claude-3-opus] |
| **Framework** | [Zero-Shot / Few-Shot / CoT / etc.] |
| **Test Date** | [YYYY-MM-DD] |
| **Tester** | [Your Name] |
| **Duration** | [X minutes] |

---

## Prompt Details

**Prompt Used:** [Copy the exact prompt you used]

**Additional Context:** [Any extra information provided to the AI]

**Expected Output:** [What you expected to get]

---

## Generation Results

### Code Generated

| Metric | Value |
|--------|-------|
| **Lines of Code** | [Number] |
| **Files Generated** | [Count] |
| **Generation Time** | [X minutes] |
| **Iterations Needed** | [1, 2, 3...] |
| **Compilation Success** | [Yes/No / X%] |

### File List

Generated files:
```
[List all files generated]
vibe_prompts/outputs/PROMPT_ID/
├── file1.java
├── file2.java
└── ...
```

---

## Quality Evaluation

### 1. Compilation & Syntax (Weight: 20%)

**Score:** [0-10]

**Details:**
- [ ] Compiles without errors
- [ ] No syntax issues
- [ ] All imports correct
- [ ] Proper package structure

**Issues Found:**
- [List compilation errors]

**Fixes Required:**
- [List fixes needed]

---

### 2. Pattern Implementation (Weight: 25%)

**Score:** [0-10]

**Details:**
- [ ] Pattern correctly identified
- [ ] Proper interface design
- [ ] Correct implementations
- [ ] Appropriate usage

**Pattern Correctness:** [0-100%]

**Issues Found:**
- [List pattern issues]

**Comments:**
[Your assessment of pattern implementation]

---

### 3. Architecture & Design (Weight: 20%)

**Score:** [0-10]

**Details:**
- [ ] Clear architecture
- [ ] SOLID principles followed
- [ ] Clean abstractions
- [ ] Proper separation of concerns

**SOLID Score:** [0-100%]
- Single Responsibility: [Yes/No]
- Open/Closed: [Yes/No]
- Liskov Substitution: [Yes/No]
- Interface Segregation: [Yes/No]
- Dependency Inversion: [Yes/No]

**Comments:**
[Your assessment]

---

### 4. Documentation (Weight: 15%)

**Score:** [0-10]

**Details:**
- [ ] JavaDoc/JSDoc present
- [ ] Clear comments
- [ ] README included
- [ ] Usage examples provided

**Documentation Quality:** [Poor / Fair / Good / Excellent]

**Comments:**
[Your assessment]

---

### 5. Testing (Weight: 10%)

**Score:** [0-10]

**Details:**
- [ ] Unit tests included
- [ ] Test coverage adequate
- [ ] Tests pass
- [ ] Edge cases covered

**Test Coverage:** [0-100%]

**Comments:**
[Your assessment]

---

### 6. Best Practices (Weight: 10%)

**Score:** [0-10]

**Details:**
- [ ] Naming conventions followed
- [ ] Proper package structure
- [ ] Error handling implemented
- [ ] Logging included

**Comments:**
[Your assessment]

---

## Overall Quality Score

### Weighted Score Calculation

| Category | Weight | Score | Weighted |
|----------|--------|-------|----------|
| Compilation & Syntax | 20% | [X/10] | [X] |
| Pattern Implementation | 25% | [X/10] | [X] |
| Architecture & Design | 20% | [X/10] | [X] |
| Documentation | 15% | [X/10] | [X] |
| Testing | 10% | [X/10] | [X] |
| Best Practices | 10% | [X/10] | [X] |
| **TOTAL** | **100%** | | **[X/10]** |

### Quality Rating

- **9.0 - 10.0:** Excellent - Production ready
- **8.0 - 8.9:** Very Good - Minor improvements needed
- **7.0 - 7.9:** Good - Some improvements needed
- **6.0 - 6.9:** Fair - Significant improvements needed
- **Below 6.0:** Poor - Major rework required

**Overall Rating:** [Excellent / Very Good / Good / Fair / Poor]

---

## Detailed Analysis

### ✅ Strengths

1. [Strength 1]
2. [Strength 2]
3. [Strength 3]
4. [Additional strengths...]

### ❌ Weaknesses

1. [Weakness 1]
2. [Weakness 2]
3. [Weakness 3]
4. [Additional weaknesses...]

### ⚠️ Issues Encountered

1. [Issue 1 - Description and impact]
2. [Issue 2 - Description and impact]
3. [Issue 3 - Description and impact]

### 🔧 Manual Fixes Required

1. [Fix 1 - What was fixed and why]
2. [Fix 2 - What was fixed and why]
3. [Fix 3 - What was fixed and why]

---

## Code Review Notes

### What Worked Well

[Detailed description of what the AI did well]

### What Didn't Work

[Detailed description of problems or shortcomings]

### Surprises

[Any unexpected behaviors, positive or negative]

### Comparison to Expected

[How did the output compare to what you expected?]

---

## Integration Testing

### Compilation Test

**Command Used:** [e.g., mvn compile, npm build]

**Result:** [Success / Failed]

**Output:**
```
[Paste compilation output]
```

### Runtime Test

**Test Performed:** [Describe what you tested]

**Result:** [Success / Failed / Partial]

**Details:**
[Describe the results]

---

## Recommendations

### For This Platform/Model

**Suitable for:**
- [Project types or scenarios where this performs well]

**Not suitable for:**
- [Project types or scenarios where this doesn't perform well]

### Improvements Suggested

1. [Suggestion 1]
2. [Suggestion 2]
3. [Suggestion 3]

### Would Use Again?

**Yes / No / Maybe**

**Reason:** [Explain your decision]

---

## Comparison Data

> Fill this section when comparing multiple platforms

### vs [Other Platform]

| Metric | This Test | Other Platform | Winner |
|--------|-----------|----------------|--------|
| Generation Time | [X min] | [Y min] | |
| Lines of Code | [X] | [Y] | |
| Quality Score | [X/10] | [Y/10] | |
| Compilation | [X%] | [Y%] | |
| Pattern Score | [X%] | [Y%] | |

**Better at:** [What this platform did better]

**Worse at:** [What this platform did worse]

---

## Additional Notes

[Any other observations, comments, or information]

---

## Attachments

- [ ] Generated code files saved to `vibe_prompts/outputs/PROMPT_ID/`
- [ ] Screenshots (if applicable)
- [ ] Test results
- [ ] Error logs

---

## Conclusion

**Summary:** [Brief summary of the test results]

**Key Takeaway:** [Main lesson learned from this test]

**Overall Assessment:** [Final thoughts on this test]

---

**Report Completed By:** [Your Name]  
**Date:** [YYYY-MM-DD]  
**Version:** 1.0
