# Vibe Prompts - AI Code Generation Testing Framework

## Overview

This folder contains comprehensive prompts designed for testing and evaluating AI-assisted code generation across different platforms, models, and prompting frameworks for the AIU Trips & Events Management System.

## Purpose

The prompts in this folder enable systematic evaluation of:
- **AI Platform Performance**: Compare GPT-4, Claude, Gemini, Copilot, and other models
- **Prompting Framework Effectiveness**: Test Zero-Shot, Few-Shot, Chain-of-Thought, and more
- **Code Quality Metrics**: Measure compilation success, pattern correctness, and architecture quality
- **Development Efficiency**: Evaluate speed, accuracy, and overall productivity

## Files

### `ai_testing_prompts.md`
Comprehensive collection of testing prompts including:
- **8 Prompting Frameworks**:
  1. Zero-Shot Prompting
  2. Few-Shot Prompting
  3. Chain-of-Thought (CoT)
  4. Tree-of-Thought (ToT)
  5. ReAct (Reasoning + Acting)
  6. Self-Consistency
  7. Constitutional AI
  8. Prompt Chaining

- **Test Scenarios**:
  - Pattern Implementation Tests
  - Component Creation Tests
  - Service Layer Implementation
  - API Controller Creation
  - Architecture Design
  - Debug and Fix Scenarios
  - Feature Addition Tests

- **Benchmarking Tests**:
  - Speed Test (code generation speed)
  - Quality Test (code quality comparison)
  - Pattern Implementation Test (design pattern correctness)
  - Integration Test (end-to-end workflows)

- **Evaluation Framework**:
  - Code Quality Rubrics
  - Speed Metrics
  - Accuracy Metrics
  - Reporting Templates

## Quick Start

### 1. Select a Prompt
Choose a prompt from `ai_testing_prompts.md` based on what you want to test:
```bash
# For testing Zero-Shot prompting
Use: Test 1.1 - Pattern Implementation (Zero-Shot)

# For testing Few-Shot prompting
Use: Test 2.1 - Service Layer Implementation (Few-Shot)

# For benchmarking platforms
Use: Benchmark 1 - Speed Test
```

### 2. Prepare Your Environment
- Clone the AIU Trips & Events repository
- Set up access to AI platforms you want to test
- Prepare a spreadsheet for tracking results

### 3. Execute the Test
1. Copy the Base Context Prompt (from System Context Prompts section)
2. Copy the specific test prompt
3. Submit both to your AI platform
4. Record start time
5. Save the generated output
6. Record end time

### 4. Evaluate Results
Use the evaluation criteria from the document:
- Compilation & Syntax (20%)
- Pattern Implementation (25%)
- Architecture & Design (20%)
- Documentation (15%)
- Testing (10%)
- Best Practices (10%)

### 5. Document Findings
Use the provided reporting templates:
- Individual Test Report (for single platform)
- Comparative Report (for multiple platforms)

## Test Categories

### Framework-Based Tests
Test how different prompting frameworks affect code generation quality:
- **Zero-Shot**: Direct instructions without examples
- **Few-Shot**: Include 2-3 examples for pattern learning
- **CoT**: Step-by-step reasoning for complex tasks
- **ToT**: Multiple reasoning paths exploration
- **ReAct**: Reasoning + Acting for debugging
- **Self-Consistency**: Generate multiple solutions and vote
- **Constitutional**: With security and ethical constraints
- **Chaining**: Multi-stage development workflows

### Scenario-Based Tests
Real-world development scenarios:
- Fresh implementation from diagrams
- Pattern refactoring of existing code
- New feature additions
- Bug fixes and debugging
- Performance optimization

### Benchmarking Tests
Quantitative comparisons:
- Speed: Time to generate code
- Quality: Code quality scores
- Pattern Correctness: Design pattern adherence
- Integration: End-to-end workflow success

## Expected Outcomes

After completing tests with this framework, you should have:

1. **Platform Comparison Report**
   - Which platform performs best for backend development?
   - Which excels at frontend generation?
   - Which handles design patterns most accurately?

2. **Framework Effectiveness Analysis**
   - Which prompting framework yields highest quality?
   - When to use Chain-of-Thought vs Few-Shot?
   - How much does context affect output quality?

3. **Quality Benchmarks**
   - Baseline quality scores for each platform
   - Expected compilation success rates
   - Pattern implementation accuracy metrics

4. **Best Practices Guide**
   - Recommended platforms for different tasks
   - Optimal prompting strategies
   - Integration and workflow recommendations

## Data Collection

Track results using this structure:
```csv
Test_ID, Platform, Model, Framework, Prompt_Type, Time_Minutes, LOC, Compilation_Success, Pattern_Score, Quality_Score, Notes
T001, GPT-4, gpt-4-turbo, Zero-Shot, Backend, 12, 450, 100%, 9/10, 8.5/10, "Good patterns"
T002, Claude-3, opus, Few-Shot, Frontend, 15, 380, 95%, 8/10, 8.0/10, "Minor issues"
...
```

## Recommendations

### For Systematic Testing
1. Start with Zero-Shot tests to establish baselines
2. Progress to Few-Shot and CoT for complex tasks
3. Use benchmarking tests for platform comparisons
4. Document all findings using provided templates

### For Best Results
- Use the exact prompts as written (for consistency)
- Test the same prompt across multiple platforms
- Record all metrics consistently
- Include both successes and failures in reports

### For Production Use
- Focus on tests relevant to your project needs
- Prioritize quality over speed initially
- Validate AI-generated code thoroughly
- Combine AI generation with human review

## Reference

This testing framework is based on the methodology and findings from:
- `/report/05_vibe_coding_analysis.md` - Comprehensive analysis of AI-assisted code generation
- Real project data from AIU Trips & Events system development
- Comparison of Before DP vs After DP implementations

## Timeline Suggestion

**Week 1**: Setup and Basic Tests
- Execute Zero-Shot and Few-Shot prompts
- Test 2-3 platforms
- Establish baseline metrics

**Week 2**: Advanced Frameworks
- Test CoT, ToT, ReAct frameworks
- Compare complex pattern implementations
- Analyze advanced scenarios

**Week 3**: Benchmarking
- Run all 4 benchmark tests
- Test across all platforms
- Collect comprehensive data

**Week 4**: Analysis and Reporting
- Analyze collected data
- Generate comparative reports
- Document recommendations
- Share findings

## Contributing

To add new prompts to this collection:
1. Follow the existing format and structure
2. Include clear success criteria
3. Provide evaluation metrics
4. Add to appropriate category
5. Update this README with new additions

## Support

For questions or issues with these prompts:
1. Review the reference document: `/report/05_vibe_coding_analysis.md`
2. Check the existing test results for examples
3. Consult the evaluation criteria for guidance

## Version History

- **v1.0** (December 17, 2024)
  - Initial release
  - 8 prompting frameworks
  - 15+ testing prompts
  - 4 benchmarking tests
  - Complete evaluation framework
  - Reporting templates

---

**Last Updated**: December 17, 2024  
**Maintained By**: AIU Trips & Events Development Team  
**Purpose**: Systematic AI Code Generation Evaluation  
**License**: Internal Use - AIU SoftWave
