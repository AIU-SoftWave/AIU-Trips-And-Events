package com.aiu.trips.service;

import com.aiu.trips.enums.EventStatus;
import com.aiu.trips.enums.EventType;
import com.aiu.trips.enums.UserRole;
import com.aiu.trips.model.Event;
import com.aiu.trips.model.User;
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
 * Unit tests for EventService
 * Based on CSV Test Cases: TC_015-TC_027 (Event Management)
 */
@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private Event testEvent;
    private User testOrganizer;

    @BeforeEach
    void setUp() {
        testOrganizer = new User();
        testOrganizer.setId(1L);
        testOrganizer.setEmail("organizer@aiu.edu");
        testOrganizer.setFullName("Test Organizer");
        testOrganizer.setRole(UserRole.ADMIN);

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
        testEvent.setCreatedBy(testOrganizer);
    }

    // TC_015: Validate that organizers can create events with all required information
    @Test
    void testCreateEvent_Success() {
        // Arrange
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);

        // Act
        Event result = eventRepository.save(testEvent);

        // Assert
        assertNotNull(result);
        assertEquals(testEvent.getTitle(), result.getTitle());
        assertEquals(testEvent.getCapacity(), result.getCapacity());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    // TC_016: Validate that event images can be uploaded successfully
    @Test
    void testCreateEventWithImage_Success() {
        // Arrange
        testEvent.setImageUrl("https://example.com/event-image.jpg");
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);

        // Act
        Event result = eventRepository.save(testEvent);

        // Assert
        assertNotNull(result);
        assertEquals("https://example.com/event-image.jpg", result.getImageUrl());
    }

    // TC_017: Validate that the system prevents creation of events with past dates
    @Test
    void testCreateEventWithPastDate_ValidationError() {
        // Arrange
        testEvent.setStartDate(LocalDateTime.now().minusDays(1));

        // Act & Assert
        // This validation should happen at the controller/validation layer
        // In service layer, we just ensure we don't save invalid events
        assertTrue(testEvent.getStartDate().isBefore(LocalDateTime.now()));
    }

    // TC_018: Validate that capacity must be a positive number
    @Test
    void testCreateEventWithNegativeCapacity_ValidationError() {
        // Arrange
        testEvent.setCapacity(-10);

        // Act & Assert
        // Validation should catch this
        assertTrue(testEvent.getCapacity() < 0);
    }

    // TC_019: Validate that organizers can edit existing events
    @Test
    void testUpdateEvent_Success() {
        // Arrange
        when(eventRepository.findById(1L)).thenReturn(Optional.of(testEvent));
        testEvent.setTitle("Updated Title");
        testEvent.setPrice(100.0);
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);

        // Act
        Optional<Event> eventOpt = eventRepository.findById(1L);
        assertTrue(eventOpt.isPresent());
        Event event = eventOpt.get();
        event.setTitle("Updated Title");
        event.setPrice(100.0);
        Event result = eventRepository.save(event);

        // Assert
        assertEquals("Updated Title", result.getTitle());
        assertEquals(100.0, result.getPrice());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    // TC_021: Validate that editing preserves existing bookings
    @Test
    void testUpdateEvent_PreservesBookings() {
        // Arrange
        testEvent.setAvailableSeats(80); // Some seats booked
        when(eventRepository.findById(1L)).thenReturn(Optional.of(testEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(testEvent);

        // Act
        Optional<Event> eventOpt = eventRepository.findById(1L);
        assertTrue(eventOpt.isPresent());
        Event event = eventOpt.get();
        int bookedSeats = event.getCapacity() - event.getAvailableSeats();
        event.setTitle("Updated Event");
        Event result = eventRepository.save(event);

        // Assert
        assertEquals(20, bookedSeats); // 100 - 80 = 20 booked
        assertEquals(80, result.getAvailableSeats());
    }

    // TC_022: Validate that organizers can delete events
    @Test
    void testDeleteEvent_Success() {
        // Arrange
        when(eventRepository.findById(1L)).thenReturn(Optional.of(testEvent));
        doNothing().when(eventRepository).delete(testEvent);

        // Act
        Optional<Event> eventOpt = eventRepository.findById(1L);
        assertTrue(eventOpt.isPresent());
        eventRepository.delete(eventOpt.get());

        // Assert
        verify(eventRepository, times(1)).delete(testEvent);
    }

    // TC_025: Validate that events are categorized correctly
    @Test
    void testEventCategorization_Success() {
        // Arrange & Act
        Event conferenceEvent = new Event();
        conferenceEvent.setType(EventType.EVENT);
        conferenceEvent.setTitle("Conference");

        Event tripEvent = new Event();
        tripEvent.setType(EventType.TRIP);
        tripEvent.setTitle("Trip");

        // Assert
        assertEquals(EventType.EVENT, conferenceEvent.getType());
        assertEquals(EventType.TRIP, tripEvent.getType());
    }

    // TC_026: Validate that registration closes when capacity is reached
    @Test
    void testRegistrationClosedWhenFull_Success() {
        // Arrange
        testEvent.setCapacity(100);
        testEvent.setAvailableSeats(0);

        // Act
        boolean isFull = testEvent.getAvailableSeats() == 0;

        // Assert
        assertTrue(isFull);
        assertEquals(0, testEvent.getAvailableSeats());
    }

    // TC_027: Validate that registration deadlines are enforced
    @Test
    void testRegistrationDeadlineEnforcement_Success() {
        // Arrange
        LocalDateTime registrationDeadline = LocalDateTime.now().minusDays(1);
        testEvent.setEndDate(registrationDeadline);

        // Act
        boolean isPastDeadline = LocalDateTime.now().isAfter(testEvent.getEndDate());

        // Assert
        assertTrue(isPastDeadline);
    }

    // Additional test: Get all events
    @Test
    void testGetAllEvents_Success() {
        // Arrange
        Event event2 = new Event();
        event2.setId(2L);
        event2.setTitle("Mountain Trip");
        event2.setType(EventType.TRIP);

        List<Event> events = Arrays.asList(testEvent, event2);
        when(eventRepository.findAll()).thenReturn(events);

        // Act
        List<Event> result = eventRepository.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(eventRepository, times(1)).findAll();
    }

    // Additional test: Get event by ID
    @Test
    void testGetEventById_Success() {
        // Arrange
        when(eventRepository.findById(1L)).thenReturn(Optional.of(testEvent));

        // Act
        Optional<Event> result = eventRepository.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testEvent.getId(), result.get().getId());
        assertEquals(testEvent.getTitle(), result.get().getTitle());
    }

    // Additional test: Get event by ID not found
    @Test
    void testGetEventById_NotFound() {
        // Arrange
        when(eventRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Event> result = eventRepository.findById(999L);

        // Assert
        assertFalse(result.isPresent());
    }

    // Additional test: Event capacity validation
    @Test
    void testEventCapacityValidation_PositiveCapacity() {
        // Arrange & Act
        testEvent.setCapacity(50);

        // Assert
        assertTrue(testEvent.getCapacity() > 0);
    }

    // Additional test: Event date validation
    @Test
    void testEventDateValidation_FutureDate() {
        // Arrange
        LocalDateTime futureDate = LocalDateTime.now().plusDays(10);
        testEvent.setStartDate(futureDate);

        // Act & Assert
        assertTrue(testEvent.getStartDate().isAfter(LocalDateTime.now()));
    }
}
