# Vibe Tests - AI-Assisted Project Generation Prompts

This folder contains carefully crafted prompts using various prompting frameworks to generate the complete AIU Trips & Events Management System using AI (Large Language Models).

## 📁 Folder Structure

```
vibe_tests/
├── README.md (this file)          # Overview and usage guide
├── data/                          # Source data for AI generation
│   ├── README.md                  # Data documentation
│   └── *.pu                       # PlantUML diagrams (After DP)
├── 01_chain_of_thought_prompt.md  # CoT framework
├── 02_role_based_prompt.md        # Role-playing framework
├── 03_task_decomposition_prompt.md # Task breakdown framework
├── 04_few_shot_examples_prompt.md # Example-based learning framework
├── 05_context_aware_prompt.md     # Repository-aware framework
├── 06_comprehensive_master_prompt.md # Combined multi-framework approach
└── [Output directories - created by AI]
    ├── 01_chain_of_thought/       # Generated code from prompt 01
    ├── 02_role_based/             # Generated code from prompt 02
    ├── 03_task_decomposition/     # Generated code from prompt 03
    ├── 04_few_shot/               # Generated code from prompt 04
    ├── 05_context_aware/          # Generated code from prompt 05
    └── 06_comprehensive/          # Generated code from prompt 06
```

**Note:** Each prompt instructs the AI to generate code in its own numbered subdirectory (e.g., `01_chain_of_thought/`, `02_role_based/`, etc.), allowing you to compare outputs from different prompting frameworks.

## 🎯 Purpose

These prompts are designed to help AI systems generate a complete, production-ready university event and trip management system from architectural diagrams. Each prompt uses a different prompting strategy to achieve the same goal.

**Key Feature:** Each prompt instructs the AI to generate code in its own numbered output directory (e.g., `01_chain_of_thought/`, `02_role_based/`, etc.), allowing you to:
- Test different prompting frameworks independently
- Compare quality and completeness across frameworks
- Identify which framework works best for your use case

## 📚 Prompt Files Overview

### 1. Chain-of-Thought Prompt (`01_chain_of_thought_prompt.md`)

**Framework:** Chain-of-Thought (CoT) reasoning

**Approach:** Guides the AI through a step-by-step logical reasoning process.

**Key Features:**
- Breaks down the task into clear thinking steps
- Asks the AI to reason through each decision
- Provides a systematic approach to understanding the architecture
- Guides from understanding → analysis → implementation → verification

**Best For:** 
- Complex reasoning tasks
- When you want the AI to "show its work"
- Ensuring thorough analysis before implementation

**Usage Example:**
```
Step 1: Understand the System Architecture
Step 2: Analyze the PlantUML Diagrams  
Step 3: Generate the Backend
Step 4: Generate the Frontend
...
```

---

### 2. Role-Based Prompt (`02_role_based_prompt.md`)

**Framework:** Role-playing / Persona-based prompting

**Approach:** Assigns the AI a specific professional role with expertise.

**Key Features:**
- AI assumes the role of "Senior Software Architect & Full-Stack Developer"
- Emphasizes professional standards and quality expectations
- Frames the task as a professional assignment
- Leverages domain expertise persona

**Best For:**
- When you need professional-level output
- Emphasizing quality and best practices
- Complex architectural decisions

**Usage Example:**
```
You are a Senior Software Architect...
Your expertise includes:
- Design Patterns (mastered)
- Spring Boot & Java Enterprise
- Next.js & Modern React
...
```

---

### 3. Task Decomposition Prompt (`03_task_decomposition_prompt.md`)

**Framework:** Task breakdown / Hierarchical decomposition

**Approach:** Breaks the main task into 14 major tasks and multiple subtasks.

**Key Features:**
- Highly structured breakdown (Task → Subtask → Action)
- Clear time estimates for each task
- Explicit file names and locations
- Step-by-step execution order
- Progress checklist included

**Best For:**
- Large, complex projects
- When you need precise control over implementation order
- Managing time and progress
- Ensuring nothing is missed

**Usage Example:**
```
TASK 1: Preparation & Analysis (15 minutes)
  Subtask 1.1: Read All PlantUML Diagrams
  Subtask 1.2: Create Implementation Order

TASK 2: Backend Setup (10 minutes)
  Subtask 2.1: Create Project Structure
  Subtask 2.2: Create pom.xml
...
```

---

### 4. Few-Shot Examples Prompt (`04_few_shot_examples_prompt.md`)

**Framework:** Few-shot learning / Learning by example

**Approach:** Teaches through concrete examples of diagram-to-code translation.

**Key Features:**
- Shows 5 detailed examples of how to translate PlantUML to code
- Demonstrates entity creation, design patterns, REST controllers
- Includes both backend (Java) and frontend (TypeScript/React) examples
- "Lesson" section after each example to reinforce learning

**Best For:**
- When AI needs to learn a specific coding pattern
- Showing expected code quality and style
- Demonstrating exact transformation process
- Visual learners (pattern matching)

**Usage Example:**
```
Example 1: Translating an Entity from PlantUML to Java
Input PlantUML: [diagram]
Output Java Code: [code]
Lesson: Transform PlantUML classes into JPA entities...

Example 2: Implementing a Design Pattern (Factory)
...
```

---

### 5. Context-Aware Prompt (`05_context_aware_prompt.md`)

**Framework:** Context-rich / Environment-aware prompting

**Approach:** Provides comprehensive context about the repository structure, history, and environment.

**Key Features:**
- Complete repository structure visualization
- Historical context (what was done before)
- Quality metrics from previous implementations
- Detailed package and component structure
- Explicit file locations and dependencies

**Best For:**
- Working in existing codebases
- When repository structure is important
- Avoiding conflicts with existing code
- Understanding project history and evolution

**Usage Example:**
```
Repository Location: /home/runner/work/AIU-Trips-And-Events/...
Current Repository Structure: [full tree]
What Has Been Done: [history]
What You Need to Do: [task]
Source of Truth: [diagrams location]
...
```

---

### 6. Comprehensive Master Prompt (`06_comprehensive_master_prompt.md`)

**Framework:** Multi-framework combined approach

**Approach:** Combines elements from all frameworks above for maximum effectiveness.

**Key Features:**
- Role assignment + Context awareness + Task breakdown + Examples
- Visual formatting (emojis, tables, code blocks)
- Quick reference sections
- Executive summary style
- Pro tips and success criteria

**Best For:**
- Maximum success rate
- One-shot generation attempts
- When you want the most powerful prompt
- Production-ready output

**Usage Example:**
```
🎯 Master Prompt: Complete System Generation
📍 Repository Context: [location]
🎓 What You're Building: [overview]
📐 Architectural Foundation: [patterns]
🏗️ Backend Architecture: [structure]
💻 Frontend Architecture: [structure]
📋 Implementation Checklist: [tasks]
...
```

---

## 🎨 Prompting Frameworks Explained

### 1. Chain-of-Thought (CoT)
**Concept:** Guide AI through explicit reasoning steps
**When to use:** Complex logical tasks, math, planning
**Strength:** Reduces errors, makes reasoning transparent

### 2. Role-Based Prompting
**Concept:** AI assumes a specific expert persona
**When to use:** Professional tasks, specialized knowledge
**Strength:** Taps into trained expert knowledge

### 3. Task Decomposition
**Concept:** Break large task into smaller manageable parts
**When to use:** Large projects, complex workflows
**Strength:** Prevents overwhelm, enables progress tracking

### 4. Few-Shot Learning
**Concept:** Learn from examples before doing the task
**When to use:** Specific formats, patterns to follow
**Strength:** Shows exact expected output

### 5. Context-Aware Prompting
**Concept:** Provide rich environmental context
**When to use:** Existing codebases, specific constraints
**Strength:** Prevents conflicts, leverages history

### 6. Multi-Framework
**Concept:** Combine multiple frameworks
**When to use:** Critical tasks, maximum success needed
**Strength:** Redundancy, multiple perspectives

---

## 📊 Prompt Comparison Matrix

| Prompt | Lines | Complexity | Best Use Case | Est. Success Rate* |
|--------|-------|------------|---------------|-------------------|
| 01 - Chain-of-Thought | 280 | High | Complex reasoning | ~85% |
| 02 - Role-Based | 340 | Medium | Professional output | ~80% |
| 03 - Task Decomposition | 560 | Very High | Large projects | ~90% |
| 04 - Few-Shot Examples | 550 | Medium | Learning patterns | ~75% |
| 05 - Context-Aware | 700 | High | Existing codebases | ~88% |
| 06 - Comprehensive | 450 | Medium-High | One-shot generation | ~95% |

*Est. Success Rate = Projected likelihood of generating complete, compilable, quality code based on prompting framework effectiveness. Actual results may vary based on AI model capabilities and task complexity.

---

## 🚀 How to Use These Prompts

### Quick Start

1. **Choose a prompt** based on your needs (see comparison above)
2. **Copy the entire content** of the chosen `.md` file
3. **Paste into your AI assistant** (ChatGPT, Claude, etc.)
4. **Wait for generation** (typically 10-30 minutes for AI processing)
5. **Review and test** the generated code in its designated output folder

**Output Directories:**
Each prompt generates code in its own subdirectory:
- Prompt 01 → `vibe_tests/01_chain_of_thought/`
- Prompt 02 → `vibe_tests/02_role_based/`
- Prompt 03 → `vibe_tests/03_task_decomposition/`
- Prompt 04 → `vibe_tests/04_few_shot/`
- Prompt 05 → `vibe_tests/05_context_aware/`
- Prompt 06 → `vibe_tests/06_comprehensive/`

This allows you to compare outputs from different frameworks side-by-side.

### Recommended Workflow

**For first-time generation:**
```
Use: 06_comprehensive_master_prompt.md
Why: Highest success rate, combines all frameworks
```

**For iterative refinement:**
```
Use: 03_task_decomposition_prompt.md
Why: Allows step-by-step verification
```

**For learning/experimentation:**
```
Use: 04_few_shot_examples_prompt.md
Why: Shows concrete examples to learn from
```

**For working in existing codebase:**
```
Use: 05_context_aware_prompt.md
Why: Respects existing structure and history
```

### Tips for Best Results

1. ✅ **Read the prompt yourself first** - Understand what it's asking
2. ✅ **Use latest AI models** - GPT-4, Claude 3 Opus, etc.
3. ✅ **Provide data folder** - Ensure `vibe_tests/data/` is accessible
4. ✅ **Monitor generation** - Check for errors during generation
5. ✅ **Test incrementally** - Compile and test as code is generated
6. ✅ **Iterate if needed** - Use follow-up prompts to fix issues

### Common Issues & Solutions

**Issue:** AI generates incomplete code
**Solution:** Use 03_task_decomposition_prompt.md and generate task by task

**Issue:** Code doesn't compile
**Solution:** Ask AI to fix compilation errors, reference diagram files

**Issue:** Patterns not correctly implemented
**Solution:** Use 04_few_shot_examples_prompt.md to show correct pattern implementation

**Issue:** Frontend and backend don't integrate
**Solution:** Use 06_comprehensive_master_prompt.md which ensures integration

---

## 📈 Expected Quality Metrics

Based on analysis in `report/05_vibe_coding_analysis.md`, using After DP diagrams with these prompts should achieve:

| Metric | Target | Notes |
|--------|--------|-------|
| **Compilation Success** | 100% | All code compiles without errors |
| **Pattern Correctness** | 95-97% | Design patterns properly implemented |
| **Code Organization** | 95-98% | Clean package structure |
| **Documentation** | 85-90% | Javadoc and comments |
| **SOLID Principles** | 90-95% | Clean architecture |
| **Overall Quality** | 8.5-8.7/10 | Production-ready |

---

## 🎯 Success Criteria

After using any of these prompts, you should have:

**Backend (Spring Boot):**
- ✅ ~137 Java files
- ✅ All 11 design patterns implemented
- ✅ 100% compilation success
- ✅ Complete REST API with JWT security
- ✅ QR code generation functional

**Frontend (Next.js):**
- ✅ ~40 React components
- ✅ TypeScript type safety
- ✅ Responsive Tailwind CSS design
- ✅ Complete user flows
- ✅ API integration working

**DevOps:**
- ✅ Dockerfiles for both services
- ✅ docker-compose.yml orchestration
- ✅ Environment configuration

---

## 📖 Related Documentation

- **Data Documentation:** `data/README.md` - Explains the PlantUML diagrams
- **Vibe Coding Analysis:** `../report/05_vibe_coding_analysis.md` - Quality metrics and comparison
- **Project README:** `../README.md` - Main project documentation
- **Implementation Guide:** `../Project/docs/` - Existing implementation docs

---

## 🤝 Contributing

To add a new prompt:

1. Choose a unique prompting framework
2. Create a new file: `0X_framework_name_prompt.md`
3. Follow the structure of existing prompts
4. Add entry to this README with comparison data
5. Test the prompt and document success rate

---

## 📝 Notes

- All prompts assume repository location: `/home/runner/work/AIU-Trips-And-Events/AIU-Trips-And-Events/`
- Diagrams are sourced from `vibe_tests/data/*.pu`
- Target output directory is `Project/` (backend and frontend)
- Prompts are designed for LLMs like GPT-4, Claude 3, etc.
- Expected generation time: 6-7 hours of AI processing
- Quality target: 8.5+/10 (based on Scenario 2 metrics)

---

## 🔗 Quick Links

- [Chain-of-Thought Prompt](./01_chain_of_thought_prompt.md)
- [Role-Based Prompt](./02_role_based_prompt.md)
- [Task Decomposition Prompt](./03_task_decomposition_prompt.md)
- [Few-Shot Examples Prompt](./04_few_shot_examples_prompt.md)
- [Context-Aware Prompt](./05_context_aware_prompt.md)
- [Comprehensive Master Prompt](./06_comprehensive_master_prompt.md)
- [Data Folder](./data/)

---

**Created:** December 2024  
**Purpose:** AI-Assisted Code Generation Experimentation  
**Status:** Ready for use  
**Quality Target:** 8.7+/10
