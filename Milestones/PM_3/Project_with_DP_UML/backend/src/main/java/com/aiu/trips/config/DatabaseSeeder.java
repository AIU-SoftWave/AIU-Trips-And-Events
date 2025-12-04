package com.aiu.trips.config;

import com.aiu.trips.enums.*;
import com.aiu.trips.model.*;
import com.aiu.trips.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Database Seeder - Populates the database with sample data for testing and development
 * This runs after DataInitializer to add comprehensive sample data
 */
@Component
@Order(2)
@Profile({"dev", "docker"})
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 1) {
            System.out.println("Database already seeded. Skipping...");
            return;
        }

        System.out.println("Starting database seeding...");

        // Create users
        List<User> users = createUsers();
        
        // Create events
        List<Activity> events = createEvents(users);
        
        // Create bookings
        createBookings(users, events);
        
        // Create feedback
        createFeedback(users, events);
        
        // Create notifications
        createNotifications(users);

        System.out.println("Database seeding completed successfully!");
        System.out.println("Sample credentials:");
        System.out.println("  Admin: admin@aiu.edu / admin123");
        System.out.println("  Student: john.doe@aiu.edu / password123");
        System.out.println("  Student: jane.smith@aiu.edu / password123");
        System.out.println("  Organizer: organizer@aiu.edu / password123");
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();

        // Create student users
        User student1 = new User();
        student1.setEmail("john.doe@aiu.edu");
        student1.setPassword(passwordEncoder.encode("password123"));
        student1.setFullName("John Doe");
        student1.setPhoneNumber("555-0101");
        student1.setRole(UserRole.STUDENT);
        users.add(userRepository.save(student1));

        User student2 = new User();
        student2.setEmail("jane.smith@aiu.edu");
        student2.setPassword(passwordEncoder.encode("password123"));
        student2.setFullName("Jane Smith");
        student2.setPhoneNumber("555-0102");
        student2.setRole(UserRole.STUDENT);
        users.add(userRepository.save(student2));

        User student3 = new User();
        student3.setEmail("mike.johnson@aiu.edu");
        student3.setPassword(passwordEncoder.encode("password123"));
        student3.setFullName("Mike Johnson");
        student3.setPhoneNumber("555-0103");
        student3.setRole(UserRole.STUDENT);
        users.add(userRepository.save(student3));

        User student4 = new User();
        student4.setEmail("sarah.williams@aiu.edu");
        student4.setPassword(passwordEncoder.encode("password123"));
        student4.setFullName("Sarah Williams");
        student4.setPhoneNumber("555-0104");
        student4.setRole(UserRole.STUDENT);
        users.add(userRepository.save(student4));

        // Create organizer (using ADMIN role)
        User organizer = new User();
        organizer.setEmail("organizer@aiu.edu");
        organizer.setPassword(passwordEncoder.encode("password123"));
        organizer.setFullName("Event Organizer");
        organizer.setPhoneNumber("555-0201");
        organizer.setRole(UserRole.ADMIN);
        users.add(userRepository.save(organizer));

        System.out.println("Created " + users.size() + " users");
        return users;
    }

    private List<Activity> createEvents(List<User> users) {
        List<Activity> events = new ArrayList<>();
        User organizer = users.get(4); // The organizer user

        // Event 1 - Tech Conference
        Event event1 = new Event();
        event1.setName("AI and Machine Learning Conference 2025");
        event1.setDescription("Join us for an exciting conference exploring the latest in AI and ML technologies. Network with industry experts and learn about cutting-edge research.");
        event1.setActivityDate(LocalDateTime.now().plusDays(30));
        event1.setLocation("Main Auditorium, AIU Campus");
        event1.setPrice(BigDecimal.valueOf(50.0));
        event1.setCapacity(200);
        event1.setCategory(ActivityCategory.CONFERENCE);
        event1.setStatus(ActivityStatus.UPCOMING);
        event1.setCreatedBy(organizer);
        event1.setTopic("AI and Machine Learning");
        event1.setVenue("Main Auditorium");
        events.add(eventRepository.save(event1));

        // Event 2 - Seminar
        Event event2 = new Event();
        event2.setName("Web Development Seminar");
        event2.setDescription("Learn modern web development with React, Node.js, and TypeScript. Hands-on coding sessions included.");
        event2.setActivityDate(LocalDateTime.now().plusDays(20));
        event2.setLocation("Computer Lab 301");
        event2.setPrice(BigDecimal.valueOf(75.0));
        event2.setCapacity(30);
        event2.setCategory(ActivityCategory.SEMINAR);
        event2.setStatus(ActivityStatus.UPCOMING);
        event2.setCreatedBy(organizer);
        event2.setTopic("Web Development");
        event2.setVenue("Computer Lab 301");
        events.add(eventRepository.save(event2));

        // Event 3 - Concert
        Event event3 = new Event();
        event3.setName("Spring Music Concert");
        event3.setDescription("Celebrate diversity with music and performances from around the world. Free admission!");
        event3.setActivityDate(LocalDateTime.now().plusDays(10));
        event3.setLocation("Campus Auditorium");
        event3.setPrice(BigDecimal.ZERO);
        event3.setCapacity(500);
        event3.setCategory(ActivityCategory.CONCERT);
        event3.setStatus(ActivityStatus.UPCOMING);
        event3.setCreatedBy(organizer);
        event3.setTopic("Music and Culture");
        event3.setVenue("Campus Auditorium");
        events.add(eventRepository.save(event3));

        // Trip 1 - Field Trip
        Trip trip1 = new Trip();
        trip1.setName("Mountain Hiking Adventure");
        trip1.setDescription("Experience the beauty of nature with a guided hiking trip to the nearby mountains. Suitable for all fitness levels.");
        trip1.setActivityDate(LocalDateTime.now().plusDays(15));
        trip1.setLocation("Blue Ridge Mountains");
        trip1.setPrice(BigDecimal.valueOf(150.0));
        trip1.setCapacity(50);
        trip1.setCategory(ActivityCategory.ADVENTURE_TRIP);
        trip1.setStatus(ActivityStatus.UPCOMING);
        trip1.setCreatedBy(organizer);
        trip1.setDestination("Blue Ridge Mountains");
        trip1.setDurationDays(2);
        trip1.setTransportMode("Bus");
        trip1.setStartLocation("AIU Campus");
        trip1.setEndLocation("Blue Ridge Mountains");
        events.add(eventRepository.save(trip1));

        // Trip 2 - Cultural Visit
        Trip trip2 = new Trip();
        trip2.setName("Historical Museum Tour");
        trip2.setDescription("Explore the city's rich history with a guided tour of museums and historical sites.");
        trip2.setActivityDate(LocalDateTime.now().plusDays(25));
        trip2.setLocation("City Historical District");
        trip2.setPrice(BigDecimal.valueOf(45.0));
        trip2.setCapacity(40);
        trip2.setCategory(ActivityCategory.CULTURAL_VISIT);
        trip2.setStatus(ActivityStatus.UPCOMING);
        trip2.setCreatedBy(organizer);
        trip2.setDestination("City Historical District");
        trip2.setDurationDays(1);
        trip2.setTransportMode("Bus");
        trip2.setStartLocation("AIU Campus");
        trip2.setEndLocation("City Historical District");
        events.add(eventRepository.save(trip2));

        // Event 4 - Free Event
        Event event4 = new Event();
        event4.setName("Career Fair 2025");
        event4.setDescription("Meet with top employers and explore career opportunities. Bring your resume and be ready to network!");
        event4.setActivityDate(LocalDateTime.now().plusDays(45));
        event4.setLocation("Student Center Hall A");
        event4.setPrice(BigDecimal.ZERO);
        event4.setCapacity(300);
        event4.setCategory(ActivityCategory.SEMINAR);
        event4.setStatus(ActivityStatus.UPCOMING);
        event4.setCreatedBy(organizer);
        event4.setTopic("Career Development");
        event4.setVenue("Student Center Hall A");
        events.add(eventRepository.save(event4));

        System.out.println("Created " + events.size() + " events");
        return events;
    }

    private void createBookings(List<User> users, List<Activity> events) {
        List<Booking> bookings = new ArrayList<>();

        // Student 1 bookings
        Booking booking1 = new Booking();
        booking1.setUser(users.get(0));
        booking1.setEvent(events.get(0));
        booking1.setBookingCode("BK-" + System.currentTimeMillis() + "-1");
        booking1.setStatus(BookingStatus.CONFIRMED);
        booking1.setAmountPaid(events.get(0).getPrice().doubleValue());
        bookings.add(bookingRepository.save(booking1));

        // Update available seats
        Activity event = events.get(0);
        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventRepository.save(event);

        // Student 2 bookings
        Booking booking2 = new Booking();
        booking2.setUser(users.get(1));
        booking2.setEvent(events.get(1));
        booking2.setBookingCode("BK-" + System.currentTimeMillis() + "-2");
        booking2.setStatus(BookingStatus.CONFIRMED);
        booking2.setAmountPaid(events.get(1).getPrice().doubleValue());
        bookings.add(bookingRepository.save(booking2));

        event = events.get(1);
        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventRepository.save(event);

        // Student 3 bookings
        Booking booking3 = new Booking();
        booking3.setUser(users.get(2));
        booking3.setEvent(events.get(2));
        booking3.setBookingCode("BK-" + System.currentTimeMillis() + "-3");
        booking3.setStatus(BookingStatus.CONFIRMED);
        booking3.setAmountPaid(events.get(2).getPrice().doubleValue());
        bookings.add(bookingRepository.save(booking3));

        event = events.get(2);
        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventRepository.save(event);

        System.out.println("Created " + bookings.size() + " bookings");
    }

    private void createFeedback(List<User> users, List<Activity> events) {
        List<Feedback> feedbackList = new ArrayList<>();

        // Feedback 1
        Feedback feedback1 = new Feedback();
        feedback1.setUser(users.get(0));
        feedback1.setEvent(events.get(0));
        feedback1.setRating(5);
        feedback1.setComment("Excellent conference! Learned a lot about AI and ML. The speakers were very knowledgeable.");
        feedbackList.add(feedbackRepository.save(feedback1));

        // Feedback 2
        Feedback feedback2 = new Feedback();
        feedback2.setUser(users.get(1));
        feedback2.setEvent(events.get(1));
        feedback2.setRating(4);
        feedback2.setComment("Great hiking experience. The views were breathtaking. Would recommend to everyone!");
        feedbackList.add(feedbackRepository.save(feedback2));

        // Feedback 3
        Feedback feedback3 = new Feedback();
        feedback3.setUser(users.get(2));
        feedback3.setEvent(events.get(2));
        feedback3.setRating(5);
        feedback3.setComment("Amazing career fair! Met with several companies and got two job interviews.");
        feedbackList.add(feedbackRepository.save(feedback3));

        System.out.println("Created " + feedbackList.size() + " feedback entries");
    }

    private void createNotifications(List<User> users) {
        List<Notification> notifications = new ArrayList<>();

        // Welcome notifications for students
        for (int i = 0; i < 4; i++) {
            Notification notification = new Notification();
            notification.setUser(users.get(i));
            notification.setMessage("Welcome to AIU Trips and Events! Explore upcoming events and trips.");
            notification.setType("INFO");
            notification.setIsRead(false);
            notifications.add(notificationRepository.save(notification));
        }

        // Event reminder notification
        Notification reminder = new Notification();
        reminder.setUser(users.get(0));
        reminder.setMessage("Reminder: AI and Machine Learning Conference starts in 30 days!");
        reminder.setType("INFO");
        reminder.setIsRead(false);
        notifications.add(notificationRepository.save(reminder));

        System.out.println("Created " + notifications.size() + " notifications");
    }
}
