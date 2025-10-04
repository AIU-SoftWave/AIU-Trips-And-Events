# Contributing to AIU Trips and Events

Thank you for considering contributing to the AIU Trips and Events system! This document provides guidelines for contributing to the project.

## Table of Contents
1. [Code of Conduct](#code-of-conduct)
2. [Getting Started](#getting-started)
3. [Development Workflow](#development-workflow)
4. [Coding Standards](#coding-standards)
5. [Testing Guidelines](#testing-guidelines)
6. [Pull Request Process](#pull-request-process)
7. [Issue Reporting](#issue-reporting)

---

## Code of Conduct

### Our Pledge

We are committed to providing a welcoming and inclusive environment for all contributors.

### Expected Behavior

- Be respectful and professional
- Provide constructive feedback
- Accept criticism gracefully
- Focus on what is best for the project

### Unacceptable Behavior

- Harassment or discrimination
- Trolling or inflammatory comments
- Personal attacks
- Publishing private information

---

## Getting Started

### Prerequisites

Before contributing, ensure you have:
- Java 17+
- Node.js 18+
- Maven
- Git
- A code editor (VS Code, IntelliJ IDEA recommended)

### Fork and Clone

1. Fork the repository on GitHub
2. Clone your fork:
```bash
git clone https://github.com/your-username/AIU-Trips-And-Events.git
cd AIU-Trips-And-Events
```

3. Add upstream remote:
```bash
git remote add upstream https://github.com/AIU-SoftWave/AIU-Trips-And-Events.git
```

4. Create a feature branch:
```bash
git checkout -b feature/your-feature-name
```

---

## Development Workflow

### 1. Sync with Upstream

Before starting work:
```bash
git fetch upstream
git checkout main
git merge upstream/main
```

### 2. Create Feature Branch

```bash
git checkout -b feature/add-payment-integration
# or
git checkout -b fix/booking-bug
# or
git checkout -b docs/update-readme
```

### 3. Make Changes

- Write clean, maintainable code
- Follow coding standards
- Add tests for new features
- Update documentation

### 4. Commit Changes

Use conventional commit messages:
```bash
git commit -m "feat: add Stripe payment integration"
git commit -m "fix: resolve booking duplicate issue"
git commit -m "docs: update API documentation"
git commit -m "test: add unit tests for AuthService"
```

**Commit Types:**
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation only
- `style`: Code style changes (formatting)
- `refactor`: Code refactoring
- `test`: Adding tests
- `chore`: Maintenance tasks

### 5. Push and Create PR

```bash
git push origin feature/your-feature-name
```

Then create a Pull Request on GitHub.

---

## Coding Standards

### Java/Spring Boot Backend

#### Code Style

```java
// Good: Clear naming, proper formatting
@Service
public class BookingService {
    
    private final BookingRepository bookingRepository;
    private final EmailService emailService;
    
    @Autowired
    public BookingService(BookingRepository bookingRepository, EmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.emailService = emailService;
    }
    
    @Transactional
    public Booking createBooking(Long eventId, String userEmail) {
        // Validate inputs
        if (eventId == null || userEmail == null) {
            throw new IllegalArgumentException("Event ID and user email are required");
        }
        
        // Business logic
        // ...
    }
}
```

#### Best Practices

1. **Use dependency injection:**
```java
@Autowired
private final SomeService someService; // Good

// Instead of:
private SomeService someService = new SomeService(); // Bad
```

2. **Exception handling:**
```java
try {
    // risky operation
} catch (SpecificException e) {
    logger.error("Error occurred: {}", e.getMessage());
    throw new CustomException("User-friendly message", e);
}
```

3. **Validation:**
```java
@PostMapping("/events")
public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event) {
    // @Valid triggers validation
}
```

4. **Logging:**
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

private static final Logger logger = LoggerFactory.getLogger(MyClass.class);

logger.debug("Debug message");
logger.info("Info message");
logger.warn("Warning message");
logger.error("Error message", exception);
```

### TypeScript/React Frontend

#### Code Style

```typescript
// Good: TypeScript with proper types
interface Event {
  id: number;
  title: string;
  date: Date;
}

const EventCard: React.FC<{ event: Event }> = ({ event }) => {
  return (
    <div className="event-card">
      <h3>{event.title}</h3>
      <p>{event.date.toLocaleDateString()}</p>
    </div>
  );
};

export default EventCard;
```

#### Best Practices

1. **Use TypeScript strictly:**
```typescript
// Good
const handleSubmit = (data: FormData): void => {
  // ...
};

// Bad
const handleSubmit = (data: any) => {
  // ...
};
```

2. **Component structure:**
```typescript
// Good: Functional components with hooks
import { useState, useEffect } from 'react';

const MyComponent: React.FC = () => {
  const [data, setData] = useState<DataType[]>([]);
  
  useEffect(() => {
    fetchData();
  }, []);
  
  return <div>{/* JSX */}</div>;
};
```

3. **Error handling:**
```typescript
try {
  const response = await api.getEvents();
  setEvents(response.data);
} catch (error) {
  console.error('Failed to fetch events:', error);
  showErrorToast('Failed to load events');
}
```

### General Guidelines

1. **DRY Principle:** Don't Repeat Yourself
2. **SOLID Principles:** Single Responsibility, Open/Closed, etc.
3. **Clean Code:** Meaningful names, small functions, clear logic
4. **Comments:** Explain WHY, not WHAT
5. **Documentation:** Update docs with code changes

---

## Testing Guidelines

### Backend Testing

#### Unit Tests (JUnit)

```java
@SpringBootTest
class BookingServiceTest {
    
    @Autowired
    private BookingService bookingService;
    
    @MockBean
    private BookingRepository bookingRepository;
    
    @Test
    void shouldCreateBookingSuccessfully() {
        // Arrange
        Long eventId = 1L;
        String userEmail = "test@aiu.edu";
        
        // Act
        Booking result = bookingService.createBooking(eventId, userEmail);
        
        // Assert
        assertNotNull(result);
        assertEquals("CONFIRMED", result.getStatus());
    }
    
    @Test
    void shouldThrowExceptionWhenEventFull() {
        // Test error scenarios
    }
}
```

#### Integration Tests

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookingControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void shouldCreateBookingViaAPI() {
        ResponseEntity<Booking> response = restTemplate
            .postForEntity("/api/bookings/event/1", null, Booking.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
```

### Frontend Testing

#### Component Tests (Jest + React Testing Library)

```typescript
import { render, screen } from '@testing-library/react';
import EventCard from './EventCard';

describe('EventCard', () => {
  it('should render event details', () => {
    const event = {
      id: 1,
      title: 'Test Event',
      date: new Date('2024-12-01')
    };
    
    render(<EventCard event={event} />);
    
    expect(screen.getByText('Test Event')).toBeInTheDocument();
  });
});
```

### Test Coverage

Maintain minimum test coverage:
- Backend: 70% code coverage
- Frontend: 60% code coverage

Run coverage reports:
```bash
# Backend
mvn test jacoco:report

# Frontend
npm run test:coverage
```

---

## Pull Request Process

### Before Submitting

1. **Run tests:**
```bash
mvn test  # Backend
npm test  # Frontend
```

2. **Check code quality:**
```bash
mvn checkstyle:check
npm run lint
```

3. **Update documentation:**
- Update README if needed
- Update API docs for endpoint changes
- Add inline code documentation

4. **Rebase on latest main:**
```bash
git fetch upstream
git rebase upstream/main
```

### PR Checklist

- [ ] Code follows project style guidelines
- [ ] Tests added/updated and passing
- [ ] Documentation updated
- [ ] No merge conflicts
- [ ] Commit messages follow convention
- [ ] PR description explains changes

### PR Template

```markdown
## Description
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
- [ ] Unit tests added/updated
- [ ] Integration tests added/updated
- [ ] Manual testing completed

## Screenshots (if applicable)
Add screenshots for UI changes

## Related Issues
Closes #123
```

### Review Process

1. Automated checks must pass (CI/CD)
2. At least one approval required
3. Address review comments
4. Maintainer will merge

---

## Issue Reporting

### Bug Reports

Use the bug report template:

```markdown
## Bug Description
Clear description of the bug

## Steps to Reproduce
1. Go to '...'
2. Click on '...'
3. See error

## Expected Behavior
What should happen

## Actual Behavior
What actually happens

## Screenshots
If applicable

## Environment
- OS: [e.g., Windows 10]
- Browser: [e.g., Chrome 120]
- Version: [e.g., 1.0.0]
```

### Feature Requests

```markdown
## Feature Description
Clear description of the feature

## Problem It Solves
What problem does this solve?

## Proposed Solution
How should it work?

## Alternatives Considered
Other approaches you've thought about
```

---

## Development Environment Setup

### IntelliJ IDEA (Recommended for Backend)

1. Import as Maven project
2. Enable annotation processing (Lombok)
3. Install plugins: Lombok, Spring Boot
4. Configure code style: Google Java Style

### VS Code (Recommended for Frontend)

1. Install extensions:
   - ESLint
   - Prettier
   - TypeScript and JavaScript
2. Configure settings:
```json
{
  "editor.formatOnSave": true,
  "editor.codeActionsOnSave": {
    "source.fixAll.eslint": true
  }
}
```

---

## Questions?

- Check [Documentation](../README.md)
- Ask in [Discussions](https://github.com/AIU-SoftWave/AIU-Trips-And-Events/discussions)
- Contact maintainers

---

Thank you for contributing! 🎉
