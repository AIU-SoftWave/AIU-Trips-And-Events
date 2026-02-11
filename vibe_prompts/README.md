# Vibe Prompts - AI Project Generation Framework

> Generate the complete AIU Trips & Events project with a single prompt!

## 🚀 Quick Start - Generate Entire Project

**Want the complete project in one go?**

Use the **comprehensive project generation prompt**: [`generate_project_prompt.md`](generate_project_prompt.md)

This single prompt generates:
- ✅ Complete Spring Boot backend (~150 files)
- ✅ Complete React frontend (~80 files)  
- ✅ All 11 design patterns implemented
- ✅ Database schema
- ✅ Complete documentation
- ✅ Build configurations
- ✅ **Total: 230+ production-ready files**

**Estimated time:** 90-120 minutes

---

## Alternative: Component-by-Component Generation

For testing individual components or patterns, use:
1. **Read context** from `vibe_prompts/data/system_context.md` 
2. **Select prompt** from `vibe_prompts/prompts.md` (e.g., P001, P002)
3. **Access repository** at `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events`
4. **Generate code** directly in `vibe_prompts/outputs/PROMPT_ID/`
5. **Create report** by copying `vibe_prompts/report_template.md` to `vibe_prompts/reports/PROMPT_ID_report.md` and filling it

## Folder Structure

```
vibe_prompts/
├── README.md                     # This file
├── generate_project_prompt.md   # 🌟 SINGLE PROMPT for entire project!
├── prompts.md                    # 15 component prompts (P001-P015)
├── EXAMPLE.md                    # Walkthrough example
├── report_template.md            # Template for individual test reports
├── comparison_template.md        # Template for comparing platforms
├── data/
│   └── system_context.md         # System information for AI
├── outputs/
│   ├── FULL_PROJECT/            # 🌟 Complete project (230+ files)
│   ├── P001/                     # Generated code for prompt P001
│   ├── P002/                     # Generated code for prompt P002
│   └── ...
└── reports/
    ├── FULL_PROJECT_report.md   # Report for complete project
    ├── P001_report.md            # Test report for P001
    └── ...
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

## How Agentic AI Uses This Framework

### Step 1: Read Context
The AI agent reads system information:
```
/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/data/system_context.md
```

This file contains all information about the AIU Trips & Events system architecture, patterns, and requirements.

### Step 2: Select Prompt

The AI reviews `prompts.md` and selects the appropriate prompt. Each prompt has:
- **ID** (e.g., P001)
- **Description** (what it tests)
- **Full context path** (repository location)
- **Chaining indicator** (if multi-stage is needed)
- **Time estimate**

### Step 3: Generate Code

The AI agent:
1. Creates output directory: `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/outputs/PROMPT_ID/`
2. Generates all required files with proper structure
3. Creates README.md with implementation notes

Example for P001:
```
/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/outputs/P001/
├── src/main/java/com/aiu/trips/observer/
│   ├── Subject.java
│   ├── Observer.java
│   └── ...
└── README.md
```

### Step 4: Create Report

The AI agent:
1. Copies template: `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/report_template.md`
2. Creates report: `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/reports/PROMPT_ID_report.md`
3. Fills in all sections with test results and metrics

### Step 5: Compare (Optional)

For comparing multiple AI platforms, the agent:
1. Copies: `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/comparison_template.md`
2. Creates: `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/reports/PROMPT_ID_comparison.md`
3. Fills comparison data from all platform reports

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

## Example Workflow for Agentic AI

The AI agent follows this workflow:

```
1. Read context from: 
   /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/data/system_context.md

2. Select prompt (e.g., P001 - Observer Pattern) from:
   /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/prompts.md

3. Create output directory:
   mkdir -p /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/outputs/P001

4. Generate all required files in proper structure

5. Try to compile (if applicable):
   cd /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/outputs/P001
   mvn compile  # or npm build, etc.

6. Copy and fill report template:
   cp /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/report_template.md \
      /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/reports/P001_report.md
   
7. Fill all sections with metrics and analysis

8. (Optional) For platform comparison:
   cp /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/comparison_template.md \
      /home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/vibe_prompts/reports/P001_comparison.md
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
