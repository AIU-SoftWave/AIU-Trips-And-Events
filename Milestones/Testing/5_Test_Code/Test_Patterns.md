# Test Patterns and Best Practices

## Overview

This document describes the testing patterns and best practices used throughout the automated test suite.

---

## 1. Given-When-Then Pattern

All tests follow the Given-When-Then structure for clarity and consistency.

### Pattern Structure
```java
@Test
void testFeature_WithCondition_ExpectedBehavior() {
    // Given: Setup test data and preconditions
    User user = createTestUser();
    Event event = createTestEvent();
    
    // When: Execute the action being tested
    Booking result = bookingService.createBooking(user.getId(), event.getId());
    
    // Then: Verify expected outcomes
    assertNotNull(result);
    assertEquals(BookingStatus.CONFIRMED, result.getStatus());
}
```

### Benefits
- ✅ Clear test structure
- ✅ Easy to understand
- ✅ Follows BDD principles
- ✅ Self-documenting

---

## 2. Test Data Builders

Use builder methods for consistent test data creation.

### Pattern Implementation
```java
private Event createTestEvent(String title) {
    Event event = new Event();
    event.setTitle(title);
    event.setDescription("Test description");
    event.setType(EventType.EVENT);
    event.setStartDate(LocalDateTime.now().plusDays(30));
    event.setEndDate(LocalDateTime.now().plusDays(30).plusHours(4));
    event.setLocation("Main Hall");
    event.setPrice(50.0);
    event.setCapacity(100);
    event.setAvailableSeats(100);
    return event;
}
```

### Benefits
- ✅ Consistent test data
- ✅ Reusable across tests
- ✅ Easy to maintain
- ✅ Reduces duplication

---

## 3. MockMvc Testing Pattern

Standard pattern for testing API endpoints.

### Pattern Structure
```java
mockMvc.perform(post("/api/events")
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(eventJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.title").value("Test Event"))
        .andExpect(jsonPath("$.capacity").value(100));
```

### Key Elements
1. **HTTP Method**: post, get, put, delete
2. **Headers**: Authorization, Content-Type
3. **Request Body**: JSON content
4. **Status Assertions**: Expected HTTP status
5. **Body Assertions**: JSON path validations

---

## 4. Test Isolation

Ensure each test runs independently without shared state.

### Techniques Used

#### Database Cleanup
```java
@BeforeEach
void setUp() {
    bookingRepository.deleteAll();
    eventRepository.deleteAll();
    userRepository.deleteAll();
}
```

#### Transaction Rollback
```java
@Transactional  // Automatic rollback after each test
public class IntegrationTest {
    // Test methods
}
```

#### Context Reset
```java
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IntegrationTest {
    // Test methods
}
```

---

## 5. Mocking Pattern

Mock external dependencies in unit tests.

### Example
```java
@Mock
private UserRepository userRepository;

@Mock
private PasswordEncoder passwordEncoder;

@InjectMocks
private AuthService authService;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}

@Test
void testLogin() {
    // Given
    User user = createTestUser();
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
    
    // When
    String token = authService.login("test@aiu.edu", "password");
    
    // Then
    assertNotNull(token);
    verify(userRepository).findByEmail("test@aiu.edu");
}
```

---

## 6. Naming Conventions

### Test Method Names
```java
test[Feature]_[Condition]_[ExpectedBehavior]
```

Examples:
- `testLogin_WithValidCredentials_ReturnsToken`
- `testCreateEvent_WithPastDate_ThrowsException`
- `testBooking_WhenEventFull_ReturnsError`

### Test Class Names
```java
[ClassName]Test          // Unit tests
[ClassName]IntegrationTest  // Integration tests
```

---

## 7. Assertion Best Practices

### Single Assertion Focus
```java
// Good - Single concern
@Test
void testEvent_HasCorrectTitle() {
    Event event = eventService.createEvent(data);
    assertEquals("Test Event", event.getTitle());
}

// Acceptable - Related assertions
@Test
void testEvent_HasCorrectProperties() {
    Event event = eventService.createEvent(data);
    assertEquals("Test Event", event.getTitle());
    assertEquals(100, event.getCapacity());
    assertTrue(event.getStartDate().isAfter(LocalDateTime.now()));
}
```

### Meaningful Messages
```java
assertEquals(expected, actual, "Event capacity should match");
```

---

## 8. Test Organization

### Standard Structure
```java
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class BookingControllerIntegrationTest {
    
    // 1. Dependencies
    @Autowired
    private MockMvc mockMvc;
    
    // 2. Test data
    private User testUser;
    private Event testEvent;
    
    // 3. Setup
    @BeforeEach
    void setUp() {
        // Initialize test data
    }
    
    // 4. Test methods
    @Test
    void testFeature() {
        // Test implementation
    }
    
    // 5. Helper methods
    private Event createTestEvent() {
        // Helper implementation
    }
}
```

---

## 9. Exception Testing

### Pattern
```java
@Test
void testInvalidInput_ThrowsException() {
    // Given
    String invalidEmail = "not-an-email";
    
    // When & Then
    assertThrows(ValidationException.class, () -> {
        authService.register(invalidEmail, "password");
    });
}
```

---

## 10. Integration Test Setup

### Complete Pattern
```java
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import(TestConfig.class)
public class IntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    private String authToken;
    
    @BeforeEach
    void setUp() {
        // Clean database
        // Create test data
        // Generate JWT token
        authToken = jwtUtil.generateToken(email, role);
    }
    
    @Test
    void testAuthenticatedEndpoint() throws Exception {
        mockMvc.perform(post("/api/protected")
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonData))
                .andExpect(status().isOk());
    }
}
```

---

## Summary

These patterns ensure:
- ✅ Consistent test structure
- ✅ High maintainability
- ✅ Clear documentation
- ✅ Reliable test execution
- ✅ Easy debugging

Following these patterns throughout the test suite maintains quality and makes tests easy to understand and maintain.
