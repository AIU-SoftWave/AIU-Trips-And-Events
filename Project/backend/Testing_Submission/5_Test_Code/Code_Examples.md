# Code Examples

## Complete Test Examples

### 1. Unit Test Example - AuthServiceTest

```java
package com.aiu.trips.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private JwtUtil jwtUtil;
    
    @InjectMocks
    private AuthService authService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testLogin_WithValidCredentials_ReturnsToken() {
        // Given: User with valid credentials exists
        User user = new User();
        user.setEmail("test@aiu.edu");
        user.setPassword("encoded_password");
        user.setRole(UserRole.STUDENT);
        
        when(userRepository.findByEmail("test@aiu.edu"))
            .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encoded_password"))
            .thenReturn(true);
        when(jwtUtil.generateToken("test@aiu.edu", "STUDENT"))
            .thenReturn("jwt_token_here");
        
        // When: Login with correct credentials
        String token = authService.login("test@aiu.edu", "password123");
        
        // Then: Token should be returned
        assertNotNull(token);
        assertEquals("jwt_token_here", token);
        verify(userRepository).findByEmail("test@aiu.edu");
        verify(passwordEncoder).matches("password123", "encoded_password");
    }
    
    @Test
    void testRegister_WithDuplicateEmail_ThrowsException() {
        // Given: User with email already exists
        when(userRepository.existsByEmail("test@aiu.edu"))
            .thenReturn(true);
        
        // When & Then: Registration should throw exception
        assertThrows(DuplicateEmailException.class, () -> {
            authService.register("test@aiu.edu", "password", "Test User");
        });
        
        verify(userRepository).existsByEmail("test@aiu.edu");
        verify(userRepository, never()).save(any());
    }
}
```

### 2. Integration Test Example - EventControllerIntegrationTest

```java
package com.aiu.trips.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EventControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    private String adminToken;
    
    @BeforeEach
    void setUp() {
        eventRepository.deleteAll();
        adminToken = jwtUtil.generateToken("admin@aiu.edu", "ADMIN");
    }
    
    @Test
    void testCreateEvent_Success() throws Exception {
        String eventJson = """
            {
                "title": "Tech Conference",
                "description": "Annual tech conference",
                "type": "EVENT",
                "startDate": "2026-01-15T09:00:00",
                "location": "Main Hall",
                "price": 50.0,
                "capacity": 100
            }
            """;
        
        mockMvc.perform(post("/api/events")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(eventJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Tech Conference"))
                .andExpect(jsonPath("$.capacity").value(100));
    }
    
    @Test
    void testGetAllEvents_Success() throws Exception {
        // Given: Events exist in database
        Event event1 = createTestEvent("Event 1");
        Event event2 = createTestEvent("Event 2");
        eventRepository.save(event1);
        eventRepository.save(event2);
        
        // When & Then: GET should return all events
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }
    
    private Event createTestEvent(String title) {
        Event event = new Event();
        event.setTitle(title);
        event.setDescription("Test event");
        event.setType(EventType.EVENT);
        event.setStartDate(LocalDateTime.now().plusDays(30));
        event.setCapacity(100);
        return event;
    }
}
```

### 3. Test Data Builder Example

```java
public class TestDataBuilder {
    
    public static User createStudent(String email) {
        User user = new User();
        user.setEmail(email);
        user.setPassword("encoded_password");
        user.setFullName("Test Student");
        user.setPhoneNumber("555-0100");
        user.setRole(UserRole.STUDENT);
        return user;
    }
    
    public static Event createEvent(String title, int capacity) {
        Event event = new Event();
        event.setTitle(title);
        event.setDescription("Test event description");
        event.setType(EventType.EVENT);
        event.setStartDate(LocalDateTime.now().plusDays(30));
        event.setEndDate(LocalDateTime.now().plusDays(30).plusHours(4));
        event.setLocation("Main Hall");
        event.setPrice(50.0);
        event.setCapacity(capacity);
        event.setAvailableSeats(capacity);
        return event;
    }
    
    public static Booking createBooking(User user, Event event) {
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setEvent(event);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setBookingCode("BOOK" + System.currentTimeMillis());
        booking.setBookingDate(LocalDateTime.now());
        booking.setAmountPaid(event.getPrice());
        booking.setPaymentMethod(PaymentMethod.CASH);
        return booking;
    }
}
```

### 4. JWT Token Generation for Tests

```java
@BeforeEach
void setUp() {
    // Create test user
    testUser = new User();
    testUser.setEmail("test@aiu.edu");
    testUser.setPassword(passwordEncoder.encode("password123"));
    testUser.setFullName("Test User");
    testUser.setRole(UserRole.STUDENT);
    testUser = userRepository.save(testUser);
    
    // Generate JWT token
    studentToken = jwtUtil.generateToken(
        testUser.getEmail(), 
        testUser.getRole().name()
    );
}
```

### 5. Testing Exceptions

```java
@Test
void testCreateEvent_WithPastDate_ThrowsException() {
    // Given: Event with past date
    Event event = new Event();
    event.setStartDate(LocalDateTime.now().minusDays(1));
    
    // When & Then: Should throw validation exception
    assertThrows(ValidationException.class, () -> {
        eventService.createEvent(event);
    });
}
```

### 6. Testing Async Operations

```java
@Test
void testAsyncNotification() throws Exception {
    // Given: Booking created
    Booking booking = bookingService.createBooking(userId, eventId);
    
    // When: Wait for async notification
    await().atMost(5, TimeUnit.SECONDS)
           .untilAsserted(() -> {
               verify(notificationService).sendBookingConfirmation(booking);
           });
}
```

---

These examples demonstrate real working code from the test suite following all established patterns and best practices.
