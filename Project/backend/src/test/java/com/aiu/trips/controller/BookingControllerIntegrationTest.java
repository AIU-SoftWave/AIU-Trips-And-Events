package com.aiu.trips.controller;

import com.aiu.trips.config.TestConfig;
import com.aiu.trips.enums.BookingStatus;
import com.aiu.trips.enums.EventType;
import com.aiu.trips.enums.UserRole;
import com.aiu.trips.model.Booking;
import com.aiu.trips.model.Event;
import com.aiu.trips.model.User;
import com.aiu.trips.repository.BookingRepository;
import com.aiu.trips.repository.EventRepository;
import com.aiu.trips.repository.UserRepository;
import com.aiu.trips.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for BookingController
 * Tests booking and ticketing operations
 * Based on CSV Test Cases: TC_028-TC_039
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import(TestConfig.class)
public class BookingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private User testStudent;
    private Event testEvent;
    private String studentToken;

    @BeforeEach
    void setUp() {
        bookingRepository.deleteAll();
        eventRepository.deleteAll();
        userRepository.deleteAll();

        // Create test student
        testStudent = new User();
        testStudent.setEmail("student@aiu.edu");
        testStudent.setPassword(passwordEncoder.encode("student123"));
        testStudent.setFullName("Test Student");
        testStudent.setPhoneNumber("555-0300");
        testStudent.setRole(UserRole.STUDENT);
        testStudent = userRepository.save(testStudent);

        // Generate JWT token for the student
        studentToken = jwtUtil.generateToken(testStudent.getEmail(), testStudent.getRole().name());

        // Create test event
        testEvent = createTestEvent("Tech Conference");
        testEvent = eventRepository.save(testEvent);
    }

    // TC_028: Browse available events
    @Test
    void testBrowseAvailableEvents_Success() throws Exception {
        Event event2 = createTestEvent("Mountain Trip");
        eventRepository.save(event2);

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    // TC_029: Display event details correctly
    @Test
    void testGetEventDetails_Success() throws Exception {
        mockMvc.perform(get("/api/events/" + testEvent.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Tech Conference"))
                .andExpect(jsonPath("$.location").value("Main Hall"))
                .andExpect(jsonPath("$.price").value(50.0))
                .andExpect(jsonPath("$.capacity").value(100));
    }

    // TC_030: Booking disabled when event is full
    @Test
    void testEventFullStatus_DisplayedCorrectly() throws Exception {
        testEvent.setAvailableSeats(0);
        eventRepository.save(testEvent);

        mockMvc.perform(get("/api/events/" + testEvent.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableSeats").value(0));
    }

    // TC_031 & TC_037: Check seat availability and update after booking
    @Test
    void testCreateBooking_UpdatesAvailableSeats() throws Exception {
        int initialSeats = testEvent.getAvailableSeats();

        mockMvc.perform(post("/api/bookings/event/" + testEvent.getId())
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verify seats decreased
        mockMvc.perform(get("/api/events/" + testEvent.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableSeats").value(initialSeats - 1));
    }

    // TC_032: Prevent duplicate bookings
    @Test
    void testDuplicateBooking_Prevented() throws Exception {
        // Create first booking
        Booking booking = new Booking();
        booking.setEvent(testEvent);
        booking.setUser(testStudent);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setBookingCode("BOOK123456");
        bookingRepository.save(booking);

        // Try to create duplicate
        mockMvc.perform(post("/api/bookings/event/" + testEvent.getId())
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // TC_033: Enforce registration deadline
    @Test
    void testRegistrationDeadline_Enforced() throws Exception {
        testEvent.setEndDate(LocalDateTime.now().minusDays(1));
        eventRepository.save(testEvent);

        mockMvc.perform(post("/api/bookings/event/" + testEvent.getId())
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // TC_034 & TC_035: Generate unique QR codes and digital tickets
    @Test
    void testBooking_GeneratesQRCode() throws Exception {
        mockMvc.perform(post("/api/bookings/event/" + testEvent.getId())
                .header("Authorization", "Bearer " + studentToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingCode").exists())
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    // TC_038: Maintain booking history
    @Test
    void testGetBookingHistory_Success() throws Exception {
        // Create multiple bookings
        Event event2 = createTestEvent("Workshop");
        event2 = eventRepository.save(event2);

        Booking booking1 = createBooking(testEvent, testStudent);
        Booking booking2 = createBooking(event2, testStudent);
        bookingRepository.save(booking1);
        bookingRepository.save(booking2);

        mockMvc.perform(get("/api/bookings/user/" + testStudent.getId())
                .header("Authorization", "Bearer " + studentToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    // TC_039: Validate QR code at entry
    @Test
    void testValidateQRCode_Success() throws Exception {
        Booking booking = createBooking(testEvent, testStudent);
        booking = bookingRepository.save(booking);

        mockMvc.perform(get("/api/bookings/validate/" + booking.getBookingCode())
                .header("Authorization", "Bearer " + studentToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONFIRMED"))
                .andExpect(jsonPath("$.event.title").value("Tech Conference"));
    }

    // Additional test: Cancel booking
    @Test
    void testCancelBooking_Success() throws Exception {
        Booking booking = createBooking(testEvent, testStudent);
        booking = bookingRepository.save(booking);

        mockMvc.perform(delete("/api/bookings/" + booking.getId())
                .header("Authorization", "Bearer " + studentToken))
                .andExpect(status().isOk());

        // Verify seats restored
        mockMvc.perform(get("/api/events/" + testEvent.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableSeats").value(100));
    }

    // Additional test: Get bookings by event
    @Test
    void testGetBookingsByEvent_Success() throws Exception {
        Booking booking1 = createBooking(testEvent, testStudent);
        bookingRepository.save(booking1);

        mockMvc.perform(get("/api/bookings/event/" + testEvent.getId())
                .header("Authorization", "Bearer " + studentToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].event.id").value(testEvent.getId()));
    }

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

    private Booking createBooking(Event event, User user) {
        Booking booking = new Booking();
        booking.setEvent(event);
        booking.setUser(user);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setBookingCode("BOOK" + System.currentTimeMillis());
        booking.setBookingDate(LocalDateTime.now());
        return booking;
    }
}
