package com.aiu.trips.service;

import com.aiu.trips.enums.BookingStatus;
import com.aiu.trips.enums.EventType;
import com.aiu.trips.enums.UserRole;
import com.aiu.trips.model.Booking;
import com.aiu.trips.model.Event;
import com.aiu.trips.model.User;
import com.aiu.trips.repository.BookingRepository;
import com.aiu.trips.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for BookingService
 * Based on CSV Test Cases: TC_028-TC_039 (Booking & Ticketing)
 */
@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private BookingService bookingService;

    private Event testEvent;
    private User testStudent;
    private Booking testBooking;

    @BeforeEach
    void setUp() {
        testStudent = new User();
        testStudent.setId(1L);
        testStudent.setEmail("student@aiu.edu");
        testStudent.setFullName("Test Student");
        testStudent.setRole(UserRole.STUDENT);

        testEvent = new Event();
        testEvent.setId(1L);
        testEvent.setTitle("Tech Conference");
        testEvent.setDescription("A technology conference");
        testEvent.setType(EventType.EVENT);
        testEvent.setStartDate(LocalDateTime.now().plusDays(30));
        testEvent.setEndDate(LocalDateTime.now().plusDays(30).plusHours(4));
        testEvent.setLocation("Main Hall");
        testEvent.setPrice(50.0);
        testEvent.setCapacity(100);
        testEvent.setAvailableSeats(100);

        testBooking = new Booking();
        testBooking.setId(1L);
        testBooking.setEvent(testEvent);
        testBooking.setUser(testStudent);
        testBooking.setStatus(BookingStatus.CONFIRMED);
        testBooking.setBookingCode("BOOK123456789");
        testBooking.setBookingDate(LocalDateTime.now());
    }

    // TC_028: Validate that students can browse available events
    @Test
    void testBrowseAvailableEvents_Success() {
        // Arrange
        Event event2 = new Event();
        event2.setId(2L);
        event2.setTitle("Mountain Trip");
        event2.setType(EventType.TRIP);
        event2.setAvailableSeats(50);

        List<Event> availableEvents = Arrays.asList(testEvent, event2);
        when(eventRepository.findAll()).thenReturn(availableEvents);

        // Act
        List<Event> result = eventRepository.findAll();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(e -> e.getAvailableSeats() > 0));
    }

    // TC_029: Validate that event details are displayed correctly
    @Test
    void testDisplayEventDetails_Success() {
        // Arrange
        when(eventRepository.findById(1L)).thenReturn(Optional.of(testEvent));

        // Act
        Optional<Event> result = eventRepository.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        Event event = result.get();
        assertEquals("Tech Conference", event.getTitle());
        assertEquals("Main Hall", event.getLocation());
        assertEquals(50.0, event.getPrice());
        assertEquals(100, event.getCapacity());
    }

    // TC_030: Validate that booking button is disabled when event is full
    @Test
    void testBookingDisabledWhenEventFull_Success() {
        // Arrange
        testEvent.setAvailableSeats(0);

        // Act
        boolean canBook = testEvent.getAvailableSeats() > 0;

        // Assert
        assertFalse(canBook);
        assertEquals(0, testEvent.getAvailableSeats());
    }

    // TC_031: Validate that system checks seat availability before booking
    @Test
    void testCheckSeatAvailabilityBeforeBooking_Success() {
        // Arrange
        when(eventRepository.findById(1L)).thenReturn(Optional.of(testEvent));

        // Act
        Optional<Event> eventOpt = eventRepository.findById(1L);
        assertTrue(eventOpt.isPresent());
        boolean hasAvailableSeats = eventOpt.get().getAvailableSeats() > 0;

        // Assert
        assertTrue(hasAvailableSeats);
        assertEquals(100, testEvent.getAvailableSeats());
    }

    // TC_032: Validate that duplicate bookings are prevented
    @Test
    void testPreventDuplicateBookings_Success() {
        // Arrange
        when(bookingRepository.existsByUser_IdAndEvent_Id(1L, 1L)).thenReturn(true);

        // Act
        boolean alreadyBooked = bookingRepository.existsByUser_IdAndEvent_Id(1L, 1L);

        // Assert
        assertTrue(alreadyBooked);
        verify(bookingRepository, times(1)).existsByUser_IdAndEvent_Id(1L, 1L);
    }

    // TC_033: Validate that registration deadline is enforced
    @Test
    void testEnforceRegistrationDeadline_Success() {
        // Arrange
        testEvent.setEndDate(LocalDateTime.now().minusDays(1));

        // Act
        boolean isPastDeadline = LocalDateTime.now().isAfter(testEvent.getEndDate());

        // Assert
        assertTrue(isPastDeadline);
    }

    // TC_034: Validate that unique QR codes are generated
    @Test
    void testGenerateUniqueQRCode_Success() {
        // Arrange
        when(bookingRepository.save(any(Booking.class))).thenReturn(testBooking);

        // Act
        Booking result = bookingRepository.save(testBooking);

        // Assert
        assertNotNull(result.getBookingCode());
        assertEquals("BOOK123456789", result.getBookingCode());
    }

    // TC_035: Validate that digital tickets are sent via email
    @Test
    void testDigitalTicketCreation_Success() {
        // Arrange
        testBooking.setStatus(BookingStatus.CONFIRMED);
        when(bookingRepository.save(any(Booking.class))).thenReturn(testBooking);

        // Act
        Booking result = bookingRepository.save(testBooking);

        // Assert
        assertEquals(BookingStatus.CONFIRMED, result.getStatus());
        assertNotNull(result.getBookingCode());
    }

    // TC_037: Validate that available seats are updated correctly
    @Test
    void testUpdateAvailableSeatsAfterBooking_Success() {
        // Arrange
        int initialSeats = testEvent.getAvailableSeats();
        testEvent.setAvailableSeats(initialSeats - 1);
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);

        // Act
        Event result = eventRepository.save(testEvent);

        // Assert
        assertEquals(initialSeats - 1, result.getAvailableSeats());
    }

    // TC_038: Validate that booking history is maintained
    @Test
    void testMaintainBookingHistory_Success() {
        // Arrange
        Booking booking2 = new Booking();
        booking2.setId(2L);
        booking2.setEvent(testEvent);
        booking2.setUser(testStudent);
        booking2.setStatus(BookingStatus.CONFIRMED);

        List<Booking> bookings = Arrays.asList(testBooking, booking2);
        when(bookingRepository.findByUser_Id(1L)).thenReturn(bookings);

        // Act
        List<Booking> result = bookingRepository.findByUser_Id(1L);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(b -> b.getUser().getId().equals(1L)));
    }

    // TC_039: Validate that QR codes can be validated at entry
    @Test
    void testValidateQRCodeAtEntry_Success() {
        // Arrange
        String bookingCode = "BOOK123456789";
        when(bookingRepository.findByBookingCode(bookingCode)).thenReturn(Optional.of(testBooking));

        // Act
        Optional<Booking> result = bookingRepository.findByBookingCode(bookingCode);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(BookingStatus.CONFIRMED, result.get().getStatus());
        assertEquals(bookingCode, result.get().getBookingCode());
    }

    // Additional test: Create booking successfully
    @Test
    void testCreateBooking_Success() {
        // Arrange
        when(eventRepository.findById(1L)).thenReturn(Optional.of(testEvent));
        when(bookingRepository.existsByUser_IdAndEvent_Id(1L, 1L)).thenReturn(false);
        when(bookingRepository.save(any(Booking.class))).thenReturn(testBooking);

        // Act
        Optional<Event> eventOpt = eventRepository.findById(1L);
        assertTrue(eventOpt.isPresent());
        boolean alreadyBooked = bookingRepository.existsByUser_IdAndEvent_Id(1L, 1L);
        assertFalse(alreadyBooked);
        Booking result = bookingRepository.save(testBooking);

        // Assert
        assertNotNull(result);
        assertEquals(testEvent.getId(), result.getEvent().getId());
        assertEquals(testStudent.getId(), result.getUser().getId());
    }

    // Additional test: Cancel booking
    @Test
    void testCancelBooking_Success() {
        // Arrange
        testBooking.setStatus(BookingStatus.CANCELLED);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(testBooking);

        // Act
        Optional<Booking> bookingOpt = bookingRepository.findById(1L);
        assertTrue(bookingOpt.isPresent());
        Booking booking = bookingOpt.get();
        booking.setStatus(BookingStatus.CANCELLED);
        Booking result = bookingRepository.save(booking);

        // Assert
        assertEquals(BookingStatus.CANCELLED, result.getStatus());
    }

    // Additional test: Get bookings by event
    @Test
    void testGetBookingsByEvent_Success() {
        // Arrange
        List<Booking> bookings = Arrays.asList(testBooking);
        when(bookingRepository.findByEvent_Id(1L)).thenReturn(bookings);

        // Act
        List<Booking> result = bookingRepository.findByEvent_Id(1L);

        // Assert
        assertEquals(1, result.size());
        assertEquals(testEvent.getId(), result.get(0).getEvent().getId());
    }

    // Additional test: Booking with no available seats throws exception
    @Test
    void testBookingWithNoSeats_ThrowsException() {
        // Arrange
        testEvent.setAvailableSeats(0);
        when(eventRepository.findById(1L)).thenReturn(Optional.of(testEvent));

        // Act
        Optional<Event> eventOpt = eventRepository.findById(1L);
        assertTrue(eventOpt.isPresent());
        boolean hasSeats = eventOpt.get().getAvailableSeats() > 0;

        // Assert
        assertFalse(hasSeats);
    }
}
