# Vibe Prompts - Simple AI Testing Framework

> Simple, copy-paste prompts for testing AI code generation

## Quick Start

1. **Read the context** - Open `data/system_context.md` to understand the AIU Trips & Events system
2. **Pick a prompt** - Open `prompts.md` and find the test you want (e.g., P001, P002)
3. **Copy & paste** - Copy the entire prompt and paste it into your AI tool
4. **Save output** - Save generated code to `outputs/PROMPT_ID/`
5. **Fill report** - Copy `report_template.md` to `reports/PROMPT_ID_report.md` and fill it

## Folder Structure

```
vibe_prompts/
├── README.md                    # This file
├── prompts.md                   # All prompts with IDs (P001-P015)
├── report_template.md           # Template for individual test reports
├── comparison_template.md       # Template for comparing platforms
├── data/
│   └── system_context.md        # System information for AI
├── outputs/
│   ├── P001/                    # Generated code for prompt P001
│   ├── P002/                    # Generated code for prompt P002
│   └── ...
└── reports/
    ├── P001_report.md           # Test report for P001
    ├── P001_gpt4_report.md      # GPT-4 test for P001
    ├── P001_claude_report.md    # Claude test for P001
    └── P001_comparison.md       # Comparison of all platforms for P001
```

## Available Prompts

### Zero-Shot (Direct Instructions)
- **P001** - Observer Pattern for Notifications
- **P002** - React Trip Detail Component  
- **P003** - JPA Repository Queries

### Few-Shot (With Examples)
- **P004** - Service Layer Implementation
- **P005** - REST Controllers

### Chain-of-Thought (Step-by-Step Reasoning)
- **P006** - Strategy Pattern for Pricing
- **P007** - Notification System Design

### Complete Scenarios
- **P008** - Complete Backend from Diagrams ⚠️ (requires chaining)
- **P009** - Complete Frontend Application ⚠️ (requires chaining)
- **P010** - Waiting List Feature
- **P011** - Review and Rating System

### Refactoring
- **P012** - Add Strategy Pattern
- **P013** - Refactor to Builder Pattern

### Benchmarking
- **P014** - Speed Test (User Management)
- **P015** - Quality Test (Command Pattern)

## How to Use

### Step 1: Read Context
```bash
cat data/system_context.md
```

This file has all the information about the AIU Trips & Events system that the AI needs.

### Step 2: Choose a Prompt

Open `prompts.md` and find the prompt you want to test. Each prompt has:
- **ID** (e.g., P001)
- **Description** (what it tests)
- **Copy-paste ready text** (just copy the whole thing)
- **Chaining indicator** (if multi-stage is needed)
- **Time estimate**

### Step 3: Copy & Paste

Copy the entire prompt box and paste it into:
- ChatGPT (GPT-4)
- Claude (Anthropic)
- Gemini (Google)
- GitHub Copilot Chat
- Or any other AI tool

### Step 4: Save Output

The AI will generate code. Save it to:
```
vibe_prompts/outputs/PROMPT_ID/
```

Create the folder structure:
```bash
mkdir -p outputs/P001
# Save files there
```

### Step 5: Fill Report

1. Copy the template:
```bash
cp report_template.md reports/P001_gpt4_report.md
```

2. Open `reports/P001_gpt4_report.md` and fill in:
   - Test information (platform, date, duration)
   - Generation results (lines of code, files)
   - Quality scores (compilation, patterns, architecture)
   - Analysis (strengths, weaknesses, issues)

3. Calculate the overall score using the weighted formula

### Step 6: Compare (Optional)

If you tested multiple platforms:

1. Copy comparison template:
```bash
cp comparison_template.md reports/P001_comparison.md
```

2. Fill in comparison data from all platform reports

3. Identify the winner for each category

## About Prompt Chaining

Some prompts are marked with ⚠️ **YES - Multi-stage recommended**

This means the task is too complex for one prompt. Break it into stages:

**Example: P008 - Complete Backend**
- Stage 1: Generate entities + repositories
- Stage 2: Generate services
- Stage 3: Generate controllers
- Stage 4: Add design patterns
- Stage 5: Add configuration

Complete each stage, verify it works, then move to the next.

## Scoring Guide

### Quality Score Components

1. **Compilation & Syntax (20%)** - Does it compile?
2. **Pattern Implementation (25%)** - Patterns correct?
3. **Architecture & Design (20%)** - Good structure?
4. **Documentation (15%)** - Well documented?
5. **Testing (10%)** - Tests included?
6. **Best Practices (10%)** - Follows standards?

### Score Interpretation

- **9.0-10.0** = Excellent (Production ready)
- **8.0-8.9** = Very Good (Minor fixes)
- **7.0-7.9** = Good (Some improvements)
- **6.0-6.9** = Fair (Significant work)
- **<6.0** = Poor (Major rework)

## Example Workflow

```bash
# 1. Read the context
cat data/system_context.md

# 2. Open prompts file
cat prompts.md
# Find P001 - Observer Pattern

# 3. Copy the P001 prompt box

# 4. Paste into ChatGPT (or your AI tool)

# 5. Save output
mkdir -p outputs/P001
# Copy generated files to outputs/P001/

# 6. Try to compile
cd outputs/P001
mvn compile  # or appropriate build command

# 7. Fill report
cp ../../report_template.md ../../reports/P001_gpt4_report.md
# Open and fill the report

# 8. Repeat with different platform (Claude)
# Copy P001 prompt, paste to Claude
mkdir -p outputs/P001_claude
# Save files, fill report: P001_claude_report.md

# 9. Compare results
cp ../../comparison_template.md ../../reports/P001_comparison.md
# Fill comparison with data from both reports
```

## Tips

### For Best Results

1. **Always include the context** - Mention you read `system_context.md`
2. **Be specific** - Tell the AI exactly where to put files
3. **Ask for complete code** - Request full implementation, not snippets
4. **Request structure** - Ask for proper package/folder structure
5. **Validate immediately** - Try to compile/run right away

### For Accurate Comparison

1. Use the **exact same prompt** across platforms
2. Test at **similar times** (models update)
3. Record **everything** (time, errors, iterations)
4. Be **objective** in reports
5. Test **multiple prompts** before concluding

### For Time Savings

1. Start with **simple prompts** (P001-P003)
2. Use **benchmarking prompts** (P014-P015) for speed tests
3. Save **good outputs** as references
4. **Document** what works best for each platform

## Troubleshooting

### AI doesn't follow structure
→ Add explicit instruction: "Create files in vibe_prompts/outputs/P001/"

### Output is incomplete
→ Try: "Continue from where you left off" or use chaining

### Code doesn't compile
→ Normal! Document errors in report, that's the point of testing

### AI ignores context
→ Paste relevant parts of `system_context.md` directly in prompt

## Goals

This framework helps you:

✅ Test different AI platforms systematically  
✅ Compare code generation quality objectively  
✅ Identify best tools for different tasks  
✅ Build evidence for AI tool selection  
✅ Share results with your team

## Support

- Questions about prompts: Check `prompts.md` for details
- Questions about system: Read `data/system_context.md`
- Questions about scoring: See `report_template.md`
- Questions about comparing: See `comparison_template.md`

---

**Version:** 2.0 (Simplified)  
**Date:** December 17, 2024  
**Purpose:** Simple, practical AI code generation testing
